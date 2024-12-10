package ctrl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import dal.AlarmDB;
import model.Alarm;
import model.Classification;

public class AlarmCtrl {

	private AlarmDB adb;
	private Alarm currentAlarm;
	
	public AlarmCtrl() throws SQLException {
		adb = new AlarmDB();
		 
	}
	
	public Alarm createAlarm(LocalDateTime time, Classification classification, String description, int reportNr) throws Exception {
		
		currentAlarm = new Alarm(time, classification, description);
		saveAlarm(reportNr);
		return currentAlarm;
		
	}
	//TODO 
	public Alarm AddAlarmEkstraDescription(int alarmID) throws Exception {
		
		return adb.findAlarmByID(alarmID);
		
	}
	
	
	public List<Alarm> findAlarmsByReportID(int reportNr) throws Exception {
		return adb.findAllAlarmsByReportID(reportNr);
		
		
	}
	
	
	public void deleteAlarmByAlarmID(int alarmID) throws SQLException {
		adb.deleteAlarmFromDB(alarmID);
	}
	
	public void saveAlarm(int reportNr) throws Exception {
		adb.saveAlarmToDB(currentAlarm, reportNr);
	}
	
}
