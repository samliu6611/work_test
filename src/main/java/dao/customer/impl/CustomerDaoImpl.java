package dao.customer.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.customer.CustomerDao;
import model.Customer;
import util.Tool;

public class CustomerDaoImpl implements CustomerDao{

	public static void main(String[] args) {
		System.out.println(new CustomerDaoImpl().select_by_username_and_password("ruru001","4123"));

	}

	Connection conn=Tool.getDb();
	
	@Override
	public void add(Customer customer) {
		String Sql="insert into customer(customer_no,customer_name,username,password)"
				+"values(?,?,?,?)";
		try {
			PreparedStatement ps=conn.prepareStatement(Sql);
			ps.setString(1, customer.getCustomer_no());
			ps.setString(2, customer.getCustomer_name());
			ps.setString(3, customer.getUsername());
			ps.setString(4, customer.getPassword());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Customer> select_by_username_and_password(String username, String password) {
		String Sql="select * from customer where username=? and password=?";
		List<Customer> l=new ArrayList<>();
		try {
			PreparedStatement ps=conn.prepareStatement(Sql);
			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				Customer c=new Customer();
				c.setId(rs.getInt("id"));
				c.setCustomer_no(rs.getString("customer_no"));
				c.setCustomer_name(rs.getString("customer_name"));
				c.setUsername(rs.getString("username"));
				c.setPassword(rs.getString("password"));
				l.add(c);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
		
	}

	@Override
	public List<Customer> select_by_username(String username) {
		String Sql="select * from customer where username=?";
		List<Customer> l=new ArrayList<>();
		try {
			PreparedStatement ps=conn.prepareStatement(Sql);
			ps.setString(1, username);
			
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				Customer c=new Customer();
				c.setId(rs.getInt("id"));
				c.setCustomer_no(rs.getString("customer_no"));
				c.setCustomer_name(rs.getString("customer_name"));
				c.setUsername(rs.getString("username"));
				c.setPassword(rs.getString("password"));
				l.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return l;
	}
	
	@Override
	public int getCustomerCount() {
		String Sql = "select count(*) from customer";
		int count = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(Sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1); // 取得目前的資料總筆數
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

}
