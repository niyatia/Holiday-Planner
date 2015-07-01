package holiday.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import holiday.model.*;



/** 
 * Data access object (DAO) class to interact with the underlying Place table in your MySQL
 * instance. This is used to store {@link Place} into your MySQL instance and retrieve 
 * {@link Place} from MySQL instance.
 */
public class PlaceDao {
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static PlaceDao instance = null;
	protected PlaceDao() {
		connectionManager = new ConnectionManager();
	}
	public static PlaceDao getInstance() {
		if(instance == null) {
			instance = new PlaceDao();
		}
		return instance;
	}

	public Place create(Place place) throws SQLException {
		String createCity = "INSERT into Place (PlaceName, Address, ZipCode, Description, PlacePhoto, CityName, UserName) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement createStmt = null;
		
		connection = connectionManager.getConnection();
		
		createStmt = connection.prepareStatement(createCity, Statement.RETURN_GENERATED_KEYS);
		createStmt.setString(1, place.getPlaceName());
		createStmt.setString(2, place.getAddress());
		createStmt.setString(3, place.getZipCode());
		createStmt.setString(4, place.getDescription());
		createStmt.setString(5, place.getPhoto());
		createStmt.setString(6, place.getCity().getCityName());
		createStmt.setString(7, place.getAdmin().getUserName());
		
		createStmt.executeUpdate();
		ResultSet resultKeys = createStmt.getGeneratedKeys();			
		int placeId = -1;
		if(resultKeys.next()) {	
			placeId = resultKeys.getInt(1);			
		} else {
			throw new SQLException("Unable to retrieve auto-generated key.");
		}
		
		place.setPlaceId(placeId);
				
		return place;
	}
	/**
	 * Delete the Place instance.
	 * This runs a DELETE statement.
	 */
	public Place delete(Place Place) throws SQLException {
		String deletePlace = "DELETE FROM Place WHERE PlaceID = ?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePlace);
			deleteStmt.setInt(1, Place.getPlaceId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Place instance.
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
	 * @param placeName
	 * @return single Place instance
	 * @throws SQLException
	 */
//	public Place getPlaceByPlaceName(String placeName) throws SQLException {
//		String selectPlace = "SELECT PlaceName,Address, Zipcode, Description, Photo, CityName, UserName, Timestamp FROM Place "
//				+ "WHERE City = ?;";
//		Connection connection = null;
//		PreparedStatement selectStmt = null;
//		ResultSet results = null;
//		UserDao userDao = UserDao.getInstance();
//		CityDao cityDao = CityDao.getInstance();
//		try {
//			connection = connectionManager.getConnection();
//			selectStmt = connection.prepareStatement(selectPlace);
//			selectStmt.setString(1, placeName);
//			
//			results = selectStmt.executeQuery();
//			
//			if(results.next()) {
//				String resultPlaceName = results.getString("PlaceName");
//				String address = results.getString("Address");
//				String zipCode = results.getString("ZipCode");
//				String description = results.getString("Description");
//				String photo = results.getString("Photo");
//				City resultCity = cityDao.getCityByCityName(results.getString("CityName"));
//				Date timestamp = results.getDate("Timestamp");
//				String userName = results.getString("UserName");
//				User user = userDao.getUserByUserName(userName);
//				
//				Place place = new Place(resultPlaceName, address, zipCode, description, photo, resultCity, user, timestamp);
//				return place;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			if(connection != null) {
//				connection.close();
//			}
//			if(selectStmt != null) {
//				selectStmt.close();
//			}
//			if(results != null) {
//				results.close();
//			}
//		}
//		return null;
//	}	

	
	 /* @param placeName
	 * @return List of Place instance
	 * @throws SQLException
	 */
	public List<Place> getPlaceList(String city) throws SQLException {
		String selectPlace = "SELECT PlaceID, PlaceName, Address, Zipcode, Description, PlacePhoto, CityName, UserName, Timestamp FROM Place "
				+ "WHERE CityName = ?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Place> placeList = new ArrayList<Place>();
		AdminDao adminDao = AdminDao.getInstance();
		CityDao cityDao = CityDao.getInstance();
		try {
			connection = connectionManager.getConnection();			
			selectStmt = connection.prepareStatement(selectPlace);
			selectStmt.setString(1, city);
			results = selectStmt.executeQuery();
			
			City resultCity = cityDao.getCityByCityName(city);
			while(results.next()) {
				int placeId = results.getInt("PlaceID");
				String resultPlaceName = results.getString("PlaceName");
				String address = results.getString("Address");
				String zipCode = results.getString("ZipCode");
				String description = results.getString("Description");
				String photo = results.getString("PlacePhoto");
				
				Date timestamp = results.getDate("Timestamp");
				String userName = results.getString("UserName");
				Admin admin = adminDao.getAdminByUserName(userName);
				
				Place place = new Place(placeId, resultPlaceName, address, zipCode, description, photo, resultCity, admin, timestamp);
				placeList.add(place);				
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
		return placeList;
	}	

}
