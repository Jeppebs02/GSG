package dal;

import java.util.List;

import model.Task;

public interface TaskDBIF {

	Task saveTask(Task task) throws Exception; 

	List<Task> findAllTasksFromDB(int year, String month) throws Exception;
	
}

	
