package dao.employee;

import java.util.List;

import model.Employee;

public interface EmployeeDao {
	//create
	void add(Employee employee);
	
	//read
	List<Employee>select_by_employee_username_and_employee_password(String username,String password);
	List<Employee>select_by_employee_username(String username);
}
