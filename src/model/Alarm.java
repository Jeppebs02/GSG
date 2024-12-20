package model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Alarm {
	private int alarmID;
	private LocalDateTime time;
    private String description;
    private boolean notify;
    private Classification classification; // Uses the Classification enum
    private List<AlarmExtra> extraDecsription;

    public Alarm(LocalDateTime time, Classification classification, String description, boolean notify) {
    	this.classification = classification;
    	this.time = time;
    	this.description = description;
    	this.notify = notify;
    	extraDecsription = new ArrayList<>();
    }
    
    // Getters and Setters
    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public Classification getClassification() {
        return classification;
    }
    
    public String getClassificationValue() {
    	return classification.toString();
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

	public List<AlarmExtra> getExtraDecsription() {
		return extraDecsription;
	}
	
	public void addExtra(List<AlarmExtra> extra) {
		this.extraDecsription = extra;
	}

	public int getAlarmID() {
		return alarmID;
	}

	public void setAlarmID(int alarmID) {
		this.alarmID = alarmID;
	}
}
