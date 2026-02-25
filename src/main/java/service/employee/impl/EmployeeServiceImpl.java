package service.employee.impl;

import java.util.List;

import dao.employee.impl.EmployeeDaoImpl;
import model.Employee;
import service.employee.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService{

	public static void main(String[] args) {
		System.out.println(new EmployeeServiceImpl().find_employee_by_employee_username_and_employee_password("sam1111", "sam12345"));

	}
	
	EmployeeDaoImpl employeeDaoImpl=new EmployeeDaoImpl();
	
	@Override
	public void AddEmployee(Employee employee) {
		employeeDaoImpl.add(employee);
		
	}

	@Override
	public Employee find_employee_by_employee_username_and_employee_password(String employee_username,
			String employee_password) {
		Employee employee=null;
		List<Employee> l=employeeDaoImpl.select_by_employee_username_and_employee_password(employee_username, employee_password);
		
		if(l.size()!=0)
		{
			employee=l.get(0);
		}
		
		return employee;
	}

	@Override
	public boolean find_employee_username(String employee_username) {
		List<Employee> l=employeeDaoImpl.select_by_employee_username(employee_username);
		
		return !l.isEmpty();
	}

}
