package ctrl;

import java.sql.SQLException;
import java.util.ArrayList;

import dal.AlarmDB;
import dal.ReportDB;
import model.Alarm;
import model.Rating;
import model.Report;
import model.Task;

public class ReportCtrl {
	
	
	ReportDB repDB;
	AlarmCtrl ac;
	RatingCtrl rc;
	
	
	public ReportCtrl() throws SQLException {
		repDB = new ReportDB();
		ac = new AlarmCtrl();
		rc = new RatingCtrl();
		
	}
	
	
	
	/**
     * Creates a new {@link Report} object with the specified parameters.
     * 
     * @param rejectionsAge the number of age-related rejections.
     * @param rejectionsAttitude the number of attitude-related rejections.
     * @param rejectionsAlternative the number of alternative-related rejections.
     * @param alternativeRemarks additional remarks related to alternatives.
     * @param employeeSignature the signature of the employee.
     * @param customerSignature the signature of the customer.
     * @return a newly created {@link Report} object.
     */
    public Report createReport(int rejectionsAge, int rejectionsAttitude, int rejectionsAlternative, String alternativeRemarks, String employeeSignature, String customerSignature) {
        return new Report(rejectionsAge, rejectionsAttitude, rejectionsAlternative, alternativeRemarks, employeeSignature, customerSignature);
    }
    
    public void saveReport(Task task, int rejectionsAge, int rejectionsAttitude, int rejectionsAlternative, String alternativeRemarks,
			String employeeSignature, String customerSignature) throws Exception {
    	ReportDB repDB = new ReportDB();
    	// Create a report with default values
        Report report = createReport(rejectionsAge, rejectionsAttitude, rejectionsAlternative, alternativeRemarks, employeeSignature, customerSignature);
        
        // Associate the report with the saved task and save the report
        report.setTaskID(task.getTaskID());
        repDB.saveReportToDb(report);
    }
    
    public Report findReportByTaskID(int taskID) throws Exception {
    	
    	Report report = repDB.findReportByTaskID(taskID);
    	
		ArrayList<Alarm> alarms = (ArrayList<Alarm>) ac.findAlarmsByReportID(report.getReportNr()); 
    	//TODO find alarm og ratings også
		alarms.forEach(a -> report.addAlarms(a));
		
		ArrayList<Rating> ratings = (ArrayList<Rating>) rc.findRatingsByReportID(report.getReportNr());
		ratings.forEach(r -> report.addRatings(r));
    	
		//TODO Find extra alarms comments
		
    	return report;
    	
    }
    
    public void deleteReportByTaskID(int taskID) throws Exception {
    	
    	
    	Report report = findReportByTaskID(taskID);
    	
    	report.getAlarms().forEach(a -> {
			try {
				ac.deleteAlarmByAlarmID(a.getAlarmID());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
    	
    	report.getRatings().forEach(r -> {
			try {
				rc.deleteRatingByRatingID(r.getRatingID());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
    	
    	repDB.deleteReportByTaskID(taskID);
    	
    	
    }
	
}
