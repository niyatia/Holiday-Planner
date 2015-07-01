package holiday.dal;

import holiday.model.Admin;
import holiday.model.Person;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AdminDao extends PersonDao{
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.
	private static AdminDao instance = null;

	protected AdminDao() {
		connectionManager = new ConnectionManager();
	}

	public static AdminDao getInstance() {
		if (instance == null) {
			instance = new AdminDao();
		}
		return instance;
	}
	
	/**
	 * Save the Admin instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Admin create(Admin user) throws SQLException {
		
		super.create(user);
		
		String insertUser = "INSERT INTO Admin(Username, LastLoginDate) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUser);
			
			insertStmt.setString(1, user.getUserName());			
			insertStmt.setTimestamp(2, new Timestamp(user.getLastLogin().getTime()));		
						
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
	 * @param userName
	 * @return single User instance
	 * @throws SQLException
	 */
	public Admin getAdminByUserName(String userName) throws SQLException {
		
		Person person = super.getPersonByUserName(userName);
		
		String selectAdmin = "SELECT LastLoginDate FROM Admin WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAdmin);
			selectStmt.setString(1, userName);
			
			results = selectStmt.executeQuery();
			
			if(results.next()) {			
				
				Date lastLoginDate = results.getDate("LastLoginDate");
				Admin admin = new Admin(userName, person.getFirstName(), person.getLastName(), person.getEmail(), person.getPassword(), lastLoginDate);				
				return admin;
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
}
