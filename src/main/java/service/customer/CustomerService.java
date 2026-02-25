package service.customer;

import model.Customer;

public interface CustomerService {

	String generateCustomerNo();
	void AddCustomer(Customer customer);
	Customer find_customer_by_username_and_password(String username,String password);
	boolean find_username(String username);
}
