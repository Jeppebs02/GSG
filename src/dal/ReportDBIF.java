package dal;

import model.Report;

public interface ReportDBIF {
	
	public void saveReportToDb(Report report) throws Exception;
	public Report getReportFromTaskID(int taskID) throws Exception;
	public void deleteFromDB(Report report) throws Exception; 

}
