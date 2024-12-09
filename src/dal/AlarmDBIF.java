package dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Alarm;

public interface AlarmDBIF {

	Alarm saveAlarm(Alarm alarm) throws Exception;
	
	Alarm createAlarmFromResultSet(ResultSet rs) throws SQLException;
	
	void deleteAlarmFromDB(int alarmID) throws SQLException;
	
	Alarm getAlarmByID(int alarmID) throws Exception;
	
	void saveAlarmExtraDescription(String extraDescription) throws Exception;
}
