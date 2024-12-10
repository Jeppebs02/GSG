package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import model.AlarmExtra;

public class AlarmExtraDB implements AlarmExtraDBIF{
	private Connection connection;
	private DBConnection dbConnection;
	
	// SQL queries for inserting and finding alarmExtras
	private static final String insert_alarm_extra = 
			"INSERT INTO [AlarmExtra] (Time, AlarmID, Description) VALUES (?, ?, ?);";
	private static final String find_all_alarm_extra_from_alarm_id = 
			"SELECT ID AS AlarmExtraID, AlarmID, Description FROM [AlarmExtra] WHERE AlarmID = ?;";
	
	private PreparedStatement insertAlarmExtra;
	private PreparedStatement findAllAlarmExtraFromAlarmID;
	
	public AlarmExtraDB() throws SQLException {
        dbConnection = DBConnection.getInstance();
        connection = DBConnection.getInstance().getConnection();
        
        insertAlarmExtra = connection.prepareStatement(insert_alarm_extra, Statement.RETURN_GENERATED_KEYS);
        findAllAlarmExtraFromAlarmID = connection.prepareStatement(find_all_alarm_extra_from_alarm_id);
	}
	
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

	@Override
	public List<AlarmExtra> findAllAlarmExtraFromAlarmID(int alarmID) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
