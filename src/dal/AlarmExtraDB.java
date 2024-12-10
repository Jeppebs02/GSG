package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import model.AlarmExtra;

public class AlarmExtraDB implements AlarmExtraDBIF{
	private Connection connection;
	private DBConnection dbConnection;
	
	// SQL queries for inserting and finding alarmExtras
	private static final String insert_alarm_extra = 
			"INSERT INTO [AlarmExtra] (ID, AlarmID, Description) VALUES (?, ?, ?);";
	private static final String find_all_alarm_extra_from_alarm_id = 
			"SELECT ID AS AlarmExtraID, AlarmID, Description FROM [AlarmExtra] WHERE AlarmID = ?;";
	
	private PreparedStatement saveAlarmExtra;
	private PreparedStatement findAllAlarmExtraFromAlarmID;
	
	public AlarmExtraDB() throws SQLException {
        dbConnection = DBConnection.getInstance();
        connection = DBConnection.getInstance().getConnection();
        
        saveAlarmExtra = connection.prepareStatement(insert_alarm_extra, Statement.RETURN_GENERATED_KEYS);
        findAllAlarmExtraFromAlarmID = connection.prepareStatement(find_all_alarm_extra_from_alarm_id);
	}
	
	@Override
	public AlarmExtra saveAlarmExtra(AlarmExtra alarmExtra, int alarmID) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AlarmExtra> findAllAlarmExtraFromAlarmID(int alarmID) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
