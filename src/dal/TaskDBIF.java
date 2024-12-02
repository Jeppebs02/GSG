package dal;

import model.Task;

public interface TaskDBIF {

	Task saveTask(Task task) throws Exception; 
}
