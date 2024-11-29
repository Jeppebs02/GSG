package model;

import java.util.ArrayList;
import java.util.List;

public class Report {
    private int rapportNr;
    private int rejectionsAge;
    private String rejectionsAttitude;
    private String rejectionsAlternative;
    private String alternativeRemarks;
    private String employeeSignature;
    private String customerSignature;
    private Task task; // Association with Task (0..1)
    private List<Alarm> alarms; // Association with Alarm (0..*)
    private List<Rating> ratings;

    public Report(Task task) {
    	this.task = task;
    	alarms = new ArrayList<>();
    	ratings = new ArrayList<>();
    	
    }
    
    // Getters and Setters
    public int getRapportNr() {
        return rapportNr;
    }

    public void setRapportNr(int rapportNr) {
        this.rapportNr = rapportNr;
    }

    public int getRejectionsAge() {
        return rejectionsAge;
    }

    public void setRejectionsAge(int rejectionsAge) {
        this.rejectionsAge = rejectionsAge;
    }

    public String getRejectionsAttitude() {
        return rejectionsAttitude;
    }

    public void setRejectionsAttitude(String rejectionsAttitude) {
        this.rejectionsAttitude = rejectionsAttitude;
    }

    public String getRejectionsAlternative() {
        return rejectionsAlternative;
    }

    public void setRejectionsAlternative(String rejectionsAlternative) {
        this.rejectionsAlternative = rejectionsAlternative;
    }

    public String getAlternativeRemarks() {
        return alternativeRemarks;
    }

    public void setAlternativeRemarks(String alternativeRemarks) {
        this.alternativeRemarks = alternativeRemarks;
    }

    public String getEmployeeSignature() {
        return employeeSignature;
    }

    public void setEmployeeSignature(String employeeSignature) {
        this.employeeSignature = employeeSignature;
    }

    public String getCustomerSignature() {
        return customerSignature;
    }

    public void setCustomerSignature(String customerSignature) {
        this.customerSignature = customerSignature;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public List<Alarm> getAlarms() {
        return new ArrayList<>(alarms);
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }

	
	public List<Rating> getRatings() {
		return ratings;
	}

	
	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
}
