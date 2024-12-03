package dal;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Shift;

public class ShiftDB implements ShiftDBIF {
	private Connection connection;
	
	private static final String insert_shift_with_employee = "INSERT INTO [Shift] (StartTime, EndTime, Employee_ID, Task_ID) VALUES (?, ?, ?, ?);";
	private static final String insert_shift_without_employee = "INSERT INTO [Shift] (StartTime, EndTime, Task_ID) VALUES(?, ?, ?)";
	
	
	private PreparedStatement insertShiftWithEmployee;
	private PreparedStatement insertShiftWithoutEmployee;
	
	public ShiftDB () throws SQLException {
		connection = DBConnection.getInstance().getConnection();
		insertShiftWithEmployee = connection.prepareStatement(insert_shift_with_employee, Statement.RETURN_GENERATED_KEYS);
		insertShiftWithoutEmployee = connection.prepareStatement(insert_shift_without_employee, Statement.RETURN_GENERATED_KEYS);
	}

	
	public Shift saveShiftWithEmployee (Shift shift, int taskID) throws Exception {
	    int shiftID = 0;
	    
	    try {
	        // Start transaction
	        DBConnection.getInstance().startTransaction();

	        // Convert LocalDateTime to Timestamp and set values
	        insertShiftWithEmployee.setTimestamp(1, Timestamp.valueOf(shift.getStartTime()));
	        insertShiftWithEmployee.setTimestamp(2, Timestamp.valueOf(shift.getEndTime()));
	        insertShiftWithEmployee.setInt(3, shift.getEmployee().getEmployeeID());
	        insertShiftWithEmployee.setInt(4,  taskID);

	        // Execute insert and get generated key
	        shiftID = DBConnection.getInstance().executeSqlInsertWithIdentityPS(insertShiftWithEmployee);

	        // Set the generated ShiftID in the Shift object
	        shift.setShiftID(shiftID);

	        // Commit transaction
	        DBConnection.getInstance().commitTransaction();
	    } catch (SQLException e) {
	        // Rollback transaction in case of error
	        DBConnection.getInstance().rollbackTransaction();
	        throw new DataAccessException("Could not save Shift to DB", e);
	    }

	    return shift;
	}

	
	public Shift saveShiftWithoutEmployee(Shift shift, int taskID) throws Exception {
	    int shiftID = 0;
	    
	    try {
	        // Start transaction
	        DBConnection.getInstance().startTransaction();

	        // Convert LocalDateTime to Timestamp and set values
	        insertShiftWithoutEmployee.setTimestamp(1, Timestamp.valueOf(shift.getStartTime()));
	        insertShiftWithoutEmployee.setTimestamp(2, Timestamp.valueOf(shift.getEndTime())); 
	        insertShiftWithoutEmployee.setInt(3,  taskID);

	        // Execute insert and get generated key
	        shiftID = DBConnection.getInstance().executeSqlInsertWithIdentityPS(insertShiftWithoutEmployee);

	        // Set the generated ShiftID in the Shift object
	        shift.setShiftID(shiftID);

	        // Commit transaction
	        DBConnection.getInstance().commitTransaction();
	    } catch (SQLException e) {
	        // Rollback transaction in case of error
	        DBConnection.getInstance().rollbackTransaction();
	        throw new DataAccessException("Could not save Shift to DB", e);
	    }

	    return shift;
	}

	@Override
	public Shift saveShift(Shift shift, int taskID) throws Exception {
		if(shift.getEmployee() != null) {
			saveShiftWithEmployee(shift, shift.getShiftID());
		} else {
			System.out.println("");
			saveShiftWithoutEmployee(shift, taskID);
		}
		return shift;
	}

	//@Override
	//public Shift buildObjectShift(ResultSet rs) throws SQLException {
		//Build Shift object from database
		//Shift s = new Shift(rs.getInt("ID"), rs.getTime("StartTime"), rs.getTime("EndTime"), findEmployeeByUserID(rs.getInt("Employee_ID")));
		//return s;
	//}


}
