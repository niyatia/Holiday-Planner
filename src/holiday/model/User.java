package holiday.model;


import java.util.Date;

public class User extends Person {
	private String phone;
	private Date dob;

	public User(String userName, String firstName, String lastName,
			String email, String password, String phone, Date dob) {
		super(userName, firstName, lastName, email, password);
		this.phone = phone;
		this.dob = dob;
	}
	
	public User(String userName, String firstName, String lastName,
			String email, String password) {
		super(userName, firstName, lastName, email, password);
		
	}
	
	public User(String userName){
		super(userName);
	}
	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

}
