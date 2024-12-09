package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Alarm;

public class AlarmDB implements AlarmDBIF{
	// Connection
	private Connection connection;
	private DBConnection dbConnection;
	
	// SQL queries
	private static final String insert_alarm = "";
	private static final String get_alarm_from_id = "";
	private static final String delete_alarm_from_id = "";
	private static final String insert_alarm_extra = "";
	
	// Prepared Statements
	private PreparedStatement insertAlarm;
	private PreparedStatement getAlarmFromID;
	private PreparedStatement deleteAlarmFromID;
	private PreparedStatement insertAlarmExtra;
	
	public AlarmDB() throws SQLException {
        dbConnection = DBConnection.getInstance();
        connection = DBConnection.getInstance().getConnection();
        
        insertAlarm = connection.prepareStatement(insert_alarm, Statement.RETURN_GENERATED_KEYS);
        getAlarmFromID = connection.prepareStatement(get_alarm_from_id);
        deleteAlarmFromID = connection.prepareStatement(delete_alarm_from_id);
        insertAlarmExtra = connection.prepareStatement(insert_alarm_extra, Statement.RETURN_GENERATED_KEYS);
	}

	@Override
	public Alarm saveAlarm(Alarm alarm) throws Exception {
		// TODO Auto-generated method stub
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
	public Alarm getAlarmByID(int alarmID) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveAlarmExtraDescription(String extraDescription) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
