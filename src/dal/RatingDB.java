package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Rating;
import model.Report;
import model.Task;

public class RatingDB implements RatingDBIF {
	
	private Connection connection;
	private DBConnection dbConnection;
	
	private static final String save_rating = "INSERT INTO [Rating] (SecurityScore,SecurityComment,ServiceScore,ServiceComment,ReportNr,Employee_ID) VALUES(?,?,?,?,?,?);";
	private static final String delete_rating_by_rating_id = "DELETE FROM [Rating] WHERE ID = ?;";
	
	private PreparedStatement saveRating;
	private PreparedStatement deleteRatingByRatingID;

	public RatingDB() throws SQLException {
		super();
		connection = DBConnection.getInstance().getConnection();
		dbConnection = DBConnection.getInstance();
		
		saveRating = connection.prepareStatement(save_rating);
		deleteRatingByRatingID = connection.prepareStatement(delete_rating_by_rating_id);
	}

	
	
	
	 /**
     * Saves a rating object to database.
     * 
     * @param rating - a rating object you want to save.
     * @param reportNr - the reportNr of the report you want to save the rating to.
     * @return the newly created rating entry in the db, as a Rating Object.
     * @throws Exception.
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
     * Deletes a rating object from the database.
     * 
     * @param ratingID - the ID of the rating you want to delete.
     * @return void.
     * @throws Exception.
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




	@Override
	public List<Rating> findRatingsByReportID(int reportID) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public Rating createRatingFromResultSet(ResultSet rs) throws Exception {
		// TODO Auto-generated method stub
		
		Rating rating;
		return rating = new Rating(rs.getInt("SecurityScore"), rs.getString("SecurityComment"), 0, null, null);
		
	}



}
