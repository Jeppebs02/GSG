package ctrl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import dal.AlarmDB;
import model.Alarm;
import model.Classification;

/**
 * The {@code AlarmCtrl} class provides the main control methods for creating,
 * retrieving, and managing {@link Alarm} objects. It acts as a liaison between
 * the data access layer (DAL) and the application logic.
 */
public class AlarmCtrl {

	private AlarmDB adb;
	private Alarm currentAlarm;

	/**
	 * Constructs an {@code AlarmCtrl} instance and initializes the underlying
	 * database access object used for storing and retrieving alarms.
	 * 
	 * @throws SQLException if a database access error occurs.
	 */
	public AlarmCtrl() throws SQLException {
		adb = new AlarmDB();
	}

	/**
	 * Creates a new {@link Alarm} instance with the given attributes and persists
	 * it to the database by associating it with the given report number.
	 * 
	 * @param time           the time at which the alarm is scheduled to trigger.
	 * @param classification the classification/type/category of the alarm.
	 * @param description    a textual description providing details about the
	 *                       alarm.
	 * @param notify         a boolean indicating if notifications are enabled for
	 *                       this alarm.
	 * @param reportNr       the identification number of the report the alarm
	 *                       belongs to.
	 * @return the newly created {@link Alarm} instance.
	 * @throws Exception if the alarm cannot be created or saved.
	 */
	public Alarm createAlarm(LocalDateTime time, Classification classification, String description, boolean notify,
			int reportNr) throws Exception {
		currentAlarm = new Alarm(time, classification, description, notify);
		saveAlarm(reportNr);
		return currentAlarm;
	}

	/**
	 * Retrieves an existing {@link Alarm} by its unique identifier. Currently, this
	 * method returns an alarm from the database. Future implementations could
	 * potentially add extra descriptions or modifications.
	 * 
	 * @param alarmID the unique identifier of the alarm to retrieve.
	 * @return the {@link Alarm} instance with the specified ID.
	 * @throws Exception if the alarm cannot be retrieved or does not exist.
	 */
	// TODO
	public Alarm AddAlarmEkstraDescription(int alarmID) throws Exception {
		return adb.findAlarmByID(alarmID);
	}

	/**
	 * Retrieves all alarms associated with a given report number from the database.
	 * 
	 * @param reportNr the identification number of the report whose alarms are to
	 *                 be fetched.
	 * @return a {@link List} of {@link Alarm} objects associated with the specified
	 *         report number.
	 * @throws Exception if there is an issue accessing the data or if no alarms can
	 *                   be found.
	 */
	public List<Alarm> findAlarmsByReportID(int reportNr) throws Exception {
		return adb.findAllAlarmsByReportID(reportNr);
	}

	/**
	 * Deletes a specific alarm from the database using its unique identifier.
	 * 
	 * @param alarmID the unique identifier of the alarm to delete.
	 * @throws SQLException if a database access error occurs or if the deletion
	 *                      fails.
	 */
	public void deleteAlarmByAlarmID(int alarmID) throws SQLException {
		adb.deleteAlarmFromDB(alarmID);
	}

	/**
	 * Saves the currently set alarm (in {@link #currentAlarm}) to the database
	 * under the given report number.
	 * 
	 * @param reportNr the identification number of the report under which the alarm
	 *                 will be saved.
	 * @throws Exception if saving the alarm fails or if the current alarm is not
	 *                   properly initialized.
	 */
	public void saveAlarm(int reportNr) throws Exception {
		adb.saveAlarmToDB(currentAlarm, reportNr);
	}

}
