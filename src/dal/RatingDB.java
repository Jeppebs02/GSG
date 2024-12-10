package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.Rating;
import model.Report;
import model.Task;

public class RatingDB implements RatingDBIF {
	
	private Connection connection;
	private DBConnection dbConnection;
	
	private static final String create_rating = "INSERT INTO [Rating] (SecurityScore,SecurityComment,ServiceScore,ServiceComment,ReportNr,Employee_ID) VALUES(?,?,?,?,?,?);";
	private static final String
	
	private PreparedStatement createRating;
	

	public RatingDB() throws SQLException {
		super();
		connection = DBConnection.getInstance().getConnection();
		dbConnection = DBConnection.getInstance();
		
		createRating = connection.prepareStatement(create_rating);
	}

	
	
	
	 /**
     * Retrieves all tasks from the database that fall within a specified month and year.
     * 
     * <p>This method prepares a SELECT query using the given year and month, executes it, 
     * and constructs a list of {@link Task} objects from the result set.</p>
     * 
     * @param rating - a rating object you want to save.
     * @param reportNr - the reportNr of the report you want to save the rating to.
     * @return the newly created rating entry in the db, as a Rating Object.
     * @throws Exception.
     */
	@Override
	public Rating saveRatingToDB(Rating rating, int reportNr) throws Exception {
		int ratingID;
		createRating.setInt(1, rating.getSecurityScore());
		createRating.setString(2, rating.getSecurityComment());
		createRating.setInt(3, rating.getServiceScore());
		createRating.setString(4, rating.getServiceComment());
		createRating.setInt(5, reportNr);
		createRating.setInt(6, rating.getEmployee().getEmployeeID());
		
		
		 try {
	            // Execute and retrieve generated key
	            ratingID = dbConnection.executeSqlInsertWithIdentityPS(createRating);
	            rating.setRatingID(ratingID);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		 
		 return rating;
	}

	@Override
	public void deleteRatingByRatingID(int ratingID) throws Exception {
		// TODO Auto-generated method stub
		
	}




	@Override
	public List<Rating> findRatingsByReportID(int reportID) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



}
