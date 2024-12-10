	package ctrl;

	import java.time.LocalDateTime;

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
		public void findRatingsByReportID(int reportID) {
			rdb.findRatingsByReportID(reportID);
		}
		
		//TODO
		public void deleteRatingsByReportID(int ratingID) {
			
			rdb.deleteRatingByRatingID(ratingID);
		}
		
	}

}
