
public class UpdateLog {

	private int customer;
	private char update;
	private String time;
	
	public UpdateLog(int customer, char update, String time)
	{
		super();
		this.customer = customer;
		this.update = update;
		this.time = time;
	}
	
	public int getCustomer() {
		return customer;
	}
	public void setCustomer(int customer) {
		this.customer = customer;
	}
	public char getUpdate() {
		return update;
	}
	public void setUpdate(char update) {
		this.update = update;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
