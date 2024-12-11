package ctrl;

import java.sql.SQLException;
import java.util.List;

import dal.RatingDB;
import model.Employee;
import model.Rating;

/**
 * The {@code RatingCtrl} class provides methods for creating and managing
 * {@link Rating} objects, including retrieving and deleting ratings from the
 * database. It serves as a controller interacting with the data access layer.
 */
public class RatingCtrl {

	private RatingDB rdb;

	/**
	 * Constructs a new {@code RatingCtrl} instance and initializes the data access
	 * object.
	 *
	 * @throws SQLException if a database access error occurs.
	 */
	public RatingCtrl() throws SQLException {
		rdb = new RatingDB();
	}

	/**
	 * Creates a new {@link Rating} object with the given scores, comments, and
	 * associated employee. Note that this method only creates the Rating object
	 * locally; it does not persist it to the database.
	 * 
	 * @param securityScore   the security score of the rating.
	 * @param securityComment a textual comment regarding security.
	 * @param serviceScore    the service score of the rating.
	 * @param serviceComment  a textual comment regarding the service.
	 * @param empolyee        the {@link Employee} associated with the rating.
	 * @return a new {@link Rating} instance.
	 */
	public Rating createRating(int securityScore, String securityComment, int serviceScore, String serviceComment,
			Employee empolyee) {
		return new Rating(securityScore, securityComment, serviceScore, serviceComment, empolyee);
	}

	/**
	 * Retrieves all {@link Rating} objects associated with a given report ID.
	 * 
	 * @param reportID the unique identifier of the report whose ratings are to be
	 *                 retrieved.
	 * @return a {@link List} of {@link Rating} objects related to the specified
	 *         report.
	 * @throws Exception if a data retrieval error occurs or the report is not
	 *                   found.
	 */
	public List<Rating> findRatingsByReportID(int reportID) throws Exception {
		return rdb.findRatingsByReportID(reportID);
	}

	/**
	 * Deletes a {@link Rating} from the database based on the given rating ID.
	 * 
	 * @param ratingID the unique identifier of the rating to be deleted.
	 * @throws Exception if a data access error occurs or the rating is not found.
	 */
	public void deleteRatingByRatingID(int ratingID) throws Exception {
		rdb.deleteRatingByRatingID(ratingID);
	}

}
