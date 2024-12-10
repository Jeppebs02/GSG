package model;

import java.util.ArrayList;
import java.util.List;

public class Report {
	private int taskID;
    private int reportNr;
    private int rejectionsAge;
    private int rejectionsAttitude;
    private int rejectionsAlternative;
    private String alternativeRemarks;
    private String employeeSignature;
    private String customerSignature;
    private List<Alarm> alarms; 
    private List<Rating> ratings;

    public Report() {
    	alarms = new ArrayList<>();
    	ratings = new ArrayList<>();
    	
    }
    
    public Report(int rejectionsAge, int rejectionsAttitude, int rejectionsAlternative, String alternativeRemarks,
			String employeeSignature, String customerSignature) {
		super();
		this.rejectionsAge = rejectionsAge;
		this.rejectionsAttitude = rejectionsAttitude;
		this.rejectionsAlternative = rejectionsAlternative;
		this.alternativeRemarks = alternativeRemarks;
		this.employeeSignature = employeeSignature;
		this.customerSignature = customerSignature;
		this.alarms = new ArrayList<>();
    	this.ratings = new ArrayList<>();
	}




	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	// Getters and Setters
    public int getReportNr() {
        return reportNr;
    }

    public void setReportNr(int reportNr) {
        this.reportNr = reportNr;
    }

    public int getRejectionsAge() {
        return rejectionsAge;
    }

    public void setRejectionsAge(int rejectionsAge) {
        this.rejectionsAge = rejectionsAge;
    }

    public int getRejectionsAttitude() {
        return rejectionsAttitude;
    }

    public void setRejectionsAttitude(int rejectionsAttitude) {
        this.rejectionsAttitude = rejectionsAttitude;
    }

    public int getRejectionsAlternative() {
        return rejectionsAlternative;
    }

    public void setRejectionsAlternative(int rejectionsAlternative) {
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

    public List<Alarm> getAlarms() {
        return new ArrayList<>(alarms);
    }

	public List<Rating> getRatings() {
		return new ArrayList<>(ratings);
	}

}
