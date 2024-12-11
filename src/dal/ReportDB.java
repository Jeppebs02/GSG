package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import model.Alarm;
import model.Rating;
import model.Report;

/**
 * The ReportDB class handles database operations related to the {@link Report} entity.
 * 
 * <p>This class uses the {@link DBConnection} singleton to execute queries and manage
 * database transactions. It provides methods for persisting Report objects to the database.</p>
 */
public class ReportDB implements ReportDBIF {
    
    private DBConnection dbConnection = DBConnection.getInstance();
    private Connection connection = DBConnection.getInstance().getConnection();
    
    // SQL Queries
    private static final String delete_report_by_taskid ="DELETE FROM [Report] WHERE [Task_ID] = ?;";
    private static final String find_report_by_taskid = "SELECT * FROM Report INNER JOIN Task ON Report.Task_ID = Task.ID WHERE Task.ID = ?;";
    private static final String save_report_to_db = 
        "INSERT INTO [Report] (RejectionAge, RejectionAttitude, RejectionAlternative, " + 
        "AlternativeRemarks, EmployeeSignature, CustomerSignature, Task_ID) " + 
        "VALUES (?,?,?,?,?,?,?)";
    private static final String update_report_by_taskid = "UPDATE dbo.[Report] SET RejectionAge = ?, RejectionAttitude = ?, RejectionAlternative = ?, AlternativeRemarks = ?, EmployeeSignature = ?, CustomerSignature = ? WHERE Task_ID = ?;";
    private PreparedStatement saveReportToDB;
    private PreparedStatement findReportByTaskID;
    private PreparedStatement deleteReportByTaskID;
    private PreparedStatement updateReportByTaskID;
    
    /**
     * Saves the given Report object to the database.
     * 
     * <p>This method prepares an INSERT statement with all the relevant fields from the 
     * Report object and executes it. It uses the {@link DBConnection#executeSqlInsertWithIdentityPS(PreparedStatement)} 
     * method to return the generated key for the newly inserted record.</p>
     * 
     * <p>If the operation is successful, the method returns true. If an exception occurs 
     * during the operation, it prints the stack trace and returns false.</p>
     * 
     * @param report the {@link Report} object to be saved in the database.
     * @return true if the report was successfully saved, false otherwise.
     * @throws Exception if any database or other error occurs during the operation.
     */
    @Override
    public void saveReportToDb(Report report) throws Exception {
        try {
            // Prepare the statement and set parameters
            saveReportToDB = connection.prepareStatement(save_report_to_db, Statement.RETURN_GENERATED_KEYS);
            saveReportToDB.setInt(1, report.getRejectionsAge());
            saveReportToDB.setInt(2, report.getRejectionsAttitude());
            saveReportToDB.setInt(3, report.getRejectionsAlternative());
            saveReportToDB.setString(4, report.getAlternativeRemarks());
            saveReportToDB.setString(5, report.getEmployeeSignature());
            saveReportToDB.setString(6, report.getCustomerSignature());
            saveReportToDB.setInt(7, report.getTaskID());
            
            // Execute the insert and retrieve the generated key
            int generatedKey = dbConnection.executeSqlInsertWithIdentityPS(saveReportToDB);
            System.out.println("Generated Key: " + generatedKey);
        } catch (Exception e) {
            // Print stack trace if there is an error
            e.printStackTrace();
            
        }
    }
    
    

	@Override
	public Report findReportByTaskID(int taskID) throws Exception {
		Report report = null;
		findReportByTaskID = connection.prepareStatement(find_report_by_taskid);	
		try {
			findReportByTaskID.setInt(1, taskID);
			ResultSet rs = dbConnection.getResultSetWithPS(findReportByTaskID);
			
			rs.next();
			report = createReportFromResultSet(rs);
			
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return report;
	}

	@Override
	public void deleteReportByTaskID(int taskID) throws Exception {
		deleteReportByTaskID = connection.prepareStatement(delete_report_by_taskid);	
		
		try {
			deleteReportByTaskID.setInt(1, taskID);
			dbConnection.executeSqlInsertWithIdentityPS(deleteReportByTaskID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Report createReportFromResultSet(ResultSet rs) throws Exception {
		
		 int taskID = rs.getInt("Task_ID");
	     int reportNr = rs.getInt("ReportNr");
	     int rejectionsAge = rs.getInt("RejectionAge");
	     int rejectionsAttitude = rs.getInt("RejectionAttitude");
	     int rejectionsAlternative =  rs.getInt("RejectionAlternative");;
	     String alternativeRemarks = rs.getString("AlternativeRemarks");
	     String employeeSignature = rs.getString("EmployeeSignature");
	     String customerSignature = rs.getString("CustomerSignature");
	     

	     Report rep = new Report(rejectionsAge, rejectionsAttitude, rejectionsAlternative, alternativeRemarks,
			employeeSignature, customerSignature);
	     rep.setTaskID(taskID);
	     rep.setReportNr(reportNr);
	     return rep;
	}



	@Override
	public void updateReportByTaskID(int rejectionAge, int rejectionAttitude, int rejectionAlternative,
			String alternativeRemarks, String employeeSignature, String customerSignature, int taskID)
			throws Exception {
		
		
		try {
			updateReportByTaskID = connection.prepareStatement(update_report_by_taskid);
			updateReportByTaskID.setInt(1, rejectionAge);
			updateReportByTaskID.setInt(2, rejectionAttitude);
			updateReportByTaskID.setInt(3, rejectionAlternative);
			updateReportByTaskID.setString(4, alternativeRemarks);
			updateReportByTaskID.setString(5, employeeSignature);
			updateReportByTaskID.setString(6, customerSignature);
			updateReportByTaskID.setInt(7, taskID);
			
			dbConnection.getResultSetWithPS(updateReportByTaskID);
		} catch (SQLException e) {
			
			e.printStackTrace();
		
		}
	}



}
