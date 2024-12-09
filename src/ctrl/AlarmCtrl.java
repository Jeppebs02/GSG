package ctrl;

import java.time.LocalDateTime;

import model.Alarm;
import model.Classification;

public class AlarmCtrl {

	
	public Alarm createAlarm(LocalDateTime time, Classification classification, String description) {
		
		return new Alarm(time, classification, description);
		
	}
	
	public Alarm updateAlarm() {
		
	}
	
	
	
	
}
