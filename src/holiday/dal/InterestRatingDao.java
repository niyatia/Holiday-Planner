/**
 * 
 */
package holiday.dal;

import holiday.model.InterestRating;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Admin
 *
 */
public class InterestRatingDao {
	protected ConnectionManager connectionManager;

	private static InterestRatingDao instance = null;

	protected InterestRatingDao() {
		connectionManager = new ConnectionManager();
	}

	public static InterestRatingDao getInstance() {
		if (instance == null) {
			instance = new InterestRatingDao();
		}
		return instance;
	}

	public InterestRating create(InterestRating interestRating)
			throws SQLException {
		String insertInterestRating = "INSERT INTO InterestRating (Username, CityName, Interest, Rating) VALUES (?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertInterestRating,
					Statement.RETURN_GENERATED_KEYS);

			insertStmt.setString(1, interestRating.getUser().getUserName());
			insertStmt.setString(2, interestRating.getCity().getCityName());
			insertStmt.setString(3, interestRating.getInterest().toString());
			insertStmt.setDouble(4, interestRating.getRating());

			insertStmt.executeUpdate();

			ResultSet resultKey = insertStmt.getGeneratedKeys();
			int ratingId = -1;
			if (resultKey.next()) {
				ratingId = resultKey.getInt(1);
			} else {
				throw new SQLException(
						"Interest Rating ID could not be fetched");
			}

			interestRating.setInterestRatingId(ratingId);

			return interestRating;
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
}
