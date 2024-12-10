	package ctrl;

	import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import dal.RatingDB;
import model.Employee;
import model.Rating;

public class RatingCtrl {


		private RatingDB rdb;
		
		
		public RatingCtrl() throws SQLException {
			rdb = new RatingDB();
		}
		
		public Rating createRating(int securityScore, String securityComment, int serviceScore, String serviceComment, Employee empolyee) {
			
			return new Rating(securityScore, securityComment, serviceScore, serviceComment, empolyee);
			
			
		}
		
		public List<Rating> findRatingsByReportID(int reportID) throws Exception {
			return rdb.findRatingsByReportID(reportID);
			
		}
		
		
		public void deleteRatingByRatingID(int ratingID) throws Exception {
			
			rdb.deleteRatingByRatingID(ratingID);
		}
		
	}

