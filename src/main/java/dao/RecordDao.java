package dao;

import model.DrawRecord;
import util.Tool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecordDao {
    
    // 功能 1：寫入一筆新的抽獎紀錄
    public void saveRecord(String playerName, String machineName, String itemName) {
        // 注意：我們不需要寫入 draw_time，因為資料庫設定了 DEFAULT CURRENT_TIMESTAMP 會自動抓現在時間
        String sql = "INSERT INTO draw_records (player_name, machine_name, item_name) VALUES (?, ?, ?)";
        
        try (Connection conn = Tool.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, playerName);
            pstmt.setString(2, machineName);
            pstmt.setString(3, itemName);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 功能 2：查詢某位玩家的所有抽獎紀錄 (依照時間從最新排到最舊)
    public List<DrawRecord> getRecordsByPlayer(String playerName) {
        List<DrawRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM draw_records WHERE player_name = ? ORDER BY draw_time DESC";
        
        try (Connection conn = Tool.getDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, playerName);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                DrawRecord record = new DrawRecord(
                    rs.getString("player_name"),
                    rs.getString("machine_name"),
                    rs.getString("item_name"),
                    rs.getTimestamp("draw_time") // 抓取時間
                );
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}