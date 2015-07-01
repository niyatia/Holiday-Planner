package holiday.dal;

import holiday.model.City;
import holiday.model.Review;
import holiday.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Data access object (DAO) class to interact with the underlying Review table in your MySQL
 * instance. This is used to store {@link Review} into your MySQL instance and retrieve 
 * {@link Review} from MySQL instance.
 */
public class ReviewDao {
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static ReviewDao instance = null;
	protected ReviewDao() {
		connectionManager = new ConnectionManager();
	}
	public static ReviewDao getInstance() {
		if(instance == null) {
			instance = new ReviewDao();
		}
		return instance;
	}

	public Review create(Review review) throws SQLException {
		String createReview = "INSERT into REVIEW (Review, CityName, Username) VALUES (?, ?, ?);";
		Connection connection = null;
		PreparedStatement createStmt = null;
		
		connection = connectionManager.getConnection();
		
		createStmt = connection.prepareStatement(createReview, Statement.RETURN_GENERATED_KEYS);
		createStmt.setString(1, review.getReview());
		createStmt.setString(2, review.getCityName().getCityName());
		createStmt.setString(3, review.getUserName().getUserName());
		
		createStmt.executeUpdate();
		
		ResultSet resultKeys = createStmt.getGeneratedKeys();
		int reviewId = -1;
		if(resultKeys.next()) {
			reviewId = resultKeys.getInt(1);			
		} else {
			throw new SQLException("Unable to retrieve auto-generated key.");
		}
		review.setReviewId(reviewId);		
		
		return review;
	}
	
	/**
	 * Delete the Review instance.
	 * This runs a DELETE statement.
	 */
	public Review delete(Review review) throws SQLException {
		String deleteReview = "DELETE FROM Review WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReview);
			deleteStmt.setInt(1, review.getReviewId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Review instance.
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
	 * @return List of Review instance
	 * @throws SQLException
	 */
	public List<Review> getReviewByUser(User user) throws SQLException {
		List<Review> reviews = new ArrayList<Review>();
		String selectReview = "SELECT ReviewId,CityName,UserName,Review, Date  FROM Review WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		CityDao citydao = CityDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setString(1, user.getUserName());
			
			results = selectStmt.executeQuery();
			
			while(results.next()) {
				int reviewId = results.getInt("ReviewId");
				String reviewcontent = results.getString("Review");
//				String resultUserName = results.getString("UserName");
				String cityName = results.getString("CityName");					
				City city = citydao.getCityByCityName(cityName);
				Date date = results.getDate("Date");
				Review review = new Review(reviewId, reviewcontent, city, user, date);
				reviews.add(review);				
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
		return reviews;
	}
	
	
}
