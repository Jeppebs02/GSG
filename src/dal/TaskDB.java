package dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import model.Task;

public class TaskDB implements TaskDBIF {
	private Connection connection;
	
	private static final String insert_task = "INSERT INTO";
			
	private PreparedStatement insertTask;
	
	public TaskDB () throws SQLException {
		connection = DBConnection.getInstance().getConnection();
		
		insertTask = connection.prepareStatement(insert_task, Statement.RETURN_GENERATED_KEYS);
	}
	@Override
	public Task saveTask(Task task) throws Exception {
	    int taskID = 0;
	    
	    try {
	        // Start transaction
	        DBConnection.getInstance().startTransaction();

	        // Set values
	        insertTask.setString(1, task.getDescription());
	        insertTask.setString(2, task.getLocation());
	        insertTask.setBoolean(3, task.isApproval());
	        insertTask.setDate(4, Date.valueOf(task.getDate()));
	        insertTask.setInt(5, task.getUser().getUserID());
	        

	        // Execute insert and get generated key
	        taskID = DBConnection.getInstance().executeSqlInsertWithIdentityPS(insertTask);

	        // Set the generated ShiftID in the Shift object
	        //task.setTaskID(taskID);

	        // Commit transaction
	        DBConnection.getInstance().commitTransaction();
	    } catch (SQLException e) {
	        // Rollback transaction in case of error
	        DBConnection.getInstance().rollbackTransaction();
	        throw new DataAccessException("Could not save Task to DB", e);
	    }

	    return task;
	}
}	
