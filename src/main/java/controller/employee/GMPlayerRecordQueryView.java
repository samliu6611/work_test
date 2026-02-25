package controller.employee;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.DrawRecord;
import service.GachaService;

public class GMPlayerRecordQueryView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPlayerName;
	private JTable table;
	private DefaultTableModel tableModel;
	private GachaService gachaService = new GachaService();

	public GMPlayerRecordQueryView() {
		setTitle("GM 系統 - 指定玩家紀錄查詢");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// 標題
		JLabel lblTitle = new JLabel("玩家抽獎歷史查詢");
		lblTitle.setFont(new Font("微軟正黑體", Font.BOLD, 22));
		lblTitle.setBounds(20, 15, 200, 30);
		contentPane.add(lblTitle);

		// 搜尋區域
		JLabel lblSearch = new JLabel("輸入玩家名稱:");
		lblSearch.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		lblSearch.setBounds(20, 60, 110, 30);
		contentPane.add(lblSearch);

		txtPlayerName = new JTextField();
		txtPlayerName.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		txtPlayerName.setBounds(130, 60, 200, 30);
		contentPane.add(txtPlayerName);

		JButton btnSearch = new JButton(" 搜尋");
		btnSearch.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		btnSearch.setBounds(340, 60, 100, 30);
		contentPane.add(btnSearch);

		// 表格區域
		String[] columnNames = {"時間", "玩家", "卡池名稱", "獲得道具"};
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		table.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		table.setRowHeight(25);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(20, 110, 640, 300);
		contentPane.add(scrollPane);

		// 返回按鈕
		JButton btnBack = new JButton("返回");
		btnBack.setBounds(540, 420, 120, 30);
		btnBack.addActionListener(e -> {
			new GMUI().setVisible(true);
			dispose();
		});
		contentPane.add(btnBack);

		// 搜尋邏輯
		btnSearch.addActionListener(e -> {
			String name = txtPlayerName.getText().trim();
			if (name.isEmpty()) {
				JOptionPane.showMessageDialog(this, "請輸入玩家名稱！");
				return;
			}
			loadData(name);
		});
	}

	private void loadData(String name) {
		// 先清空表格
		tableModel.setRowCount(0);
		
		// 呼叫 Service 撈資料
		List<DrawRecord> history = gachaService.getPlayerHistory(name);
		
		if (history.isEmpty()) {
			JOptionPane.showMessageDialog(this, "找不到玩家 [" + name + "] 的抽獎紀錄。");
			return;
		}

		for (DrawRecord record : history) {
			Object[] row = {
				record.getDraw_time(),
				record.getPlayer_name(),
				record.getMachine_name(),
				record.getItem_name()
			};
			tableModel.addRow(row);
		}
	}
}