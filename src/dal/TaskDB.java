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

public class TaskDB implements TaskDBIF {

	private Connection connection;

	private static final String insert_task = "INSERT INTO [Task] (Description, Location, Approval, Date, User_ID) VALUES (?, ?, ?, ?, ?);";
	private static final String find_all_tasks_per_month = "SELECT ID AS Task_ID, Description, Location, Approval, Date, User_ID FROM [Task] WHERE YEAR(Date) = ? AND MONTH(Date) = ?;";

	private PreparedStatement insertTask;
	private PreparedStatement findAllTasks;

	private DBConnection dbConnection;

	public TaskDB() throws SQLException {
		dbConnection = DBConnection.getInstance();
		connection = DBConnection.getInstance().getConnection();

		insertTask = connection.prepareStatement(insert_task, Statement.RETURN_GENERATED_KEYS);

		findAllTasks = connection.prepareStatement(find_all_tasks_per_month);
	}

	@Override
	public Task saveTask(Task task) throws Exception {
		int taskID = 0;

		LocalDate date = task.getDate();
		LocalDateTime localDateTime = date.atStartOfDay();

		try {
			// Start transaction
			DBConnection.getInstance().startTransaction();

			// Set values
			insertTask.setString(1, task.getDescription());
			insertTask.setString(2, task.getLocation());
			insertTask.setBoolean(3, task.isApproval());
			insertTask.setTimestamp(4, Timestamp.valueOf(localDateTime));
			insertTask.setInt(5, task.getUser().getUserID());

			// Execute insert and get generated key
			taskID = DBConnection.getInstance().executeSqlInsertWithIdentityPS(insertTask);

			// Set the generated ShiftID in the Shift object
			task.setTaskID(taskID);

			// Commit transaction
			DBConnection.getInstance().commitTransaction();
		} catch (SQLException e) {
			// Rollback transaction in case of error
			DBConnection.getInstance().rollbackTransaction();
			throw new DataAccessException("Could not save Task to DB", e);
		}

		return task;
	}

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
		return task;
	}

}
