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

/**
 * The ShiftDB class handles database operations related to the {@link Shift} entity,
 * including inserting shifts (with or without employees) and retrieving shifts by task ID.
 */
public class ShiftDB implements ShiftDBIF {
    private Connection connection;
    private DBConnection dbConnection;

    // SQL queries for inserting and selecting shifts
    private static final String insert_shift_with_employee = 
        "INSERT INTO [Shift] (StartTime, EndTime, Employee_ID, Task_ID) VALUES (?, ?, ?, ?);";
    private static final String insert_shift_without_employee = 
        "INSERT INTO [Shift] (StartTime, EndTime, Task_ID) VALUES(?, ?, ?)";
    private static final String find_shifts_from_task_id = 
        "SELECT StartTime, EndTime, Employee_ID, Task_ID FROM [Shift] WHERE Task_ID =?;";
    private static final String get_shift_from_id ="SELECT * FROM [Shift] WHERE ID=?;";
    
    
    private PreparedStatement insertShiftWithEmployee;
    private PreparedStatement insertShiftWithoutEmployee;
    private PreparedStatement findShiftsFromTaskID;
    private PreparedStatement getShiftFromShiftID;

    /**
     * Constructs a ShiftDB object and prepares the necessary SQL statements.
     *
     * @throws SQLException if there is an error preparing the statements.
     */
    public ShiftDB() throws SQLException {
        dbConnection = DBConnection.getInstance();
        connection = DBConnection.getInstance().getConnection();
        
        insertShiftWithEmployee = connection.prepareStatement(insert_shift_with_employee, Statement.RETURN_GENERATED_KEYS);
        insertShiftWithoutEmployee = connection.prepareStatement(insert_shift_without_employee, Statement.RETURN_GENERATED_KEYS);
        findShiftsFromTaskID = connection.prepareStatement(find_shifts_from_task_id);
        getShiftFromShiftID = connection.prepareStatement(get_shift_from_id);
    }

