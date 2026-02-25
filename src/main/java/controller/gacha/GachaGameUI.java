package controller.gacha;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import controller.customer.DrawRecordUI;

import javax.swing.JLabel;
import javax.swing.JButton;

import model.Customer;
import util.Tool;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GachaGameUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Customer loggedInCustomer; // 用來存放目前登入的玩家

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GachaGameUI frame = new GachaGameUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GachaGameUI() {
		// --- 【核心魔法】從 Tool 讀取剛才登入的玩家資料 ---
		loggedInCustomer = (Customer) Tool.readObject("Customer");
		
		// 如果沒有登入資料 (可能是不小心直接執行這支程式)，給個預設值防呆
		String welcomeName = (loggedInCustomer != null) ? loggedInCustomer.getCustomer_name() : "訪客";
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 顯示歡迎標語
		String welcomeId = (loggedInCustomer != null) ? loggedInCustomer.getUsername() : "未知";
		
		JLabel lblWelcome = new JLabel("歡迎回來，玩家: " + welcomeId);
		lblWelcome.setFont(new Font("微軟正黑體", Font.BOLD, 24));
		lblWelcome.setBounds(30, 18, 400, 40);
		contentPane.add(lblWelcome);
		
		JLabel lblClock = new JLabel("載入中....");
		lblClock.setBounds(30, 68, 230, 24);
		contentPane.add(lblClock);
		
		Timer timer=new Timer(1000, new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				lblClock.setText(sdf.format(calendar.getTime()));
			}
		});
		timer.start();

/*************************************************************/		
		
		// 之後可以在這裡放「抽獎按鈕」跟「顯示結果的文字框」
		JButton btnDraw = new JButton("卡池");
		btnDraw.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GachaBannerUI gachaBannerUI=new GachaBannerUI(loggedInCustomer);
				gachaBannerUI.setVisible(true);
				dispose();
			}
		});
		btnDraw.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		btnDraw.setBounds(30, 268, 400, 50);
		contentPane.add(btnDraw);
		
		JButton btnNewButton = new JButton("查詢紀錄");
		btnNewButton.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        // 開啟紀錄視窗並傳入玩家姓名
		        DrawRecordUI recordUI = new DrawRecordUI(welcomeName);
		        recordUI.setVisible(true);
		        // 這裡通常不需要 dispose()，因為玩家看完紀錄可能還要回來抽卡
		    }
		});
		btnNewButton.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		btnNewButton.setBounds(30, 218, 400, 40);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("儲值");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				haha Haha=new haha();
				Haha.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		btnNewButton_1.setBounds(30, 163, 400, 40);
		contentPane.add(btnNewButton_1);
		
		
		
		JButton btnNewButton_2 = new JButton("返回登入畫面");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ConfirmUI confirmUI=new ConfirmUI();
				confirmUI.setVisible(true);
				dispose();
			}
		});
		btnNewButton_2.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		btnNewButton_2.setBounds(321, 20, 153, 40);
		contentPane.add(btnNewButton_2);
		
		
	}
}