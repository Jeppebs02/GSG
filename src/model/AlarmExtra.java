package model;

import java.time.LocalDateTime;

public class AlarmExtra {
	private int alarmExtraID;
	private String description;
	private LocalDateTime timeMade;
	
	public AlarmExtra(String description) {
		this.alarmExtraID = 0;
		this.description = description;
		this.timeMade = LocalDateTime.now();
	}

	public int getAlarmExtraID() {
		return alarmExtraID;
	}

	public String getDescription() {
		return description;
	}

	public void setAlarmExtraID(int alarmExtraID) {
		this.alarmExtraID = alarmExtraID;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
