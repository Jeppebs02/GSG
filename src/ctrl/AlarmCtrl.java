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
	
	public Alarm updateAlarm(int alarmID) {
		
		adb.findAlarmByID(alarmID);
		
		
		
	}
	
	public 
	
	
	
}
