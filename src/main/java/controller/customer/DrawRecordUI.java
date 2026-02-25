package controller.customer;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.DrawRecord;
import service.GachaService;

public class DrawRecordUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel tableModel;
	private GachaService gachaService = new GachaService();

	public DrawRecordUI(String playerName) {
		setTitle(playerName + " 的祈願紀錄");
		setBounds(100, 100, 600, 450);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 只關閉此視窗
		
		// 建立表格欄位
		String[] columnNames = {"時間", "卡池名稱", "獲得道具"};
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		table.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		table.setRowHeight(25);
		
		// 撈取資料並填入表格
		List<DrawRecord> history = gachaService.getPlayerHistory(playerName);
		for (DrawRecord record : history) {
			Object[] row = {
				record.getDraw_time(), // 確保 DrawRecord 裡有這個 getter
				record.getMachine_name(), 
				record.getItem_name()
			};
			tableModel.addRow(row);
		}

		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
	}
}