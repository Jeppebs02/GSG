package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Rating;
import model.Report;

public class RatingDB implements RatingDBIF {
	
	private Connection connection;
	private DBConnection dbConnection;
	
	private static final String create_rating = "INSERT INTO [Rating] (SecurityScore,SecurityComment,ServiceScore,ServiceComment,ReportNr,Employee_ID) VALUES(?,?,?,?,?,?);";
	
	private PreparedStatement createRating;
	

	public RatingDB() throws SQLException {
		super();
		connection = DBConnection.getInstance().getConnection();
		dbConnection = DBConnection.getInstance();
		
		createRating = connection.prepareStatement(create_rating);
	}

	@Override
	public void saveRatingToDB(Rating rating, int reportNr) throws Exception {
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
	            task.setTaskID(taskID);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
		
	}

	@Override
	public void deleteRatingByRatingID(int ratingID) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rating findRatingByReportID(int reportID) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
