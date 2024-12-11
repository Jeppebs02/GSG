package model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import java.time.LocalDateTime;

public class Shift {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Employee employee;
    private int shiftID;

    public Shift(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be later than end time.");
        }
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        if (endTime != null && startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be later than end time.");
        }
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        if (startTime != null && endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("End time cannot be earlier than start time.");
        }
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
