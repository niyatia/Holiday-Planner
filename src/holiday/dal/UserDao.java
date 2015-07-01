package holiday.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import holiday.model.*;



/** 
 * Data access object (DAO) class to interact with the underlying User table in your MySQL
 * instance. This is used to store {@link User} into your MySQL instance and retrieve 
 * {@link User} from MySQL instance.
 */
public class UserDao extends PersonDao {
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static UserDao instance = null;
	protected UserDao() {
		connectionManager = new ConnectionManager();
	}
	public static UserDao getInstance() {
		if(instance == null) {
			instance = new UserDao();
		}
		return instance;
	}

	/**
	 * Save the User instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public User create(User user) throws SQLException {
		
		super.create(user);
		
		String insertUser = "INSERT INTO User(UserName, Dob, Phone) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUser);
			
			insertStmt.setString(1, user.getUserName());			
			insertStmt.setTimestamp(2, new Timestamp(user.getDob().getTime()));			
			insertStmt.setString(3, user.getPhone());			
			insertStmt.executeUpdate();
			
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
		}
	}

	/**
	 * Delete the User instance.
	 * This runs a DELETE statement.
	 */
	public User delete(User User) throws SQLException {
		String deleteUser = "DELETE FROM User WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteUser);
			deleteStmt.setString(1, User.getUserName());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the User instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}

	/**
	 * @param userName
	 * @return single User instance
	 * @throws SQLException
	 */
	public User getUserByUserName(String userName) throws SQLException {
		
		Person person = super.getPersonByUserName(userName);
		
		String selectUser = "SELECT UserName, Dob, Phone FROM User WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setString(1, userName);
			
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				String resultUserName = results.getString("UserName");
				Date dob = results.getDate("Dob");
				String phone = results.getString("Phone");
				User user = new User(resultUserName, person.getFirstName(), person.getLastName(), person.getEmail(), person.getPassword(), phone, dob);
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}

	/**
	 * @param userName
	 * @return single User instance
	 * @throws SQLException
	 */
	public List<User> getUserByFirstName(String firstName) throws SQLException {		
		List<User> users = new ArrayList<User>();
		String selectUser = "SELECT User.UserName AS UserName, FirstName, LastName, Password, Email, "
				+ "Dob, Phone "
				+ "FROM User INNER JOIN Person "
				+ "ON User.UserName = Person.UserName "
				+ "WHERE Person.FirstName = ?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setString(1, firstName);
			
			results = selectStmt.executeQuery();
			
			while(results.next()) {
				String userName = results.getString("UserName");
				String lastName = results.getString("LastName");
				String password = results.getString("Password");
				String email = results.getString("Email");				
				Date dob = results.getDate("Dob");
				String phone = results.getString("Phone");
				
				User user = new User(userName, firstName, lastName, email, password, phone, dob);				
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
	}

	/**
	 * Update the User instance.
	 * This runs a UPDATE statement.
	 */
	public User updateUser(User user, String firstName, String lastName, String email, String phone) throws SQLException {
		
		super.updatePerson(user, firstName, lastName, email);
		
		String updateShippingAddress = "UPDATE User SET Phone=? WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateShippingAddress);
			updateStmt.setString(1, phone);
			updateStmt.setString(2, user.getUserName());
			
			updateStmt.executeUpdate();
			
			// Update the user params before returning to the caller.
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setPhone(phone);
						
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
}
