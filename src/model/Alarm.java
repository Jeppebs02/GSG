package model;
import java.time.LocalDateTime;


public class Alarm {
    private LocalDateTime time;
    private String description;
    private String notify;
    private Classification classification; // Uses the Classification enum
    private Report report;

    public Alarm(LocalDateTime time, Classification classification, Report report) {
    	this.classification = classification;
    	this.time = time;
    	this.report = report;
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

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}
}
