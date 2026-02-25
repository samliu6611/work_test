package model;

public class Log {
	private int id;
	private String customer_no;
	private String items_no;
	private int frequency;
	public Log() {
		super();
	}
	public Log(int id, String customer_no, String items_no, int frequency) {
		super();
		this.id = id;
		this.customer_no = customer_no;
		this.items_no = items_no;
		this.frequency = frequency;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCustomer_no() {
		return customer_no;
	}
	public void setCustomer_no(String customer_no) {
		this.customer_no = customer_no;
	}
	public String getItems_no() {
		return items_no;
	}
	public void setItems_no(String items_no) {
		this.items_no = items_no;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
}
