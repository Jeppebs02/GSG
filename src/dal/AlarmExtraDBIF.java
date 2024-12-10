package dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.AlarmExtra;

public interface AlarmExtraDBIF {
	
	
	AlarmExtra saveAlarmExtra(AlarmExtra alarmExtra, int alarmID) throws Exception;
	
	List<AlarmExtra> findAllAlarmExtraFromAlarmID(int alarmID) throws Exception;
	
	AlarmExtra createAlarmExtraFromResultSet(ResultSet rs) throws Exception;
}
