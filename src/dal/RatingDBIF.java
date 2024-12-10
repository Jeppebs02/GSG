package dal;

import java.sql.Connection;

import model.Rating;
import model.Report;

public interface RatingDBIF {

	
	
	public void saveRatingToDB(Rating rating, int reportNr) throws Exception;
	public void deleteRatingByRatingID(int ratingID) throws Exception;
	public Rating findRatingByReportID(int reportID) throws Exception;

}
