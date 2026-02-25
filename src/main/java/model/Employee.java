package model;

import java.io.Serializable;

public class Employee implements Serializable{
	private int id;
	private String employee_no;
	private String employee_name;
	private String employee_username;
	private String employee_password;
	public Employee() {
		super();
	}
	public Employee(int id, String employee_no, String employee_name, String employee_username,
			String employee_password) {
		super();
		this.id = id;
		this.employee_no = employee_no;
		this.employee_name = employee_name;
		this.employee_username = employee_username;
		this.employee_password = employee_password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmployee_no() {
		return employee_no;
	}
	public void setEmployee_no(String employee_no) {
		this.employee_no = employee_no;
	}
	public String getEmployee_name() {
		return employee_name;
	}
	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}
	public String getEmployee_username() {
		return employee_username;
	}
	public void setEmployee_username(String employee_username) {
		this.employee_username = employee_username;
	}
	public String getEmployee_password() {
		return employee_password;
	}
	public void setEmployee_password(String employee_password) {
		this.employee_password = employee_password;
	}
	
	
}
