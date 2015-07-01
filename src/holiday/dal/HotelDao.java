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
 * Data access object (DAO) class to interact with the underlying Hotel table in your MySQL
 * instance. This is used to store {@link Hotel} into your MySQL instance and retrieve 
 * {@link Hotel} from MySQL instance.
 */
public class HotelDao {
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static HotelDao instance = null;
	protected HotelDao() {
		connectionManager = new ConnectionManager();
	}
	public static HotelDao getInstance() {
		if(instance == null) {
			instance = new HotelDao();
		}
		return instance;
	}
	

	public Hotel create(Hotel hotel) throws SQLException {
		String createCity = "INSERT into Hotel (HotelName, Address, ZipCode, Phone, Website, CityName, UserName) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement createStmt = null;
		
		connection = connectionManager.getConnection();
		
		createStmt = connection.prepareStatement(createCity, Statement.RETURN_GENERATED_KEYS);
		createStmt.setString(1, hotel.getHotelName());
		createStmt.setString(2, hotel.getAddress());
		createStmt.setString(3, hotel.getZipCode());
		createStmt.setString(4, hotel.getPhone());
		createStmt.setString(5, hotel.getWebsite());
		createStmt.setString(6, hotel.getCity().getCityName());
		createStmt.setString(7, hotel.getAdmin().getUserName());
		
		createStmt.executeUpdate();
		ResultSet resultKeys = createStmt.getGeneratedKeys();		
		
		int hotelId = -1;
		if(resultKeys.next()) {	
			hotelId = resultKeys.getInt(1);
			
		} else {
			throw new SQLException("Unable to retrieve auto-generated key.");
		}
		
		hotel.setHotelId(hotelId);
		
		return hotel;
	}

	/**
	 * Delete the Hotel instance.
	 * This runs a DELETE statement.
	 */
	public Hotel delete(Hotel Hotel) throws SQLException {
		String deleteHotel = "DELETE FROM Hotel WHERE HotelID = ?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteHotel);
			deleteStmt.setInt(1, Hotel.getHotelId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Hotel instance.
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
	 * @param hotelName
	 * @return single Hotel instance
	 * @throws SQLException
	 */
	public Hotel getHotelByHotelId(int hotelId) throws SQLException {
		String selectHotel = "SELECT HotelName,Address, Zipcode, Phone, Website, CityName, UserName, Timestamp FROM Hotel "
				+ "WHERE HotelID = ?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		AdminDao adminDao = AdminDao.getInstance();
		CityDao cityDao = CityDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectHotel);
			selectStmt.setInt(1, hotelId);
			
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				String resultHotelName = results.getString("HotelName");
				String address = results.getString("Address");
				String zipCode = results.getString("ZipCode");
				String phone = results.getString("Phone");
				String website = results.getString("Website");					
				City resultCity = cityDao.getCityByCityName(results.getString("CityName"));
				Date timestamp = results.getDate("Timestamp");
				String userName = results.getString("UserName");
				Admin admin = adminDao.getAdminByUserName(userName);
				
				Hotel hotel = new Hotel(hotelId, resultHotelName, address, zipCode, phone, website, resultCity, admin, timestamp);
				return hotel;
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

	
	 /* @param hotelName
	 * @return List of Hotel instance
	 * @throws SQLException
	 */
	public List<Hotel> getHotelList(String city) throws SQLException {
		String selectHotel = "SELECT HotelID, HotelName, Address, Zipcode, Phone, Website, CityName, UserName, Timestamp FROM Hotel "
				+ "WHERE CityName = ?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Hotel> hotelList = new ArrayList<Hotel>();
		AdminDao adminDao = AdminDao.getInstance();
		CityDao cityDao = CityDao.getInstance();
		try {
			connection = connectionManager.getConnection();			
			selectStmt = connection.prepareStatement(selectHotel);
			selectStmt.setString(1, city);
			results = selectStmt.executeQuery();
			City resultCity = cityDao.getCityByCityName(city);
			while(results.next()) {
				int hotelId = results.getInt("HotelID");
				String resultHotelName = results.getString("HotelName");
				String address = results.getString("Address");
				String zipCode = results.getString("ZipCode");
				String phone = results.getString("Phone");
				String website = results.getString("Website");				
				Date timestamp = results.getDate("Timestamp");
				String userName = results.getString("UserName");
				Admin admin = adminDao.getAdminByUserName(userName);
				
				Hotel hotel = new Hotel(hotelId, resultHotelName, address, zipCode, phone, website, resultCity, admin, timestamp);
				hotelList.add(hotel);				
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
		return hotelList;
	}	

}
