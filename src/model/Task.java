package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private String description;
    private String location;
    private boolean approval;
    private LocalDate date;
    private List<Shift> shifts;
    private Report report;
    private User user;
    private int taskID;

    public Task(String description, String location, LocalDate date, Report report, User user) {
    	this.description = description;
    	this.location = location;
    	this.date = date;
    	this.approval = true;
    	this.report = report;
    	this.user = user;
    	shifts = new ArrayList<>();
    }
    
    //Usecase 1
    public Task(LocalDate date, String description, String location, User userID) {
    	this.date = date;
    	this.description = description;
    	this.location = location;
    	this.user = userID;
    	this.approval = true;
    	shifts = new ArrayList<>();
    }
    
    // Getters and Setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

	
    public void addShift(Shift shift) {
    	if(shift != null) {
    		shifts.add(shift);
    	}
    }
    
	public List<Shift> getShifts() {
		return new ArrayList<>(shifts);
	}
	
	public void setTaskID (int taskID) {
		this.taskID = taskID;
	}
	
	public int getTaskID () {
		return taskID;
	}

	/**
	 * @return the report
	 */
	public Report getReport() {
		return report;
	}

	/**
	 * @param report the report to set
	 */
	public void setReport(Report report) {
		this.report = report;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
