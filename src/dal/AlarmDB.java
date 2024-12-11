package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Alarm;
import model.Classification;

/**
 * The {@code AlarmDB} class provides data access methods for storing,
 * retrieving, and deleting {@link Alarm} objects from the database. It
 * implements the {@link AlarmDBIF} interface and uses a {@link DBConnection}
 * instance for database operations.
 */
public class AlarmDB implements AlarmDBIF {

	// Connection and DBConnection instance
	private Connection connection;
	private DBConnection dbConnection;

	// SQL queries
	private static final String insert_alarm = "INSERT INTO [Alarm] (Time, Description, Notify, Classification, Report_ID) VALUES (?, ?, ?, ?, ?);";
	private static final String find_alarm_from_id = "SELECT ID AS Alarm_ID, Time, Description, Notify, Classification, Report_ID FROM [Alarm] WHERE ID = ?;";
	private static final String delete_alarm_from_id = "DELETE FROM [Alarm] WHERE ID = ?;";
	private static final String find_all_alarms_from_report_id = "SELECT ID AS Alarm_ID, Time, Description, Notify, Classification, Report_ID FROM [Alarm] WHERE Report_ID = ?;";

	// Prepared Statements
	private PreparedStatement insertAlarm;
	private PreparedStatement findAlarmFromID;
	private PreparedStatement deleteAlarmFromID;
	private PreparedStatement findAllAlarmsFromReportID;

	/**
	 * Constructs an {@code AlarmDB} instance, initializes database connection, and
	 * prepares the SQL statements.
	 * 
	 * @throws SQLException if a database access error occurs.
	 */
	public AlarmDB() throws SQLException {
		dbConnection = DBConnection.getInstance();
		connection = DBConnection.getInstance().getConnection();

		insertAlarm = connection.prepareStatement(insert_alarm, Statement.RETURN_GENERATED_KEYS);
		findAlarmFromID = connection.prepareStatement(find_alarm_from_id);
		deleteAlarmFromID = connection.prepareStatement(delete_alarm_from_id);
		findAllAlarmsFromReportID = connection.prepareStatement(find_all_alarms_from_report_id);
	}

	/**
	 * Saves a given {@link Alarm} object to the database and associates it with the
	 * specified report ID.
	 * 
	 * @param alarm    the {@link Alarm} object to save.
	 * @param reportID the ID of the report to associate with the alarm.
	 * @return the saved {@link Alarm} object (unchanged reference).
	 * @throws Exception if a database access or insertion error occurs.
	 */
	@Override
	public Alarm saveAlarmToDB(Alarm alarm, int reportID) throws Exception {
		int alarmID = -1;

		// Set parameters for the insert statement
		insertAlarm.setTimestamp(1, Timestamp.valueOf(alarm.getTime()));
		insertAlarm.setString(2, alarm.getDescription());
		insertAlarm.setBoolean(3, alarm.getNotify());
		insertAlarm.setString(4, alarm.getClassificationValue());
		insertAlarm.setInt(5, reportID);

		// Execute the insert and retrieve the generated key
		alarmID = dbConnection.executeSqlInsertWithIdentityPS(insertAlarm);
		System.out.println("Alarm ID: " + alarmID);

		return alarm;
	}

	/**
	 * Creates an {@link Alarm} object from the current row of the given ResultSet.
	 * This method expects all necessary columns to be present in the ResultSet.
	 * 
	 * @param rs the {@link ResultSet} positioned at a valid alarm row.
	 * @return an {@link Alarm} object constructed from the ResultSet data.
	 * @throws SQLException if accessing the ResultSet data causes a database error.
	 */
	@Override
	public Alarm createAlarmFromResultSet(ResultSet rs) throws SQLException {
		// Extract fields
		LocalDateTime time = rs.getTimestamp("Time").toLocalDateTime();
		Classification classification = Classification.valueOf(rs.getString("classification"));
		String description = rs.getString("Description");
		Boolean notify = rs.getBoolean("Notify");
		int alarmID = rs.getInt("Alarm_ID");

		// Create Alarm object and set the alarm ID
		Alarm alarm = new Alarm(time, classification, description, notify);
		alarm.setAlarmID(alarmID);

		// TODO: Add logic to retrieve and set any additional alarm comments or related
		// data if needed.

		return alarm;
	}

	/**
	 * Deletes an alarm from the database using its unique ID.
	 * 
	 * @param alarmID the unique identifier of the alarm to delete.
	 * @throws SQLException if a database access error occurs.
	 */
	@Override
	public void deleteAlarmFromDB(int alarmID) throws SQLException {
		// Set parameters for SQL query
		deleteAlarmFromID.setInt(1, alarmID);

		// Execute query
		try {
			dbConnection.executeSqlInsertWithIdentityPS(deleteAlarmFromID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Finds a specific {@link Alarm} by its unique ID.
	 * 
	 * @param alarmID the unique identifier of the alarm to find.
	 * @return the {@link Alarm} object if found; otherwise null.
	 * @throws Exception if a database access or retrieval error occurs.
	 */
	@Override
	public Alarm findAlarmByID(int alarmID) throws Exception {
		Alarm alarm = null;
		findAlarmFromID.setInt(1, alarmID);

		try {
			ResultSet rs = dbConnection.getResultSetWithPS(findAlarmFromID);

			if (rs.next()) {
				alarm = createAlarmFromResultSet(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return alarm;
	}

	/**
	 * Retrieves all alarms associated with a given report ID.
	 * 
	 * @param reportID the unique identifier of the report whose alarms should be
	 *                 fetched.
	 * @return a {@link List} of {@link Alarm} objects associated with the given
	 *         report ID.
	 * @throws Exception if a database access or retrieval error occurs.
	 */
	@Override
	public List<Alarm> findAllAlarmsByReportID(int reportID) throws Exception {
		List<Alarm> alarms = new ArrayList<>();

		// Set parameter
		findAllAlarmsFromReportID.setInt(1, reportID);

		try (ResultSet rs = dbConnection.getResultSetWithPS(findAllAlarmsFromReportID)) {
			while (rs.next()) {
				// Create Alarm from ResultSet and add to list
				Alarm alarm = createAlarmFromResultSet(rs);
				alarms.add(alarm);
			}
		} catch (Exception e) {
			System.out.println("Creating/adding alarm went wrong");
			e.printStackTrace();
		}

		return alarms;
	}
}
