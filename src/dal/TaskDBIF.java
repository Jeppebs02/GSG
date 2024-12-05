package dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Task;

public interface TaskDBIF {

	Task saveTask(Task task) throws Exception; 

	List<Task> findAllTasksFromDB(int year, String month) throws Exception;
	
	Task createTaskFromResultSet(ResultSet rs) throws SQLException;
	
	Task getTaskByID(int taskID) throws Exception;
}

	
