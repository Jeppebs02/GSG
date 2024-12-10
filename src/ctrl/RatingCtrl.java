	package ctrl;

	import java.time.LocalDateTime;
import java.util.List;

import dal.RatingDB;
import model.Employee;
import model.Rating;

public class RatingCtrl {


		private RatingDB rdb;
		
		
		public RatingCtrl() {
			rdb = new RatingDB();
		}
		
		public Rating createRating(int securityScore, String securityComment, int serviceScore, String serviceComment, Employee empolyee) {
			
			return new Rating(securityScore, securityComment, serviceScore, serviceComment, empolyee);
			
			
		}
		//TODO
		public List<Rating> findRatingsByReportID(int reportID) {
			rdb.findRatingsByReportID(reportID);
			return null;
		}
		
		//TODO
		public void deleteRatingByRatingID(int ratingID) {
			
			rdb.deleteRatingByRatingID(ratingID);
		}
		
	}

