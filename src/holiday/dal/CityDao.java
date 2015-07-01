package holiday.dal;

import holiday.model.Admin;
import holiday.model.City;
import holiday.model.Enumerations;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



/** 
 * Data access object (DAO) class to interact with the underlying City table in your MySQL
 * instance. This is used to store {@link City} into your MySQL instance and retrieve 
 * {@link City} from MySQL instance.
 */
public class CityDao {
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static CityDao instance = null;
	protected CityDao() {
		connectionManager = new ConnectionManager();
	}
	public static CityDao getInstance() {
		if(instance == null) {
			instance = new CityDao();
		}
		return instance;
	}
	

	public City create(City city) throws SQLException {
		String createCity = "INSERT into City (CityName, Description, Photo, Region, UserName) "
				+ "VALUES (?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement createStmt = null;
		
		connection = connectionManager.getConnection();
		
		createStmt = connection.prepareStatement(createCity);
		createStmt.setString(1, city.getCityName());
		createStmt.setString(2, city.getDescription());
		createStmt.setString(3, city.getPhoto());
		createStmt.setString(4, city.getRegion().toString());
		createStmt.setString(5, city.getAdmin().getUserName());
		
		createStmt.executeUpdate();
	
		return city;
	}

	/**
	 * Delete the City instance.
	 * This runs a DELETE statement.
	 */
	public City delete(City City) throws SQLException {
		String deleteCity = "DELETE FROM City WHERE CityName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCity);
			deleteStmt.setString(1, City.getCityName());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the City instance.
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
	 * @param cityName
	 * @return single City instance
	 * @throws SQLException
	 */
	public City getCityByCityName(String cityName) throws SQLException {
		String selectCity = "SELECT CityName,Description,Photo, Region, UserName, Timestamp FROM City WHERE CityName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		AdminDao adminDao = AdminDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCity);
			selectStmt.setString(1, cityName);
			
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				String resultCityName = results.getString("CityName");
				String decription = results.getString("Description");
				String photo = results.getString("Photo");
				Enumerations.Region region = Enumerations.Region.valueOf(results.getString("Region").toUpperCase());
				Date timestamp = results.getDate("Timestamp");
				String userName = results.getString("UserName");
				Admin admin = adminDao.getAdminByUserName(userName);
				
				City City = new City(resultCityName, decription, photo, region, admin, timestamp);
				return City;
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

	
	 /* @param cityName
	 * @return List of City instance
	 * @throws SQLException
	 */
	public List<City> getCityList(String region, String interest, String companion) throws SQLException {
		String selectCity = "select city.CityName, "
								+	"city.Description, "
								+	"city.Photo, "	
								+	"city.Region, "
								+   "city.Timestamp, "
								+   "city.Username, "
								+	"companionrating.Companion, "
								+	"interestrating.Interest, "      
								+	"avg(interestrating.Rating) , "
								+	"avg(companionrating.Rating) "
								+ "from city "
								+ "left outer join companionrating on companionrating.CityName = city.CityName "
								+ "left outer join interestrating on interestrating.CityName = city.CityName "
								+ "group by interestrating.Interest, companionrating.Companion "
								+ "having city.Region =? "
								+ "and interestrating.Interest =? "
								+ "and avg(interestrating.Rating) > 3 "
								+ "and companionrating.Companion = ? "
								+ "and avg(companionrating.Rating) > 3 ";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<City> cityList = new ArrayList<City>();
		AdminDao adminDao = AdminDao.getInstance();
		try {
			connection = connectionManager.getConnection();			
			selectStmt = connection.prepareStatement(selectCity);
			selectStmt.setString(1, region);
			selectStmt.setString(2, interest);
			selectStmt.setString(3, companion);
			results = selectStmt.executeQuery();
			
			while(results.next()) {
				String resultCityName = results.getString("CityName");
				String description = results.getString("Description");
				String photo = results.getString("Photo");
				Enumerations.Region resultRegion = Enumerations.Region.valueOf(region.toUpperCase());
				Date timestamp = results.getDate("Timestamp");
				String userName = results.getString("UserName");
				Admin admin = adminDao.getAdminByUserName(userName);
				
				City city = new City(resultCityName, description, photo, resultRegion, admin, timestamp);
				cityList.add(city);				
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
		return cityList;
	}	

}
