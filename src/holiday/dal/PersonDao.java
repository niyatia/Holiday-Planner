package holiday.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import holiday.model.*;

/**
 * Niyati Acharya Data access object (DAO) class to interact with the underlying
 * Person table in your MySQL instance. This is used to store {@link Person}
 * into your MySQL instance and retrieve {@link Person} from MySQL instance.
 */
public class PersonDao {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.
	private static PersonDao instance = null;

	protected PersonDao() {
		connectionManager = new ConnectionManager();
	}

	public static PersonDao getInstance() {
		if (instance == null) {
			instance = new PersonDao();
		}
		return instance;
	}

	/*
	 * @param userName
	 * 
	 * @return single User instance
	 * 
	 * @throws SQLException
	 */
	public Person getPersonByUserName(String userName) throws SQLException {

		String selectUser = "SELECT UserName,FirstName,LastName, Password, Email FROM Person WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setString(1, userName);

			results = selectStmt.executeQuery();

			if (results.next()) {
				String resultUserName = results.getString("UserName");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				String password = results.getString("Password");
				String email = results.getString("Email");
				User User = new User(resultUserName, firstName, lastName,
						email, password);
				return User;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (selectStmt != null) {
				selectStmt.close();
			}
			if (results != null) {
				results.close();
			}
		}
		return null;
	}

	public boolean isAuthenticated (String userName, String password) throws SQLException {

		String selectUser = "SELECT * FROM Person WHERE UserName = ? AND Password = ?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setString(1, userName);
			selectStmt.setString(2, password);
			results = selectStmt.executeQuery();

			if (results.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (selectStmt != null) {
				selectStmt.close();
			}
			if (results != null) {
				results.close();
			}
		}		
	}

	/*
	 * @param userName
	 * 
	 * @return single User instance
	 * 
	 * @throws SQLException
	 */
	public List<Person> getPersonByFirstName(String userName)
			throws SQLException {
		List<Person> persons = new ArrayList<Person>();

		String selectUser = "SELECT UserName,FirstName,LastName, Password, Email FROM Person WHERE FirstName = ?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setString(1, userName);

			results = selectStmt.executeQuery();

			while (results.next()) {
				String resultUserName = results.getString("UserName");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				String password = results.getString("Password");
				String email = results.getString("Email");
				User User = new User(resultUserName, firstName, lastName,
						email, password);
				persons.add(User);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (selectStmt != null) {
				selectStmt.close();
			}
			if (results != null) {
				results.close();
			}
		}
		return persons;
	}

	/**
	 * Save the Person instance by storing it in your MySQL instance. This runs
	 * a INSERT statement.
	 */
	public Person create(Person Person) throws SQLException {

		String insertUser = "INSERT INTO Person(UserName,FirstName,LastName, Password, Email) VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUser);
			// PreparedStatement allows us to substitute specific types into the
			// query template.
			// For an overview, see:
			// http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html.
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// For nullable fields, you can check the property first and then
			// call setNull()
			// as applicable.
			insertStmt.setString(1, Person.getUserName());
			insertStmt.setString(2, Person.getFirstName());
			insertStmt.setString(3, Person.getLastName());
			insertStmt.setString(4, Person.getPassword());
			insertStmt.setString(5, Person.getEmail());
			insertStmt.executeUpdate();

			return Person;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (insertStmt != null) {
				insertStmt.close();
			}
		}
	}

	/**
	 * Delete the Person instance. This runs a DELETE statement.
	 */
	public Person delete(Person Person) throws SQLException {
		String deleteUser = "DELETE FROM Person WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteUser);
			deleteStmt.setString(1, Person.getUserName());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Person
			// instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}

	/**
	 * Update the User instance. This runs a UPDATE statement.
	 */
	public Person updatePerson(Person person, String firstName,
			String lastName, String email) throws SQLException {

		String updatePerson = "UPDATE Person SET FirstName=?, LastName=?, Email=? WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePerson);
			updateStmt.setString(1, firstName);
			updateStmt.setString(2, lastName);
			updateStmt.setString(3, email);
			updateStmt.setString(4, person.getUserName());

			updateStmt.executeUpdate();

			// Update the user params before returning to the caller.
			person.setFirstName(firstName);
			person.setLastName(lastName);
			person.setEmail(email);

			return person;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (updateStmt != null) {
				updateStmt.close();
			}
		}
	}

}
