package controller.cover;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;



public class CoverUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CoverUI frame = new CoverUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CoverUI() {
		// 基本視窗設定
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 808, 496);
		setLocationRelativeTo(null); // 讓視窗顯示在螢幕中央
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(45, 45, 45)); // 改為深灰色更有質感
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 1. 【手遊感核心】全畫面點擊功能
		contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 滑鼠移入變手型
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 跳轉至登入畫面
				LoginUI loginUI = new LoginUI();
				loginUI.setVisible(true);
				dispose(); // 關閉封面
			}
		});
		
		// 遊戲標題
		JLabel lblTitle = new JLabel("武器轉蛋遊戲");
		lblTitle.setForeground(new Color(255, 215, 0)); // 金色標題
		lblTitle.setFont(new Font("微軟正黑體", Font.BOLD, 70));
		lblTitle.setBounds(180, 60, 450, 100);
		contentPane.add(lblTitle);
		
		// 點擊提示文字
		JLabel lblClickHint = new JLabel("點擊畫面進入遊戲");
		lblClickHint.setForeground(Color.WHITE);
		lblClickHint.setFont(new Font("微軟正黑體", Font.PLAIN, 30));
		lblClickHint.setBounds(275, 330, 300, 50);
		contentPane.add(lblClickHint);
		
		// 版本資訊 (裝飾用)
		JLabel lblVersion = new JLabel("Version 1.0.2 Build 2026");
		lblVersion.setForeground(Color.GRAY);
		lblVersion.setBounds(10, 435, 200, 15);
		contentPane.add(lblVersion);

		// 2. 【視覺效果】提示文字閃爍動畫
		// 每 800 毫秒切換一次可見度
		Timer blinkTimer = new Timer(800, e -> {
			lblClickHint.setVisible(!lblClickHint.isVisible());
		});
		blinkTimer.start();
	}
}