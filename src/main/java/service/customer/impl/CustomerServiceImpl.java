package service.customer.impl;

import java.util.List;

import dao.customer.impl.CustomerDaoImpl;
import model.Customer;
import service.customer.CustomerService;

public class CustomerServiceImpl implements CustomerService{

	public static void main(String[] args) {
		System.out.println(new CustomerServiceImpl().find_username("ruru001"));

	}

	CustomerDaoImpl customerDaoImpl=new CustomerDaoImpl();

	@Override
	public void AddCustomer(Customer customer) {
		customerDaoImpl.add(customer);
		
	}

	@Override
	public Customer find_customer_by_username_and_password(String username, String password) {
		Customer customer=null;
		List<Customer> l=customerDaoImpl.select_by_username_and_password(username, password);
		
		if(l.size()!=0)
		{
			customer=l.get(0);
		}
		
		return customer;
	}

	@Override
	public boolean find_username(String username) {
		List<Customer> l=customerDaoImpl.select_by_username(username);
		
		return !l.isEmpty();
	}
	@Override
	public String generateCustomerNo() {
		// 1. 去 DAO 查目前有幾個人
		int count = customerDaoImpl.getCustomerCount();
		
		// 2. 組合字串：小寫 c + 3位數字 (例如目前有 3 人，就會產生 c004)
		return "c" + String.format("%03d", count + 1);
	}
	
}
