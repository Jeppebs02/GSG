package ctrl;

import java.time.LocalDateTime;

import dal.TaskDB;
import model.Employee;
import model.Shift;
import model.Task;
import model.User;

public class TaskCtrl {

	private Task currentTask;
	
	
	
	public Task createTask(LocalDateTime date, String description, String location, int userID) {
		
		UserCtrl uc = new UserCtrl();
		User u = uc.findCustomerByUserID(userID);
		
		Task task = new Task(date, description, location, u);
		currentTask = task;
		return currentTask;
	}
	
	
	public Shift addShift(LocalDateTime startTime, LocalDateTime expectedEndTime) throws Exception {
		
		ShiftCtrl sc = new ShiftCtrl();
		Shift s = sc.createShift(startTime, expectedEndTime);
		
		currentTask.addShift(s);
		sc.saveShift();
		return s;
	}
	
	
	public void addEmployeeToShift(Shift shift, int userID) throws Exception {
		
		
		UserCtrl uc = new UserCtrl();
		Employee e = uc.findEmployeeByUserID(userID);
		
		ShiftCtrl sc = new ShiftCtrl();
		sc.addEmployeeToShift(e);
		
	}
	
	
	public void saveTask() throws Exception {
		
		TaskDB tb = new TaskDB();
		tb.saveTask(currentTask);
		
		
		
	}
}
