package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import model.Alarm;

public class AlarmDB implements AlarmDBIF{
	// Connection
	private Connection connection;
	private DBConnection dbConnection;
	
	// SQL queries
	private static final String insert_alarm = 
			"INSERT INTO [Alarm] (Time, Description, Notify, Classification, Report_ID) VALUES (?, ?, ?, ?, ?);";
	private static final String find_alarm_from_id = 
			"SELECT ID AS ALARM_ID, Time, Description, Notify, Classification, Report_ID FROM [Alarm] WHERE ID = ?;";
	private static final String delete_alarm_from_id = "";
	private static final String insert_alarm_extra = 
			"INSERT INTO [AlarmExtra] (ID, AlarmID, Description) VALUES (?, ?, ?);";
	private static final String find_all_alarm_extra_from_alarm_id = 
			"SELECT ID AS AlarmExtraID, AlarmID, Description FROM [AlarmExtra] WHERE AlarmID = ?;";
	
	// Prepared Statements
	private PreparedStatement insertAlarm;
	private PreparedStatement findAlarmFromID;
	private PreparedStatement deleteAlarmFromID;
	private PreparedStatement findAlarmExtra;
	private PreparedStatement getAllAlarmExtraFromAlarmID;
	
	public AlarmDB() throws SQLException {
        dbConnection = DBConnection.getInstance();
        connection = DBConnection.getInstance().getConnection();
        
        insertAlarm = connection.prepareStatement(insert_alarm, Statement.RETURN_GENERATED_KEYS);
        findAlarmFromID = connection.prepareStatement(find_alarm_from_id);
        deleteAlarmFromID = connection.prepareStatement(delete_alarm_from_id);
        findAlarmExtra = connection.prepareStatement(insert_alarm_extra, Statement.RETURN_GENERATED_KEYS);
        getAllAlarmExtraFromAlarmID = connection.prepareStatement(find_all_alarm_extra_from_alarm_id);
	}

	@Override
	public Alarm saveAlarm(Alarm alarm) throws Exception {
		int alarmID = -1;
		
		// Set parameters for the insert statement
		insertAlarm.setTimestamp(1, Timestamp.valueOf(alarm.getTime()));
		return null;
	}

	@Override
	public Alarm createAlarmFromResultSet(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
 
	@Override
	public void deleteAlarmFromDB(int alarmID) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Alarm findAlarmByID(int alarmID) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveAlarmExtraDescription(String extraDescription) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Alarm> findAllAlarmsByReportID(int reportID) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
