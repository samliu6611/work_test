package controller.cover;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.customer.AddCustomerUI;
import controller.customer.LoginErrorUI;
import controller.customer.LoginSuccessUI;
import controller.employee.ELoginSuccessUI;
import model.Customer;
import model.Employee;
import service.customer.impl.CustomerServiceImpl;
import service.employee.impl.EmployeeServiceImpl;
import util.Tool;

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

public class LoginUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField username;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI frame = new LoginUI();
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
	public LoginUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 544, 428);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(10, 10, 508, 116);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("會員登入");
		lblNewLabel.setFont(new Font("新細明體", Font.PLAIN, 60));
		lblNewLabel.setBounds(127, 10, 355, 96);
		panel.add(lblNewLabel);
		
		JLabel lblClock = new JLabel("載入中....");
		lblClock.setBounds(185, 91, 220, 25);
		panel.add(lblClock);
		
		JButton btnNewButton_2 = new JButton("返回主畫面");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CoverUI coverUI=new CoverUI();
				coverUI.setVisible(true);
				dispose();
			}
		});
		btnNewButton_2.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		btnNewButton_2.setBounds(379, 10, 119, 39);
		panel.add(btnNewButton_2);
		
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
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(192, 192, 192));
		panel_1.setBounds(10, 136, 508, 243);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("帳號:");
		lblNewLabel_1.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(100, 52, 91, 37);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("密碼:");
		lblNewLabel_1_1.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(100, 99, 91, 37);
		panel_1.add(lblNewLabel_1_1);
		
		username = new JTextField();
		username.setBounds(201, 52, 208, 37);
		panel_1.add(username);
		username.enableInputMethods(false);
		username.setColumns(10);
		
		password = new JPasswordField();
		password.setEchoChar('*');
		password.setColumns(10);
		password.setBounds(201, 99, 208, 37);
		password.enableInputMethods(false);
		panel_1.add(password);
		
		
		
/*******************************EVENT***********************************/
		JButton btnNewButton = new JButton("註冊");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddCustomerUI addCustomerUI=new AddCustomerUI();
				addCustomerUI.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		btnNewButton.setBounds(90, 175, 126, 43);
		panel_1.add(btnNewButton);
		
		
		CustomerServiceImpl customerServiceImpl=new CustomerServiceImpl();
		JButton btnNewButton_1 = new JButton("登入");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String Username=username.getText();
				String Password=password.getText();
				
				Customer customer=customerServiceImpl.find_customer_by_username_and_password(Username, Password);
				if(customer!=null)
				{
					Tool.saveObject(customer, "Customer");
					
					LoginSuccessUI loginSuccess=new LoginSuccessUI();
					loginSuccess.setVisible(true);
					dispose();	
				}
				else
				{
					EmployeeServiceImpl employeeServiceImpl=new EmployeeServiceImpl();
					Employee employee=employeeServiceImpl.find_employee_by_employee_username_and_employee_password(Username, Password);
					
					if(employee !=null)
					{
						Tool.saveObject(employee, "Employee");
						
						ELoginSuccessUI eLoginSuccessUI=new ELoginSuccessUI();
						eLoginSuccessUI.setVisible(true);
						dispose();
					}
					else
					{
						LoginErrorUI loginerror=new LoginErrorUI();
						loginerror.setVisible(true);
						dispose();
					}
				}
			}
		});
		btnNewButton_1.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		btnNewButton_1.setBounds(306, 175, 126, 43);
		panel_1.add(btnNewButton_1);

	}
}
