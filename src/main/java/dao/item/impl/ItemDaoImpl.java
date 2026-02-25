package dao.item.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.item.ItemDao;
import model.Item;
import util.Tool;

public class ItemDaoImpl implements ItemDao {

	Connection conn = Tool.getDb();

	@Override
	public void add(Item item) {
		// 修正 1：SQL 語法裡的欄位名稱改回正確的 item_no 和 item_name
		String Sql = "insert into items(item_no, item_name, rarity, probability, machine_name) values(?,?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(Sql);
			ps.setString(1, item.getItem_no());
			ps.setString(2, item.getItem_name());
			ps.setString(3, item.getRarity());
			ps.setDouble(4, item.getProbability());
			ps.setString(5, item.getMachine_name());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getItemCount() {
		String Sql = "select count(*) from items";
		int count = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(Sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<Item> select_by_machine(String machine_name) {
		String Sql = "select * from items where machine_name=?";
		List<Item> l = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(Sql);
			ps.setString(1, machine_name);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Item item = new Item();
				item.setId(rs.getInt("id"));
				
				// 修正 2：讀取資料庫時，也要用正確的 item_no 和 item_name
				item.setItem_no(rs.getString("item_no"));
				item.setItem_name(rs.getString("item_name")); 
				
				item.setRarity(rs.getString("rarity"));
				item.setProbability(rs.getDouble("probability"));
				item.setMachine_name(rs.getString("machine_name"));
				l.add(item);
			}
		} catch (SQLException e) {
			System.out.println("資料庫欄位對應錯誤，請檢查資料庫！");
			e.printStackTrace();
		}
		return l;
	}
}