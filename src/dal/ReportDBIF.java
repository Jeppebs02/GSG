package dal;

import model.Report;

public interface ReportDBIF {
	
	public boolean saveReportToDb(Report report) throws Exception;

}
