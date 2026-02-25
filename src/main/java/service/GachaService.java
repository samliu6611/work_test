package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dao.RecordDao; 
import dao.item.ItemDao;
import dao.item.impl.ItemDaoImpl;
import model.DrawRecord;
import model.Item;
import util.Tool;

public class GachaService {
	
	private ItemDao itemDao = new ItemDaoImpl();
	private RecordDao recordDAO = new RecordDao(); 
	
	/**
	 * 新增道具邏輯
	 */
	public String createNewItem(String name, String rarity, double prob, String machineName) {
	    try {
	        int count = itemDao.getItemCount();
	        String item_no = "it" + String.format("%03d", count + 1) + rarity;
	        Item newItem = new Item(item_no, name, rarity, prob, machineName);
	        itemDao.add(newItem);
	        return "成功！新增道具：【" + name + "】至 [" + machineName + "]";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "新增失敗，請檢查資料庫連線。";
	    }
	}
	
	/**
	 * 玩家單抽邏輯 (新手卡池不可單抽)
	 */
	public String drawItemForPlayer(String playerName, String machineName) {
		// 1. 時間檢查
	    if (!isBannerOpen(machineName)) {
	        return "【系統提示】抱歉，" + machineName + " 目前不在開放時間內！";
	    }

	    // 2. 新手卡池攔截
	    if (machineName.equals("新手卡池")) {
	        return "【系統提示】新手卡池限定 10 連召喚，請使用 10 連按鈕！";
	    }

		List<Item> pool = itemDao.select_by_machine(machineName);

		// 特殊卡池規則：超絕武器神祭
		if (machineName.equals("超絕武器神祭")) {
			pool = getMixedGodPool();
		}

		if (pool.isEmpty()) return "這台轉蛋機目前維修中！";

		return executeDraw(playerName, machineName, pool);
	}

	/**
	 * 玩家 10 連抽邏輯 (新手卡池限抽一次)
	 */
	public List<String> drawTenItemsForPlayer(String playerName, String machineName) {
		List<String> results = new ArrayList<>();
		
		// 1. 開放檢查
		if (!isBannerOpen(machineName)) {
			results.add("【系統提示】該卡池目前已關閉。");
			return results;
		}

		// 2. 新手卡池「僅限一次」檢查
		if (machineName.equals("新手卡池")) {
			List<DrawRecord> history = recordDAO.getRecordsByPlayer(playerName);
			for (DrawRecord r : history) {
				if (r.getMachine_name().equals("新手卡池")) {
					results.add("【系統提示】您已參加過新手召喚活動，無法再次抽取。");
					return results;
				}
			}
			// 執行新手 10 連 (9 抽 + 1 SSR 保底)
			return executeNewbieTenDraw(playerName);
		}

		// 3. 一般卡池 10 連抽 (含 SR 保底邏輯)
		boolean hasSRorAbove = false;
		for (int i = 0; i < 9; i++) {
			String res = drawItemForInternal(playerName, machineName);
			results.add(res);
			if (res.contains("SR") || res.contains("SSR")) hasSRorAbove = true;
		}

		// 第 10 抽保底
		if (!hasSRorAbove) {
			results.add(drawGuaranteedItem(playerName, machineName, "SR"));
		} else {
			results.add(drawItemForInternal(playerName, machineName));
		}

		return results;
	}

	// --- 內部私有輔助方法 ---

	/** 執行抽獎演算法並儲存紀錄 */
	private String executeDraw(String playerName, String machineName, List<Item> pool) {
		double totalWeight = 0.0;
		for (Item item : pool) totalWeight += item.getProbability();

		double rand = Math.random() * totalWeight;
		double cumulative = 0.0;
		for (Item item : pool) {
			cumulative += item.getProbability();
			if (rand <= cumulative) {
				String wonName = "【" + item.getRarity() + "】" + item.getItem_name();
				recordDAO.saveRecord(playerName, machineName, wonName);
				return wonName;
			}
		}
		return "摃龜";
	}

	/** 專門給 10 連抽內部使用的單抽，不重複檢查時間 */
	private String drawItemForInternal(String playerName, String machineName) {
		List<Item> pool = (machineName.equals("超絕武器神祭")) ? getMixedGodPool() : itemDao.select_by_machine(machineName);
		return executeDraw(playerName, machineName, pool);
	}

