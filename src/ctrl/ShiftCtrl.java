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
		currentShift = new Shift(startTime, endTime);
		
		return currentShift;
	}
	
	public void addEmployeeToShift(Shift shift, int employeeID) {
		Employee e = ec.findEmployeeByID(employeeID);
		if (e == null) {
			throw new NullPointerException();
		}
		currentShift.setEmployee(e);
	}
	
}
