package model;

import java.io.Serializable;

public class Customer implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private int id; 
    private String customer_no;   
    private String customer_name; 
    private String username;
    private String password;

    public Customer() {}

    public Customer(String customer_no, String customer_name, String username, String password) {
        this.customer_no = customer_no;
        this.customer_name = customer_name;
        this.username = username;
        this.password = password;
    }

   

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCustomer_no() { return customer_no; }
    public void setCustomer_no(String customer_no) { this.customer_no = customer_no; }

    public String getCustomer_name() { return customer_name; }
    public void setCustomer_name(String customer_name) { this.customer_name = customer_name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}