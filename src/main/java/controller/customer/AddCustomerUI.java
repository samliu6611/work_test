package controller.customer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.cover.LoginUI;
import model.Customer;
import service.customer.impl.CustomerServiceImpl;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddCustomerUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField customer_name;
	private JTextField username;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddCustomerUI frame = new AddCustomerUI();
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
	public AddCustomerUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 578, 559);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(10, 10, 542, 500);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("帳號註冊");
		lblNewLabel.setFont(new Font("新細明體", Font.PLAIN, 80));
		lblNewLabel.setBounds(98, 38, 361, 111);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("名字:");
		lblNewLabel_1.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(98, 226, 106, 37);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("帳號:");
		lblNewLabel_1_1.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(98, 290, 106, 37);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("密碼:");
		lblNewLabel_1_2.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		lblNewLabel_1_2.setBounds(98, 356, 106, 37);
		panel.add(lblNewLabel_1_2);
		
		customer_name = new JTextField();
		customer_name.setBounds(214, 226, 209, 37);
		panel.add(customer_name);
		customer_name.setColumns(10);
		
		
		username = new JTextField();
		username.setColumns(10);
		username.setBounds(214, 290, 209, 37);
		username.enableInputMethods(false);
		panel.add(username);
		
		password = new JPasswordField();
		password.setColumns(10);
		password.setBounds(214, 356, 209, 37);
		password.enableInputMethods(false);
		panel.add(password);
		
		JLabel lblClock = new JLabel("載入中....");
		lblClock.setBounds(10, 10, 194, 31);
		panel.add(lblClock);
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
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setBounds(108, 159, 393, 31);
		panel.add(lblNewLabel_2);
		
/***********************************EVENT*************************************************/		
		JButton btnNewButton = new JButton("確定");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*
				 * 1.接收 username-->判斷帳號是否使用
				 * true-->AddCustomerErrorUI
				 * false->接收 編號,姓名,地址,帳號,密碼-->new Customer()-->AddCustomer方法->AddCustomerSuccessUI
				 */
				String Customer_name = customer_name.getText().trim();
				String Username=username.getText().trim();
				String Password = new String(password.getPassword()).trim();
				
				if (Customer_name.isEmpty() || Username.isEmpty() || Password.isEmpty()) {
					// 如果有空值，顯示提示文字並中斷執行 (return)
					lblNewLabel_2.setText("欄位不得空白，請確認所有資料皆已輸入！");
					return; 
				} else {
					// 如果都有輸入，就把提示文字清空
					lblNewLabel_2.setText(""); 
				}
				
				CustomerServiceImpl csi=new CustomerServiceImpl();
				boolean x=csi.find_username(Username);
				if(x)
				{
					AddCustomerErrorUI addCustomerErrorUI=new AddCustomerErrorUI();
					addCustomerErrorUI.setVisible(true);
					dispose();
				}
				else
				{
					String Customer_no = csi.generateCustomerNo();
					Customer customer = new Customer(Customer_no, Customer_name, Username, Password);
					
					csi.AddCustomer(customer);
					
					AddCustomerSuccess addCustomerSuccess=new AddCustomerSuccess();
					addCustomerSuccess.setVisible(true);
					dispose();
				}
			}
		});
		btnNewButton.setFont(new Font("新細明體", Font.PLAIN, 25));
		btnNewButton.setBounds(132, 427, 277, 48);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("返回");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LoginUI loginUI=new LoginUI();
				loginUI.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		btnNewButton_1.setBounds(414, 14, 118, 37);
		panel.add(btnNewButton_1);
		
			
	}
}
