package dao.employee.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.employee.EmployeeDao;
import model.Employee;
import util.Tool;

public class EmployeeDaoImpl implements EmployeeDao{

	public static void main(String[] args) {
		System.out.println(new EmployeeDaoImpl().select_by_employee_username_and_employee_password("sam1111","sam12345"));
	}
	
	Connection conn=Tool.getDb();
	
	@Override
	public void add(Employee employee) {
		String Sql="insert into employee(employee_no,employee_name,employee_username,employee_password)"
				+"values(?,?,?,?)";
		try {
			PreparedStatement ps=conn.prepareStatement(Sql);
			ps.setString(1, employee.getEmployee_no());
			ps.setString(2, employee .getEmployee_name());
			ps.setString(3, employee.getEmployee_username());
			ps.setString(4, employee.getEmployee_password());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Employee> select_by_employee_username_and_employee_password(String username, String password) {
		String Sql="select * from employee where employee_username=? and employee_password=?";
		List<Employee> l=new ArrayList<>();
		try {
			PreparedStatement ps=conn.prepareStatement(Sql);
			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				Employee e=new Employee();
				e.setId(rs.getInt("id"));
				e.setEmployee_no(rs.getString("employee_no"));
				e.setEmployee_name(rs.getString("employee_name"));
				e.setEmployee_username(rs.getString("employee_username"));
				e.setEmployee_password(rs.getString("employee_password"));
				l.add(e);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
	}

	@Override
	public List<Employee> select_by_employee_username(String username) {
		String Sql="select * from employee where employee_username=? ";
		List<Employee> l=new ArrayList<>();
		try {
			PreparedStatement ps=conn.prepareStatement(Sql);
			ps.setString(1, Sql);
			
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				Employee e=new Employee();
				e.setId(rs.getInt("id"));
				e.setEmployee_no(rs.getString("employee_no"));
				e.setEmployee_name(rs.getString("employee_name"));
				e.setEmployee_username(rs.getString("employee_username"));
				e.setEmployee_password(rs.getString("employee_password"));
				l.add(e);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
	}

}
