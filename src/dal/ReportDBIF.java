package dal;

import java.sql.ResultSet;

import model.Report;

public interface ReportDBIF {
	
	public void saveReportToDb(Report report) throws Exception;
	public Report findReportByTaskID(int taskID) throws Exception;
	public boolean deleteReportByTaskID(int reportID) throws Exception; 
	public Report createReportFromResultSet(ResultSet rs) throws Exception;
	public void updateReportByTaskID( int rejectionAge, int rejectionAttitude, int rejectionAlternative, String alternativeRemarks, String employeeSignature, String customerSignature, int taskID) throws Exception;
}
