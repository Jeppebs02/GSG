package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private String description;
    private String location;
    private boolean approval;
    private LocalDateTime date;
    private List<Shift> shifts;
    private Report report;
    private User user;

    public Task(String description, String location, LocalDateTime date, Report report, User user) {
    	this.description = description;
    	this.location = location;
    	this.date = date;
    	this.approval = true;
    	this.report = report;
    	this.user = user;;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

	
	public List<Shift> getShifts() {
		return new ArrayList<>(shifts);
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
