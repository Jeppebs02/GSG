package dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import model.Rating;
import model.Report;

public interface RatingDBIF {

	
	
	public Rating saveRatingToDB(Rating rating, int reportNr) throws Exception;
	public void deleteRatingByRatingID(int ratingID) throws Exception;
	public List<Rating> findRatingsByReportID(int reportID) throws Exception;
	public Rating createRatingFromResultSet(ResultSet rs) throws Exception;
	public ResultSet findRatingByReportID(int reportID) throws Exception;

}
