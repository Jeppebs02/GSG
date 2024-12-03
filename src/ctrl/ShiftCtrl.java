package ctrl;

import java.time.LocalDateTime;
import java.time.LocalTime;

import dal.DataAccessException;
import dal.ShiftDB;
import model.Employee;
import model.Shift;

public class ShiftCtrl {
	private Shift currentShift;
	
	public ShiftCtrl()  {
		this.currentShift = null;
	}
	
	public Shift createShift(LocalDateTime startTime, LocalDateTime endTime) {
		// Create a new Shift object and assign it to currentShift
		this.currentShift = new Shift(startTime, endTime);
		
		return currentShift;
	}
	
	public void addEmployeeToShift(Employee employee) throws Exception {
		// Validate that the Shift object is not null
        if (currentShift == null) {
            throw new IllegalArgumentException("Shift cannot be null");
        }
		
        // Assign the employee to the provided shift
        currentShift.setEmployee(employee);
	}
	
	public void saveShift(int taskID) throws Exception {
		//creates a ShiftDB within the method
		ShiftDB shiftDB = new ShiftDB();
		//uses the save 
		shiftDB.saveShift(currentShift, taskID);
		
	}
	
	
}
