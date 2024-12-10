package dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Alarm;

public interface AlarmDBIF {

	Alarm saveAlarmToDB(Alarm alarm, int reportID) throws Exception;
	
	Alarm createAlarmFromResultSet(ResultSet rs) throws SQLException;
	
	void deleteAlarmFromDB(int alarmID) throws SQLException;
	
	Alarm findAlarmByID(int alarmID) throws Exception;
	
	void saveAlarmExtraDescription(String extraDescription) throws Exception;
	
	List<Alarm> findAllAlarmsByReportID(int reportID) throws Exception;
	
	
}
