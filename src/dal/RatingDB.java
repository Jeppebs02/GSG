package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Employee;
import model.Rating;

/**
 * The {@code RatingDB} class provides methods for persisting, retrieving, and
 * deleting {@link Rating} objects from the database. It implements the
 * {@link RatingDBIF} interface.
 */
public class RatingDB implements RatingDBIF {

	private Connection connection;
	private DBConnection dbConnection;

	// SQL queries
	private static final String save_rating = "INSERT INTO [Rating] (SecurityScore,SecurityComment,ServiceScore,ServiceComment,ReportNr,Employee_ID) VALUES(?,?,?,?,?,?);";
	private static final String delete_rating_by_rating_id = "DELETE FROM [Rating] WHERE ID = ?;";
	private static final String get_rating_info = "SELECT r.ID AS RatingID, r.SecurityScore, r.SecurityComment, r.ServiceScore, r.ServiceComment, r.ReportNr, "
			+ "r.Employee_ID AS RatingEmployeeID, e.ID AS EmployeeID, e.CPR, e.SecurityClearance, e.AccountNr, e.Certification, "
			+ "e.RegistrationNr, e.Department, eu.ID AS EmployeeUserID, eu.User_ID AS EmployeeUser_UserID, "
			+ "eu.Employee_ID AS EmployeeUser_EmployeeID, u.ID AS UserID, u.UserName, u.Password, u.FirstName, u.LastName, "
			+ "u.Email, u.PhoneNr, u.AccountPrivileges, u.Type, u.Address_ID AS UserAddressID " + "FROM Rating AS r "
			+ "INNER JOIN Employee AS e ON r.Employee_ID = e.ID "
			+ "INNER JOIN EmployeeUser AS eu ON e.ID = eu.Employee_ID " + "INNER JOIN [User] AS u ON eu.User_ID = u.ID "
			+ "WHERE r.ReportNr = ?;";

	// Prepared Statements
	private PreparedStatement saveRating;
	private PreparedStatement deleteRatingByRatingID;
	private PreparedStatement getRatingInfo;
	private EmployeeDB edb;

	/**
	 * Constructs a new {@code RatingDB} object and prepares the SQL statements.
	 * 
	 * @throws SQLException if a database access error occurs.
	 */
	public RatingDB() throws SQLException {
		super();
		connection = DBConnection.getInstance().getConnection();
		dbConnection = DBConnection.getInstance();
		edb = new EmployeeDB();

		saveRating = connection.prepareStatement(save_rating);
		deleteRatingByRatingID = connection.prepareStatement(delete_rating_by_rating_id);
		getRatingInfo = connection.prepareStatement(get_rating_info);
	}

	/**
	 * Saves a {@link Rating} object to the database, associating it with the given
	 * report number.
	 * 
	 * @param rating   the Rating object to be saved.
	 * @param reportNr the report number to associate with the rating.
	 * @return the saved Rating object with its newly assigned database ID.
	 * @throws Exception if an error occurs during the database operation.
	 */
	@Override
	public Rating saveRatingToDB(Rating rating, int reportNr) throws Exception {
		int ratingID;
		saveRating.setInt(1, rating.getSecurityScore());
		saveRating.setString(2, rating.getSecurityComment());
		saveRating.setInt(3, rating.getServiceScore());
		saveRating.setString(4, rating.getServiceComment());
		saveRating.setInt(5, reportNr);
		saveRating.setInt(6, rating.getEmployee().getEmployeeID());

		try {
			// Execute and retrieve generated key
			ratingID = dbConnection.executeSqlInsertWithIdentityPS(saveRating);
			rating.setRatingID(ratingID);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rating;
	}

	/**
	 * Deletes a rating from the database based on its ID.
	 * 
	 * @param ratingID the unique identifier of the rating to be deleted.
	 * @throws Exception if an error occurs during the database operation.
	 */
	@Override
	public void deleteRatingByRatingID(int ratingID) throws Exception {
		deleteRatingByRatingID.setInt(1, ratingID);
		try {
			dbConnection.executeSqlInsertWithIdentityPS(deleteRatingByRatingID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Finds all {@link Rating} objects associated with a given report ID.
	 * 
	 * @param reportID the ID of the report whose ratings are to be retrieved.
	 * @return a List of Ratings associated with the given report ID.
	 * @throws Exception if an error occurs during the database retrieval.
	 */
	@Override
	public List<Rating> findRatingsByReportID(int reportID) throws Exception {
		List<Rating> ratings = new ArrayList<>();
		ResultSet rs = findRatingByReportID(reportID);
		Rating rating = null;

		while (rs.next()) {
			rating = createRatingFromResultSet(rs);
			ratings.add(rating);
		}

		return ratings;
	}

	/**
	 * Creates a {@link Rating} object from the data in the given ResultSet.
	 * 
	 * @param rs the ResultSet containing rating data.
	 * @return a Rating object populated with the data from the ResultSet.
	 * @throws Exception if an error occurs while reading the data or creating the
	 *                   employee.
	 */
	@Override
	public Rating createRatingFromResultSet(ResultSet rs) throws Exception {
		Rating rating;
		Employee employee;

		// Retrieve associated employee by UserID
		employee = edb.findEmployeeByUserID(rs.getInt("UserID"));

		rating = new Rating(rs.getInt("SecurityScore"), rs.getString("SecurityComment"), rs.getInt("ServiceScore"),
				rs.getString("ServiceComment"), employee);

		rating.setRatingID(rs.getInt("RatingID"));

		return rating;
	}

	/**
	 * Retrieves a ResultSet of ratings for a given report ID. This method is used
	 * internally by {@link #findRatingsByReportID(int)}.
	 * 
	 * @param reportID the ID of the report for which ratings are retrieved.
	 * @return a ResultSet containing rating records.
	 * @throws Exception if an error occurs during the database retrieval.
	 */
	@Override
	public ResultSet findRatingByReportID(int reportID) throws Exception {
		getRatingInfo.setInt(1, reportID);
		ResultSet rs = null;

		try {
			rs = dbConnection.getResultSetWithPS(getRatingInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

}
