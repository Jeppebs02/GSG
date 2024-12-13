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

import model.AlarmExtra;

/**
 * The {@code AlarmExtraDB} class provides data access operations for creating
 * and retrieving {@link AlarmExtra} objects associated with a specific alarm.
 * It implements the {@link AlarmExtraDBIF} interface.
 */
public class AlarmExtraDB implements AlarmExtraDBIF {
	private Connection connection;
	private DBConnection dbConnection;

	// SQL queries for inserting and finding AlarmExtra records
	private static final String insert_alarm_extra = "INSERT INTO [AlarmExtra] (Time, Alarm_ID, Description) VALUES (?, ?, ?);";
	private static final String find_all_alarm_extra_from_alarm_id = "SELECT ID AS AlarmExtraID, Alarm_ID, Description, Time FROM [AlarmExtra] WHERE Alarm_ID = ?;";

	private PreparedStatement insertAlarmExtra;
	private PreparedStatement findAllAlarmExtraFromAlarmID;

	/**
	 * Constructs an {@code AlarmExtraDB} instance by establishing a database
	 * connection and preparing the SQL statements.
	 * 
	 * @throws SQLException if a database access error occurs.
	 */
	public AlarmExtraDB() throws SQLException {
		dbConnection = DBConnection.getInstance();
		connection = DBConnection.getInstance().getConnection();

		insertAlarmExtra = connection.prepareStatement(insert_alarm_extra, Statement.RETURN_GENERATED_KEYS);
		findAllAlarmExtraFromAlarmID = connection.prepareStatement(find_all_alarm_extra_from_alarm_id);
	}

	/**
	 * Saves a given {@link AlarmExtra} object to the database, associating it with
	 * a specified alarm ID. Once saved, the generated ID is set on the
	 * {@link AlarmExtra} object.
	 * 
	 * @param alarmExtra the {@link AlarmExtra} object to be saved.
	 * @param alarmID    the ID of the alarm to which this extra information is
	 *                   related.
	 * @return the {@link AlarmExtra} object with its newly assigned ID.
	 * @throws Exception if an error occurs during the database operation.
	 */
	@Override
	public AlarmExtra saveAlarmExtra(AlarmExtra alarmExtra, int alarmID) throws Exception {
		int alarmExtraID = -1;

		// Set parameters
		insertAlarmExtra.setTimestamp(1, Timestamp.valueOf(alarmExtra.getTimeMade()));
		insertAlarmExtra.setInt(2, alarmID);
		insertAlarmExtra.setString(3, alarmExtra.getDescription());

		try {
			// Execute and get generated key
			alarmExtraID = dbConnection.executeSqlInsertWithIdentityPS(insertAlarmExtra);

			// Set alarmExtra ID on the object
			alarmExtra.setAlarmExtraID(alarmExtraID);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return alarmExtra;
	}

	/**
	 * Finds all {@link AlarmExtra} objects associated with a given alarm ID.
	 * 
	 * @param alarmID the ID of the alarm for which extra information is retrieved.
	 * @return a {@link List} of {@link AlarmExtra} objects related to the specified
	 *         alarm ID.
	 * @throws Exception if an error occurs during the database retrieval.
	 */
	@Override
	public List<AlarmExtra> findAllAlarmExtraFromAlarmID(int alarmID) throws Exception {
		List<AlarmExtra> alarmExtras = new ArrayList<>();

		// Set parameter
		findAllAlarmExtraFromAlarmID.setInt(1, alarmID);

		try (ResultSet rs = dbConnection.getResultSetWithPS(findAllAlarmExtraFromAlarmID)) {
			while (rs.next()) {
				// Create AlarmExtra from ResultSet
				AlarmExtra alarmExtra = createAlarmExtraFromResultSet(rs);
				// Add to list
				alarmExtras.add(alarmExtra);
			}
		} catch (SQLException e) {
			System.out.println("Creating/adding the alarmExtra failed");
			e.printStackTrace();
		}
		return alarmExtras;
	}

	/**
	 * Creates an {@link AlarmExtra} object from the current row of the given
	 * {@link ResultSet}. This method expects all necessary columns to be present
	 * (Description, Time).
	 * 
	 * @param rs the {@link ResultSet} positioned at a valid alarm extra row.
	 * @return an {@link AlarmExtra} object constructed from the current row of the
	 *         ResultSet.
	 * @throws Exception if accessing the ResultSet data causes an error.
	 */
	@Override
	public AlarmExtra createAlarmExtraFromResultSet(ResultSet rs) throws Exception {
		// Extract fields
		String description = rs.getString("Description");
		LocalDateTime timeMade = rs.getTimestamp("Time").toLocalDateTime();

		// Create AlarmExtra object and set time and any other needed fields
		AlarmExtra alarmExtra = new AlarmExtra(description);
		alarmExtra.setTimeMade(timeMade);

		return alarmExtra;
	}
}
