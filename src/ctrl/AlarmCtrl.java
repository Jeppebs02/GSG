package ctrl;

import java.time.LocalDateTime;

import dal.AlarmDB;
import model.Alarm;
import model.Classification;

public class AlarmCtrl {

	private AlarmDB adb;
	
	
	public Alarm createAlarm(LocalDateTime time, Classification classification, String description) {
		
		return new Alarm(time, classification, description);
		
	}
	//TODO ??
	public Alarm updateAlarm(int alarmID) throws Exception {
		
		adb.findAlarmByID(alarmID);
		
	}
	
	//TODO
	public void findAlarmsByReportID() {
		
	}
	
	//TODO
	public void deleteAlarmsByReportID() {
		
	}
	
}
