package ctrl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import dal.DataAccessException;
import dal.ShiftDB;
import model.Employee;
import model.Shift;

/**
 * The ShiftCtrl class manages the creation, modification, and persistence of 
 * {@link Shift} objects within the system. It provides methods to create a new 
 * shift, assign an employee to it, and save it to the database.
 */
public class ShiftCtrl {
    private Shift currentShift;

    /**
     * Constructs a new ShiftCtrl instance with no currently assigned shift.
     */
    public ShiftCtrl() {
        this.currentShift = null;
    }

    /**
     * Creates a new {@link Shift} object with the specified start and end times, 
     * and sets it as the current shift.
     * 
     * @param startTime the start time of the new shift.
     * @param endTime the end time of the new shift.
     * @return the newly created Shift object.
     */
    public Shift createShift(LocalDateTime startTime, LocalDateTime endTime) {
        this.currentShift = new Shift(startTime, endTime);
        return currentShift;
    }

    /**
     * Assigns the given {@link Employee} to the current shift.
     * 
     * @param employee the Employee to be assigned to the current shift.
     * @throws Exception if the current shift is null.
     */
    public void addEmployeeToShift(Employee employee) throws Exception {
        if (currentShift == null) {
            throw new IllegalArgumentException("Shift cannot be null");
        }
        currentShift.setEmployee(employee);
    }

    /**
     * Saves the current shift to the database, associating it with the given task ID.
     * 
     * <p>This method creates a {@link ShiftDB} instance and uses it to save the current shift. 
     * If the current shift has an associated employee, the shift is saved with that employee; 
     * otherwise, it is saved without an employee.</p>
     * 
     * @param taskID the ID of the task this shift is associated with.
     * @throws Exception if a database access error or other issue occurs during save.
     */
    public void saveShift(int taskID) throws Exception {
        ShiftDB shiftDB = new ShiftDB();
        shiftDB.saveShift(currentShift, taskID);
    }

    /**
     * Retrieves the current shift managed by this controller.
     * 
     * @return the current Shift object.
     * @throws NullPointerException if the current shift is null.
     */
    public Shift getCurrentShift() throws NullPointerException {
        if (currentShift == null) {
            throw new NullPointerException("Current Shift is null");
        }
        return this.currentShift;
    }
    
    public Shift findShiftByShiftID(int shiftID) throws Exception {
    	ShiftDB shiftDB = new ShiftDB();
    	Shift s = shiftDB.getShiftByID(shiftID);
    	return s;
    }
    
    public List<Shift> findAllShiftsByTaskID(int taskID) throws SQLException{
    	ShiftDB shiftDB = new ShiftDB();
		return shiftDB.findAllShiftsByTaskIDFromDB(taskID);
    	
    }
    
}