	/** 新手 10 連抽專用邏輯 (9 常駐 + 1 SSR) */
	private List<String> executeNewbieTenDraw(String playerName) {
		List<String> results = new ArrayList<>();
		// 前 9 抽從常駐池抽
		for (int i = 0; i < 9; i++) {
			results.add(drawItemForInternal(playerName, "常駐卡池"));
		}
		// 第 10 抽必中 SSR
		results.add(drawGuaranteedItem(playerName, "常駐卡池", "SSR"));
		return results;
	}

	/** 指定稀有度保底抽獎 */
	private String drawGuaranteedItem(String playerName, String machineName, String minRarity) {
		List<Item> allItems = itemDao.select_by_machine(machineName);
		List<Item> gPool = new ArrayList<>();
		for (Item item : allItems) {
			if (minRarity.equals("SSR") && item.getRarity().equals("SSR")) gPool.add(item);
			else if (minRarity.equals("SR") && (item.getRarity().equals("SR") || item.getRarity().equals("SSR"))) gPool.add(item);
		}
		if (gPool.isEmpty()) return drawItemForInternal(playerName, machineName);
		
		int idx = (int)(Math.random() * gPool.size());
		Item won = gPool.get(idx);
		String wonName = "【" + won.getRarity() + "】" + won.getItem_name() + " (保底!)";
		recordDAO.saveRecord(playerName, "新手卡池", wonName);
		return wonName;
	}

	/** 獲取神祭混池資料 */
	private List<Item> getMixedGodPool() {
		String[] pools = {"武士崛起", "騎士王傳說", "亞洲父母"};
		List<Item> mixed = new ArrayList<>();
		for (String pName : pools) {
			for (Item item : itemDao.select_by_machine(pName)) {
				if (!item.getRarity().equals("N")) {
					Item up = new Item(item.getItem_no(), item.getItem_name(), item.getRarity(), item.getProbability(), item.getMachine_name());
					if (up.getRarity().equals("SSR")) up.setProbability(up.getProbability() * 3.0);
					if (up.getRarity().equals("SR")) up.setProbability(up.getProbability() * 2.0);
					mixed.add(up);
				}
			}
		}
		return mixed;
	}

	/** 檢查時間 */
	public boolean isBannerOpen(String machineName) {
	    String sql = "SELECT start_time, end_time, is_permanent FROM banners WHERE banner_name = ?";
	    try (Connection conn = Tool.getDb();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, machineName);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            if (rs.getBoolean("is_permanent")) return true;
	            Timestamp start = rs.getTimestamp("start_time");
	            Timestamp end = rs.getTimestamp("end_time");
	            Timestamp now = new Timestamp(System.currentTimeMillis());
	            if (start != null && end != null) return now.after(start) && now.before(end);
	        }
	    } catch (SQLException e) { e.printStackTrace(); }
	    return false;
	}

	/** 獲取時間範圍字串 */
	public String getBannerTimeRange(String machineName) {
		String sql = "SELECT start_time, end_time, is_permanent FROM banners WHERE banner_name = ?";
		try (Connection conn = Tool.getDb(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, machineName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getBoolean("is_permanent")) return "開放狀態：永久開放";
				Timestamp start = rs.getTimestamp("start_time");
				Timestamp end = rs.getTimestamp("end_time");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				if (start != null && end != null) return "活動時間：" + sdf.format(start) + " ~ " + sdf.format(end);
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return "活動時間：即將開放";
	}

	public String getBannerProbabilities(String machine_name) {
		List<Item> items = itemDao.select_by_machine(machine_name);
		if (items.isEmpty()) return "該卡池目前沒有道具資料。";
		StringBuilder sb = new StringBuilder("═══ " + machine_name + " 機率詳情 ═══\n");
		sb.append("◆ SSR 稀有道具:\n");
		for (Item item : items) if (item.getRarity().equals("SSR")) sb.append("  - ").append(item.getItem_name()).append("\n");
		return sb.toString();
	}

	public List<DrawRecord> getPlayerHistory(String playerName) {
		return recordDAO.getRecordsByPlayer(playerName);
	}
}