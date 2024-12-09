package ctrl;

import dal.ReportDB;
import model.Report;
import model.Task;

public class ReportCtrl {
	
	
	ReportDB repDB = new ReportDB();
	
	
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
    
    public void saveReport(Task task) throws Exception {
    	ReportDB repDB = new ReportDB();
    	// Create a report with default values
        Report report = createReport(0, 0, 0, "", "", "");
        
        // Associate the report with the saved task and save the report
        report.setTaskID(task.getTaskID());
        repDB.saveReportToDb(report);
    }
    //TODO fix this ?? skal vi have den her?
    public Report updateReport(int taskID, String alternativeRemarks) {
    	
    	
    	Report report = repDB.findReportByTaskID(taskID);
    	
    	report.setAlternativeRemarks(alternativeRemarks);
    	
    	//push to DB after changes
    	
    	return null;
    }
    
    public Report findReportByTaskID(int taskID) {
    	
    	Report report = repDB.findReportByTaskID(taskID);
    	
    	//TODO find alarm og ratings også
    	
    	return null;
    	
    }
    
    public void deleteReportByTaskID(int taskID) {
    	
    	repDB.deleteReportByTaskID(taskID);
    	//TODO Slet tilhørende alarmer og ratings
    }
	
}
