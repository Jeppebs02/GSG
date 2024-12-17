package ctrl;

import java.sql.SQLException;
import java.util.ArrayList;

import dal.ReportDB;
import model.Alarm;
import model.Rating;
import model.Report;
import model.Task;

/**
 * The {@code ReportCtrl} class provides methods for creating, retrieving,
 * updating, and deleting {@link Report} objects, as well as managing associated
 * alarms and ratings. It interacts with the {@link ReportDB},
 * {@link AlarmCtrl}, and {@link RatingCtrl} classes to perform database
 * operations.
 */
public class ReportCtrl {

	private ReportDB repDB;
	private AlarmCtrl ac;
	private RatingCtrl rc;

	/**
	 * Constructs a new {@code ReportCtrl} instance and initializes the underlying
	 * data access objects and controllers.
	 * 
	 * @throws SQLException if there is a database connection or query issue.
	 */
	public ReportCtrl() throws SQLException {
		repDB = new ReportDB();
		ac = new AlarmCtrl();
		rc = new RatingCtrl();
	}

	/**
	 * Creates a new {@link Report} object with the specified parameters.
	 * 
	 * @param rejectionsAge         the number of age-related rejections.
	 * @param rejectionsAttitude    the number of attitude-related rejections.
	 * @param rejectionsAlternative the number of alternative-related rejections.
	 * @param alternativeRemarks    additional remarks related to alternatives.
	 * @param employeeSignature     the signature of the employee.
	 * @param customerSignature     the signature of the customer.
	 * @return a newly created {@link Report} object.
	 */
	public Report createReport(int rejectionsAge, int rejectionsAttitude, int rejectionsAlternative,
			String alternativeRemarks, String employeeSignature, String customerSignature) {
		return new Report(rejectionsAge, rejectionsAttitude, rejectionsAlternative, alternativeRemarks,
				employeeSignature, customerSignature);
	}

	/**
	 * Creates and saves a new {@link Report} object in the database with the
	 * specified parameters and associates it with the given {@link Task}.
	 * 
	 * @param task                  the {@link Task} associated with the report.
	 * @param rejectionsAge         the number of age-related rejections.
	 * @param rejectionsAttitude    the number of attitude-related rejections.
	 * @param rejectionsAlternative the number of alternative-related rejections.
	 * @param alternativeRemarks    additional remarks related to alternatives.
	 * @param employeeSignature     the signature of the employee.
	 * @param customerSignature     the signature of the customer.
	 * @throws Exception if the report cannot be saved to the database.
	 */
	public void saveReport(Task task, int rejectionsAge, int rejectionsAttitude, int rejectionsAlternative,
			String alternativeRemarks, String employeeSignature, String customerSignature) throws Exception {
		ReportDB repDB = new ReportDB();
		Report report = createReport(rejectionsAge, rejectionsAttitude, rejectionsAlternative, alternativeRemarks,
				employeeSignature, customerSignature);

		// Associate the report with the given task
		report.setTaskID(task.getTaskID());
		repDB.saveReportToDb(report);
	}

	/**
	 * Finds a {@link Report} by the associated {@link Task} ID. It also retrieves
	 * and attaches related alarms and ratings to the returned report.
	 * 
	 * @param taskID the unique identifier of the {@link Task}.
	 * @return the {@link Report} associated with the given task ID.
	 * @throws Exception if the report cannot be found or an error occurs during
	 *                   retrieval.
	 */
	public Report findReportByTaskID(int taskID) throws Exception {
		Report report = repDB.findReportByTaskID(taskID);

		ArrayList<Alarm> alarms = (ArrayList<Alarm>) ac.findAlarmsByReportID(report.getReportNr());
		alarms.forEach(report::addAlarms);

		ArrayList<Rating> ratings = (ArrayList<Rating>) rc.findRatingsByReportID(report.getReportNr());
		ratings.forEach(report::addRatings);

		return report;
	}

	/**
	 * Deletes the {@link Report} associated with the given task ID from the
	 * database. Additionally, deletes all alarms and ratings linked to that report.
	 * 
	 * @param taskID the unique identifier of the {@link Task} whose report is to be
	 *               deleted.
	 * @throws Exception if the report or related data cannot be deleted.
	 */
	public boolean deleteReportByTaskID(int taskID) throws Exception {
		Report report = findReportByTaskID(taskID);

		
		// Delete associated alarms
		report.getAlarms().forEach(a -> {
			try {
				ac.deleteAlarmByAlarmID(a.getAlarmID());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});

		// Delete associated ratings
		report.getRatings().forEach(r -> {
			try {
				rc.deleteRatingByRatingID(r.getRatingID());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return repDB.deleteReportByTaskID(taskID);
		
	}

	/**
	 * Updates a {@link Report} associated with a given task ID using the provided
	 * parameters.
	 * 
	 * @param rejectionAge         the updated number of age-related rejections.
	 * @param rejectionAttitude    the updated number of attitude-related
	 *                             rejections.
	 * @param rejectionAlternative the updated number of alternative-related
	 *                             rejections.
	 * @param alternativeRemarks   updated remarks related to alternatives.
	 * @param employeeSignature    the updated employee signature.
	 * @param customerSignature    the updated customer signature.
	 * @param task                 the {@link Task} whose associated report will be
	 *                             updated.
	 * @throws Exception if the report cannot be updated.
	 */
	public void updateReportByTaskID(int rejectionAge, int rejectionAttitude, int rejectionAlternative,
			String alternativeRemarks, String employeeSignature, String customerSignature, Task task) throws Exception {
		repDB.updateReportByTaskID(rejectionAge, rejectionAttitude, rejectionAlternative, alternativeRemarks,
				employeeSignature, customerSignature, task.getTaskID());
	}

}
