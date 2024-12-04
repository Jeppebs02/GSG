package dal;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
	private static final String find_shifts_from_task_id = "SELECT StartTime, EndTime, Employee_ID, Task_ID FROM [Shift] WHERE Task_ID =?;";

	private PreparedStatement insertShiftWithEmployee;
	private PreparedStatement insertShiftWithoutEmployee;
	private PreparedStatement findShiftsFromTaskID;

	public ShiftDB() throws SQLException {
		connection = DBConnection.getInstance().getConnection();
		insertShiftWithEmployee = connection.prepareStatement(insert_shift_with_employee,
				Statement.RETURN_GENERATED_KEYS);
		insertShiftWithoutEmployee = connection.prepareStatement(insert_shift_without_employee,
				Statement.RETURN_GENERATED_KEYS);
		findShiftsFromTaskID = connection.prepareStatement(find_shifts_from_task_id);
	}

	public Shift saveShiftWithEmployee(Shift shift, int taskID) throws Exception {
		int shiftID = 0;

		try {
			// Start transaction
			DBConnection.getInstance().startTransaction();
			// Convert LocalDateTime to Timestamp and set values
			insertShiftWithEmployee.setTimestamp(1, Timestamp.valueOf(shift.getStartTime()));
			insertShiftWithEmployee.setTimestamp(2, Timestamp.valueOf(shift.getEndTime()));
			insertShiftWithEmployee.setInt(3, shift.getEmployee().getEmployeeID());
			insertShiftWithEmployee.setInt(4, taskID);
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
			insertShiftWithoutEmployee.setInt(3, taskID);

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
		if (shift.getEmployee() != null) {
			saveShiftWithEmployee(shift, taskID);
		} else {
			System.out.println("");
			saveShiftWithoutEmployee(shift, taskID);
		}
		return shift;
	}

	@Override
	public List<Shift> findAllShiftsByTaskIDFromDB(int taskID) throws SQLException {
		// create ArrayList to hold all shifts
		List<Shift> shifts = new ArrayList<>();
		
		// Set the Task_ID parameter in the query
		findShiftsFromTaskID.setInt(1, taskID);

			try (ResultSet rs = findShiftsFromTaskID.executeQuery()) {
				while (rs.next()) {
					// Create Shift from ResultSet
					Shift shift = createShiftFromResultSet(rs);
					// Add to the list
					shifts.add(shift);
				}
			} catch (Exception e) {
				System.out.println("creating/adding shift went wrong");
				e.printStackTrace();
			}
		
		// Return the list of shifts
		return shifts;
	}

	public Shift createShiftFromResultSet(ResultSet rs) throws Exception {
		// Shift specific fields
		LocalDateTime startTime = rs.getTimestamp("StartTime").toLocalDateTime();
		LocalDateTime endTime = rs.getTimestamp("EndTime").toLocalDateTime();
		int employeeID = rs.getInt("Employee_ID");
		
		// create Shift with values, create EmployeeDB to use method to find, create and set the employee of the given shift
		EmployeeDB eDB = new EmployeeDB();
		Shift shift = new Shift(startTime, endTime);
		shift.setEmployee(eDB.findEmployeeByUserID(employeeID));

		return shift;
	}

}
