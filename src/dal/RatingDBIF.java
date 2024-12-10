package dal;

import model.Rating;

public interface RatingDBIF {
	
	public void saveRatingToDB() throws Exception;
	public Rating updateRating() throws Exception;

}
