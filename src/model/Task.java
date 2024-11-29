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

    public Task(String description, String location, LocalDateTime date) {
    	this.description = description;
    	this.location = location;
    	this.date = date;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

	
	public List<Shift> getShifts() {
		return shifts;
	}

	
	public void setShifts(List<Shift> shifts) {
		this.shifts = shifts;
	}
}
