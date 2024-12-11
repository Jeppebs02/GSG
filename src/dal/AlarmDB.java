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

public class AlarmDB implements AlarmDBIF{
	// Connection
	private Connection connection;
	private DBConnection dbConnection;
	
	// SQL queries
	private static final String insert_alarm = 
			"INSERT INTO [Alarm] (Time, Description, Notify, Classification, Report_ID) VALUES (?, ?, ?, ?, ?);";
	private static final String find_alarm_from_id = 
			"SELECT ID AS Alarm_ID, Time, Description, Notify, Classification, Report_ID FROM [Alarm] WHERE ID = ?;";
	private static final String delete_alarm_from_id = 
			"DELETE FROM [Alarm] WHERE ID = ?;";
	private static final String find_all_alarms_from_report_id = 
			"SELECT Time, Description, Notify, Classification, Report_ID FROM [Alarm] WHERE Report_ID =?;";
	
	// Prepared Statements
	private PreparedStatement insertAlarm;
	private PreparedStatement findAlarmFromID;
	private PreparedStatement deleteAlarmFromID;
	private PreparedStatement findAllAlarmsFromReportID;
	
	public AlarmDB() throws SQLException {
        dbConnection = DBConnection.getInstance();
        connection = DBConnection.getInstance().getConnection();
        
        insertAlarm = connection.prepareStatement(insert_alarm, Statement.RETURN_GENERATED_KEYS);
        findAlarmFromID = connection.prepareStatement(find_alarm_from_id);
        deleteAlarmFromID = connection.prepareStatement(delete_alarm_from_id);
        findAllAlarmsFromReportID = connection.prepareStatement(find_all_alarms_from_report_id);
        }

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

	@Override
	public Alarm createAlarmFromResultSet(ResultSet rs) throws SQLException {
		// Extract fields
		LocalDateTime time = rs.getTimestamp("Time").toLocalDateTime();
		Classification classification = Classification.valueOf(rs.getString("classification"));
		String description = rs.getString("Description");
		Boolean notify = rs.getBoolean("Notify");
		int alarmID = rs.getInt("Alarm_ID");
		
		// Create Alarm object and set value that aren't included in constructer
		Alarm alarm = new Alarm(time, classification, description, notify);
		alarm.setAlarmID(alarmID);
		
		//Set associated extra comments
		//TODO
		
		return alarm;
	}
 
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

	@Override
	public Alarm findAlarmByID(int alarmID) throws Exception {
		Alarm alarm = null;
		findAlarmFromID.setInt(1, alarmID);
		
		try {
			ResultSet rs = dbConnection.getResultSetWithPS(findAlarmFromID);
			
			rs.next();
			alarm = createAlarmFromResultSet(rs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return alarm;
	}

	@Override
	public List<Alarm> findAllAlarmsByReportID(int reportID) throws Exception {
		List<Alarm> alarms = new ArrayList<>();
		
		// Set parameter
		findAllAlarmsFromReportID.setInt(1, reportID);
		
		try (ResultSet rs = dbConnection.getResultSetWithPS(findAllAlarmsFromReportID)) {
			while (rs.next()) {
				// Create Alarm from ResultSet
				Alarm alarm = createAlarmFromResultSet(rs);
				// Add to the list
				alarms.add(alarm);
			}
		} catch (Exception e) {
			System.out.println("Creating/adding alarm went wrong");
			e.printStackTrace();
		}
		
		return alarms;
	}

}
