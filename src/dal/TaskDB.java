package dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Task;
import model.User;

/**
 * The TaskDB class manages database operations related to {@link Task} entities,
 * including inserting tasks into the database and retrieving them by month and year.
 */
public class TaskDB implements TaskDBIF {
    // Connection
	private Connection connection;
    private DBConnection dbConnection;
    
    // SQL queries
    private static final String insert_task = 
        "INSERT INTO [Task] (Description, Location, Approval, Date, User_ID) VALUES (?, ?, ?, ?, ?);";
    private static final String find_all_tasks_per_month = 
        "SELECT ID AS Task_ID, Description, Location, Approval, Date, User_ID FROM [Task] WHERE YEAR(Date) = ? AND MONTH(Date) = ?;";
    private static final String get_task_from_id ="SELECT ID AS Task_ID, Description, Location, Approval, Date, User_ID FROM [Task] WHERE ID=?;";
    
    // Prepared Statements
    private PreparedStatement insertTask;
    private PreparedStatement findAllTasks;
    private PreparedStatement getTaskFromTaskID;
    
    /**
     * Constructs a TaskDB object, initializes the database connection, and prepares 
     * the SQL statements for inserting tasks and finding tasks by year and month.
     * 
     * @throws SQLException if a database access error occurs or if the prepared statements cannot be created.
     */
    public TaskDB() throws SQLException {
        dbConnection = DBConnection.getInstance();
        connection = DBConnection.getInstance().getConnection();

        insertTask = connection.prepareStatement(insert_task, Statement.RETURN_GENERATED_KEYS);
        findAllTasks = connection.prepareStatement(find_all_tasks_per_month);
        getTaskFromTaskID = connection.prepareStatement(get_task_from_id);
    }

    /**
     * Saves the given {@link Task} to the database.
     * 
     * <p>This method starts a transaction, inserts a new Task record into the database, 
     * retrieves the generated task ID, sets it on the {@link Task} object, and commits the transaction. 
     * If an error occurs, it rolls back the transaction and throws a {@link DataAccessException}.</p>
     * 
     * @param task the Task object to be saved.
     * @return the saved Task with its generated task ID set.
     * @throws Exception if an error occurs during the insert operation.
     */
    @Override
    public Task saveTask(Task task) throws Exception {
        int taskID = -1;
        LocalDate date = task.getDate();
        LocalDateTime localDateTime = date.atStartOfDay();
        
        // Set parameters for the insert statement
        insertTask.setString(1, task.getDescription());
        insertTask.setString(2, task.getLocation());
        insertTask.setBoolean(3, task.isApproval());
        insertTask.setTimestamp(4, Timestamp.valueOf(localDateTime));
        insertTask.setInt(5, task.getUser().getUserID());

        try {
            // Execute and retrieve generated key
            taskID = dbConnection.getInstance().executeSqlInsertWithIdentityPS(insertTask);
            task.setTaskID(taskID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return task;
    }

    /**
     * Retrieves all tasks from the database that fall within a specified month and year.
     * 
     * <p>This method prepares a SELECT query using the given year and month, executes it, 
     * and constructs a list of {@link Task} objects from the result set.</p>
     * 
     * @param year  the year to filter tasks by.
     * @param month the month name (e.g. "JANUARY", "FEBRUARY") to filter tasks by.
     * @return a list of Task objects matching the specified year and month.
     * @throws Exception if a database access error occurs or if processing the result set fails.
     */
    @Override
    public List<Task> findAllTasksFromDB(int year, String month) throws Exception {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            findAllTasks = connection.prepareStatement(find_all_tasks_per_month);
            findAllTasks.setInt(1, year);
            findAllTasks.setInt(2, java.time.Month.valueOf(month).getValue());

            ResultSet rs = dbConnection.getResultSetWithPS(findAllTasks);

            while (rs.next()) {
                tasks.add(createTaskFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }

    /**
     * Creates a Task object from the current row of the given {@link ResultSet}.
     * 
     * <p>This method extracts Task data from the result set, 
     * creates a new Task object, and retrieves the associated {@link User} object 
     * using the {@link UserDB} class.</p>
     * 
     * @param rs the ResultSet from which to create the Task.
     * @return a newly created Task populated with data from the result set.
     * @throws SQLException if there is an error accessing the result set data.
     */
    @Override
    public Task createTaskFromResultSet(ResultSet rs) throws SQLException {
        int taskID = rs.getInt("Task_ID");
        String description = rs.getString("Description");
        String location = rs.getString("Location");
        String approval = rs.getString("Approval");
        LocalDate date = rs.getDate("Date").toLocalDate();
        int userID = rs.getInt("User_ID");

        UserDB udb = new UserDB();
        User u = udb.findCustomerByUserID(userID);

        Task task = new Task(date, description, location, u);
        task.setTaskID(taskID);

        return task;
    }

	@Override
	public Task findTaskByID(int taskID) throws Exception {
		getTaskFromTaskID.setInt(1, taskID);
		Task task = null;
		
		try(ResultSet rs = dbConnection.getResultSetWithPS(getTaskFromTaskID)){
			if(rs.next()) {
				System.out.println("Data found in ResultSet");
				task = createTaskFromResultSet(rs);
			} else {
				System.out.println("No data in ResultSet");
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return task;
	}

}
