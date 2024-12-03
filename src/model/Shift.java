package model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Shift {
	private LocalTime startTime;
	private LocalTime endTime;
	private Employee employee;
	private int shiftID;


	public Shift(LocalTime startTime, LocalTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}


	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}


	public LocalTime getEndTime() {
		return endTime;
	}


	public void setEndTime(LocalTime endTime) {
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