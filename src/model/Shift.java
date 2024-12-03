package model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Shift {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Employee employee;
	private int shiftID;


	public Shift(LocalDateTime startTime, LocalDateTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public LocalDateTime getStartTime() {
		return startTime;
	}


	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}


	public LocalDateTime getEndTime() {
		return endTime;
	}


	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	
	public Employee getEmployee() {
		return employee;
	}

	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public int getShiftID() {
		return shiftID;
	}
	
	public void setShiftID(int shiftID) {
		this.shiftID = shiftID;
	}
}