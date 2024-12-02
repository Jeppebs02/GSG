package ctrl;

import java.time.LocalDateTime;

import dal.DataAccessException;
import model.Employee;
import model.Shift;

public class ShiftCtrl {
	private Shift currentShift;
	private EmployeeCtrl ec;
	
	public ShiftCtrl() throws DataAccessException {
		this.currentShift = null;
		ec = new EmployeeCtrl();
	}
	
	public Shift createShift(LocalDateTime startTime, LocalDateTime endTime) {
		// Create a new Shift object and assign it to currentShift
		currentShift = new Shift(startTime, endTime);
		
		return currentShift;
	}
	
	public void addEmployeeToShift(Shift shift, int employeeID) {
		// Validate that the Shift object is not null
        if (shift == null) {
            throw new IllegalArgumentException("Shift cannot be null");
        }
		
        // Find the employee using the employeeID
        Employee e = ec.findEmployeeByUserID(employeeID);
        
        // If the employee is not found, throw a NullPointerException
        if (e == null) {
            throw new NullPointerException("Employee with ID " + employeeID + " not found");
        }

        // Assign the employee to the provided shift
        shift.setEmployee(e);
	}
	
}
