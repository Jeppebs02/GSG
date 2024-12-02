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
	
	private static final String insert_shift = "INSERT INTO Shift (StartTime, EndTime, Employee_ID) VALUES(?, ?, ?)";
	
	private PreparedStatement insertShift;
	
	public ShiftDB () throws SQLException {
		connection = DBConnection.getInstance().getConnection();
		insertShift = connection.prepareStatement(insert_shift, Statement.RETURN_GENERATED_KEYS);
	}

	@Override
	public Shift saveShift(Shift shift) throws Exception {
	    int shiftID = 0;
	    
	    try {
	        // Start transaction
	        DBConnection.getInstance().startTransaction();

	        // Convert LocalDateTime to Timestamp and set values
	        insertShift.setTimestamp(1, Timestamp.valueOf(shift.getStartTime()));
	        insertShift.setTimestamp(2, Timestamp.valueOf(shift.getEndTime()));
	        insertShift.setInt(3, shift.getEmployee().getEmployeeID());

	        // Execute insert and get generated key
	        shiftID = DBConnection.getInstance().executeSqlInsertWithIdentityPS(insertShift);

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
	public Shift buildObjectShift(ResultSet rs) throws SQLException {
		//Build Shift object from database
		Shift s = new Shift(rs.getInt("ID"), rs.getTime("StartTime"), rs.getTime("EndTime"), buildObjectEmployee(rs.getInt("Employee_ID")));
		return s;
	}


}
