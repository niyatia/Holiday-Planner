package holiday.model;


import java.util.Date;

public class Admin extends Person {

	private Date lastLogin;

	public Admin(String userName, String firstName, String lastName,
			String email, String password, Date lastLogin) {
		super(userName, firstName, lastName, email, password);		
		this.lastLogin = lastLogin;
	}


	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

}
