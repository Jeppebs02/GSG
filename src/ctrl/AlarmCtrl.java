package ctrl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import dal.AlarmDB;
import model.Alarm;
import model.Classification;

public class AlarmCtrl {

	private AlarmDB adb;
	
	public AlarmCtrl() throws SQLException {
		adb = new AlarmDB();
	}
	
	public Alarm createAlarm(LocalDateTime time, Classification classification, String description) {
		
		return new Alarm(time, classification, description);
		
	}
	//TODO 
	public Alarm AddAlarmEkstraDescription(int alarmID) throws Exception {
		
		return adb.findAlarmByID(alarmID);
		
	}
	
	//TODO
	public List<Alarm> findAlarmsByReportID(int reportNr) {
		return null;
		
	}
	
	//TODO
	public void deleteAlarmByAlarmID(int alarmID) throws SQLException {
		adb.deleteAlarmFromDB(alarmID);
	}
	
}
