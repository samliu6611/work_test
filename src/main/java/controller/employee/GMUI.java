package controller.employee;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GMUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GMUI frame = new GMUI();
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
	public GMUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 529, 434);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(10, 10, 493, 94);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("GM介面");
		lblNewLabel.setFont(new Font("新細明體", Font.PLAIN, 60));
		lblNewLabel.setBounds(127, 10, 251, 74);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(192, 192, 192));
		panel_1.setBounds(10, 116, 493, 269);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

/*************************************EVENT****************************************/		
		
		JButton btnNewButton = new JButton("道具新增");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ItemCreatorView itemCreatorView=new ItemCreatorView();
				itemCreatorView.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		btnNewButton.setBounds(42, 29, 398, 48);
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("卡池管理");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BannerManagerView bannerManagerView=new BannerManagerView();
				bannerManagerView.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		btnNewButton_1.setBounds(42, 87, 398, 48);
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("查詢玩家紀錄");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GMPlayerRecordQueryView gMPlayerRecordQueryView=new GMPlayerRecordQueryView();
				gMPlayerRecordQueryView.setVisible(true);
				dispose();
			}
		});
		btnNewButton_2.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		btnNewButton_2.setBounds(42, 145, 398, 48);
		panel_1.add(btnNewButton_2);
		
		JButton btnNewButton_2_1 = new JButton("返回登入畫面");
		btnNewButton_2_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GMConfirmUI gMConfirmUI=new GMConfirmUI();
				gMConfirmUI.setVisible(true);
				dispose();
			}
		});
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2_1.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		btnNewButton_2_1.setBounds(42, 203, 398, 48);
		panel_1.add(btnNewButton_2_1);

	}
}
