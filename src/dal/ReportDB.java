package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Report;

/**
 * The {@code ReportDB} class handles database operations related to the
 * {@link Report} entity.
 * 
 * <p>
 * This class uses the {@link DBConnection} singleton to execute queries and
 * manage database transactions. It provides methods for saving, updating,
 * retrieving, and deleting reports in the database.
 * </p>
 */
public class ReportDB implements ReportDBIF {

	private DBConnection dbConnection = DBConnection.getInstance();
	private Connection connection = DBConnection.getInstance().getConnection();

	// SQL Queries
	private static final String delete_report_by_taskid = "DELETE FROM [Report] WHERE [Task_ID] = ?;";
	private static final String find_report_by_taskid = "SELECT * FROM Report INNER JOIN Task ON Report.Task_ID = Task.ID WHERE Task.ID = ?;";
	private static final String save_report_to_db = "INSERT INTO [Report] (RejectionAge, RejectionAttitude, RejectionAlternative, "
			+ "AlternativeRemarks, EmployeeSignature, CustomerSignature, Task_ID) " + "VALUES (?,?,?,?,?,?,?)";
	private static final String update_report_by_taskid = "UPDATE dbo.[Report] SET RejectionAge = ?, RejectionAttitude = ?, RejectionAlternative = ?, AlternativeRemarks = ?, EmployeeSignature = ?, CustomerSignature = ? WHERE Task_ID = ?;";

	private PreparedStatement saveReportToDB;
	private PreparedStatement findReportByTaskID;
	private PreparedStatement deleteReportByTaskID;
	private PreparedStatement updateReportByTaskID;

	/**
	 * Saves the given {@link Report} object to the database.
	 * 
	 * <p>
	 * This method prepares an INSERT statement with all the relevant fields from
	 * the Report object and executes it. It uses the
	 * {@link DBConnection#executeSqlInsertWithIdentityPS(PreparedStatement)} method
	 * to return the generated key for the newly inserted record.
	 * </p>
	 * 
	 * @param report the {@link Report} object to be saved in the database.
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

	/**
	 * Finds a {@link Report} by the given task ID.
	 * 
	 * <p>
	 * This method retrieves the associated report for the given task ID from the
	 * database and constructs a {@link Report} object from the result set.
	 * </p>
	 * 
	 * @param taskID the ID of the task whose associated report is to be retrieved.
	 * @return a {@link Report} object if found, otherwise null.
	 * @throws Exception if a database or other error occurs during retrieval.
	 */
	@Override
	public Report findReportByTaskID(int taskID) throws Exception {
		Report report = null;
		findReportByTaskID = connection.prepareStatement(find_report_by_taskid);
		try {
			findReportByTaskID.setInt(1, taskID);
			ResultSet rs = dbConnection.getResultSetWithPS(findReportByTaskID);

			if (rs.next()) {
				report = createReportFromResultSet(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return report;
	}

	/**
	 * Deletes the {@link Report} associated with the specified task ID from the
	 * database.
	 * 
	 * @param taskID the ID of the task whose associated report is to be deleted.
	 * @throws Exception if a database or other error occurs during the deletion.
	 */
	
	@Override
	public boolean deleteReportByTaskID(int taskID) throws Exception {
		deleteReportByTaskID = connection.prepareStatement(delete_report_by_taskid);
		boolean result = false;
		try {
			deleteReportByTaskID.setInt(1, taskID);
			dbConnection.executeSqlInsertPS(deleteReportByTaskID);
			result = true;
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
			
		}
		return result;
	}

	/**
	 * Creates a {@link Report} object from the current row of the given
	 * {@link ResultSet}.
	 * 
	 * <p>
	 * This method extracts all relevant fields to construct a Report object,
	 * including report number, rejections, remarks, signatures, and associated task
	 * ID.
	 * </p>
	 * 
	 * @param rs the ResultSet positioned at a valid report row.
	 * @return a {@link Report} object constructed from the row's data.
	 * @throws Exception if a database or other error occurs while extracting data.
	 */
	@Override
	public Report createReportFromResultSet(ResultSet rs) throws Exception {
		int taskID = rs.getInt("Task_ID");
		int reportNr = rs.getInt("ReportNr");
		int rejectionsAge = rs.getInt("RejectionAge");
		int rejectionsAttitude = rs.getInt("RejectionAttitude");
		int rejectionsAlternative = rs.getInt("RejectionAlternative");
		String alternativeRemarks = rs.getString("AlternativeRemarks");
		String employeeSignature = rs.getString("EmployeeSignature");
		String customerSignature = rs.getString("CustomerSignature");

		Report rep = new Report(rejectionsAge, rejectionsAttitude, rejectionsAlternative, alternativeRemarks,
				employeeSignature, customerSignature);
		rep.setTaskID(taskID);
		rep.setReportNr(reportNr);
		return rep;
	}

	/**
	 * Updates a {@link Report} associated with the given task ID using the provided
	 * parameters.
	 * 
	 * <p>
	 * This method executes an UPDATE query to modify an existing report record in
	 * the database.
	 * </p>
	 * 
	 * @param rejectionAge         the updated number of age-related rejections.
	 * @param rejectionAttitude    the updated number of attitude-related
	 *                             rejections.
	 * @param rejectionAlternative the updated number of alternative-related
	 *                             rejections.
	 * @param alternativeRemarks   the updated alternative remarks.
	 * @param employeeSignature    the updated employee signature.
	 * @param customerSignature    the updated customer signature.
	 * @param taskID               the ID of the task whose associated report will
	 *                             be updated.
	 * @throws Exception if a database or other error occurs during the update.
	 */
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

			dbConnection.executeSqlInsertPS(updateReportByTaskID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
