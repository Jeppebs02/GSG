package dal;

import java.sql.ResultSet;

import model.Report;

public interface ReportDBIF {
	
	public void saveReportToDb(Report report) throws Exception;
	public Report findReportByTaskID(int taskID) throws Exception;
	public void deleteReportByTaskID(int reportID) throws Exception; 
	public Report createReportFromResultSet(ResultSet rs) throws Exception;
}