    /**
     * Saves a shift associated with a specific task and an employee to the database.
     *
     * <p>This method starts a transaction, inserts the shift data into the database, and 
     * retrieves the generated key (shift ID). On success, it commits the transaction and 
     * sets the shift ID on the given Shift object. If an error occurs, it rolls back the 
     * transaction and throws a {@link DataAccessException}.</p>
     *
     * @param shift  the {@link Shift} object to be saved.
     * @param taskID the ID of the task associated with the shift.
     * @return the saved {@link Shift} object with its generated shift ID set.
     * @throws Exception if a database access error or other issue occurs during the operation.
     */
    @Override
    public Shift saveShiftWithEmployee(Shift shift, int taskID) throws Exception {
        int shiftID = 0;
        
        // Set parameters
        insertShiftWithEmployee.setTimestamp(1, Timestamp.valueOf(shift.getStartTime()));
        insertShiftWithEmployee.setTimestamp(2, Timestamp.valueOf(shift.getEndTime()));
        insertShiftWithEmployee.setInt(3, shift.getEmployee().getEmployeeID());
        insertShiftWithEmployee.setInt(4, taskID);

        try {
            // Execute and get generated key
            shiftID = DBConnection.getInstance().executeSqlInsertWithIdentityPS(insertShiftWithEmployee);

            // Set shift ID on the object
            shift.setShiftID(shiftID);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return shift;
    }

    /**
     * Saves a shift associated with a specific task but without a specified employee to the database.
     *
     * <p>This method starts a transaction, inserts the shift data (excluding Employee_ID) 
     * into the database, and retrieves the generated shift ID. On success, it commits the 
     * transaction and sets the shift ID on the given Shift object. If an error occurs, it 
     * rolls back the transaction and throws a {@link DataAccessException}.</p>
     *
     * @param shift  the {@link Shift} object to be saved.
     * @param taskID the ID of the task associated with the shift.
     * @return the saved {@link Shift} object with its generated shift ID set.
     * @throws Exception if a database access error or other issue occurs during the operation.
     */
    @Override
    public Shift saveShiftWithoutEmployee(Shift shift, int taskID) throws Exception {
        int shiftID = -1;
        
        // Set parameters
        insertShiftWithoutEmployee.setTimestamp(1, Timestamp.valueOf(shift.getStartTime()));
        insertShiftWithoutEmployee.setTimestamp(2, Timestamp.valueOf(shift.getEndTime()));
        insertShiftWithoutEmployee.setInt(3, taskID);

        try{
            // Execute and get generated key
            shiftID = dbConnection.getInstance().executeSqlInsertWithIdentityPS(insertShiftWithoutEmployee);
            // Set shift ID on the object
            shift.setShiftID(shiftID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return shift;
    }

    /**
     * Saves a shift to the database, determining automatically whether to include 
     * the employee ID based on whether the Shift object has an employee set.
     *
     * @param shift  the {@link Shift} object to be saved.
     * @param taskID the ID of the task associated with the shift.
     * @return the saved {@link Shift} object with its generated shift ID set.
     * @throws Exception if a database access error or other issue occurs during the operation.
     */
    @Override
    public Shift saveShift(Shift shift, int taskID) throws Exception {
        if (shift.getEmployee() != null) {
            saveShiftWithEmployee(shift, taskID);
        } else {
            saveShiftWithoutEmployee(shift, taskID);
        }
        return shift;
    }

    /**
     * Retrieves all shifts associated with a given task ID from the database.
     *
     * <p>This method executes a SELECT query to find all shifts for the specified task. 
     * It then creates {@link Shift} objects from the result set and returns them in a list.</p>
     *
     * @param taskID the ID of the task whose shifts are to be retrieved.
     * @return a {@link List} of {@link Shift} objects associated with the given task ID.
     * @throws SQLException if a database access error occurs during the query.
     */
    @Override
    public List<Shift> findAllShiftsByTaskIDFromDB(int taskID) throws SQLException {
        List<Shift> shifts = new ArrayList<>();
        
        // Set parameter
        findShiftsFromTaskID.setInt(1, taskID);

        try (ResultSet rs = dbConnection.getResultSetWithPS(findShiftsFromTaskID)) {
            while (rs.next()) {
                // Create Shift from ResultSet
                Shift shift = createShiftFromResultSet(rs);
                // Add to the list
                shifts.add(shift);
            }
        } catch (Exception e) {
            System.out.println("Creating/adding shift went wrong");
            e.printStackTrace();
        }

        return shifts;
    }

    /**
     * Creates a {@link Shift} object from the current row of the provided ResultSet.
     *
     * <p>This method extracts the start time, end time, and employee ID from the result set, 
     * creates a Shift object, and sets the associated Employee by querying the database 
     * using an {@link EmployeeDB} instance.</p>
     *
     * @param rs the {@link ResultSet} positioned at a valid row containing Shift data.
     * @return a {@link Shift} object created from the ResultSet data.
     * @throws Exception if a database access error occurs or if unable to retrieve Employee information.
     */
    @Override
    public Shift createShiftFromResultSet(ResultSet rs) throws Exception {
        // Extract fields
        LocalDateTime startTime = rs.getTimestamp("StartTime").toLocalDateTime();
        LocalDateTime endTime = rs.getTimestamp("EndTime").toLocalDateTime();
        int employeeID = rs.getInt("Employee_ID");

        // Create Shift object
        Shift shift = new Shift(startTime, endTime);

        // Use EmployeeDB to find and set the associated employee
        EmployeeDB eDB = new EmployeeDB();
        shift.setEmployee(eDB.findEmployeeByUserID(employeeID));

        return shift;
    }
    
    /**
     * Creates a {@link Shift} object from a shift ID.
     * @return a {@link Shift}
     * @throws Exception if a database access error occurs or if unable to retrieve Shift information.
     */
	@Override
	public Shift getShiftByID(int shiftID) throws Exception {
		getShiftFromShiftID.setInt(1, shiftID);
		Shift shift = null;
		
		try(ResultSet rs = dbConnection.getResultSetWithPS(getShiftFromShiftID)){
			if(rs.next()) {
				System.out.println("Data found in ResultSet");
				shift = createShiftFromResultSet(rs);
			} else {
				System.out.println("No data in ResultSet");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return shift;
	}

}
