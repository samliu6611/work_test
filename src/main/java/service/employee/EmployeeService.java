package service.employee;

import model.Employee;

public interface EmployeeService {
	
	void AddEmployee(Employee employee);
	Employee find_employee_by_employee_username_and_employee_password(String employee_username,String employee_password);
	boolean find_employee_username(String employee_username);

}
