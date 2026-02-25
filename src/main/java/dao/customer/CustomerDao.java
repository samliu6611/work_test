package dao.customer;

import java.util.List;

import model.Customer;

public interface CustomerDao {
	int getCustomerCount();
	//create
	void add(Customer custmoer);
	
	//read
	List<Customer> select_by_username_and_password(String username,String password);
	List<Customer> select_by_username(String username);
	}
