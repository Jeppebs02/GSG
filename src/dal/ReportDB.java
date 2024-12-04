package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import model.Report;

public class ReportDB implements ReportDBIF {
	
	private DBConnection dbConnection = DBConnection.getInstance();
	private Connection connection = DBConnection.getInstance().getConnection();
	
	// SQL String and prepared statement
	private static final String save_report_to_db ="INSERT INTO [Report] (RejectionAge, RejectionAttitude, RejectionAlternative, AlternativeRemarks, EmployeeSignature, CustomerSignature, Task_ID) VALUES (?,?,?,?,?,?,?)";
	private PreparedStatement saveReportToDB;
	
	@Override
	public boolean saveReportToDb(Report report) throws Exception {
		
		try {
			
			//Prepare statement and set parameters
			saveReportToDB = connection.prepareStatement(save_report_to_db, Statement.RETURN_GENERATED_KEYS);
			saveReportToDB.setInt(1, report.getRejectionsAge());
			saveReportToDB.setInt(2, report.getRejectionsAttitude());
			saveReportToDB.setInt(3, report.getRejectionsAlternative());
			saveReportToDB.setString(4, report.getAlternativeRemarks());
			saveReportToDB.setString(5, report.getEmployeeSignature());
			saveReportToDB.setString(6, report.getCustomerSignature());
			saveReportToDB.setInt(7, report.getTaskID());
			
			//commit transaction
			int generatedKey = dbConnection.executeSqlInsertWithIdentityPS(saveReportToDB);
			System.out.println("Generated Key: " + generatedKey);
		} catch (Exception e) {
			//Print stacktrace if error
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}

}
