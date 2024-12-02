package ctrl;

import java.time.LocalDateTime;

import model.Shift;
import model.Task;
import model.User;

public class TaskCtrl {

	private Task currentTask;
	private Shift currentShift;
	
	
	public Task createTask(LocalDateTime date, String description, String location, int userID) {
		
		UserCtrl uc = new UserCtrl();
		User u = uc.findCustomerByUserID(userID);
		
		Task task = new Task(date, description, location, u);
		currentTask = task;
		return currentTask;
	}
	
	//TODO
	public Shift addShift(LocalDateTime startTime, LocalDateTime expectedEndTime) {
		
		ShiftCtrl sc = new ShiftCtrl();
		Shift s = sc.createShift(startTime, expectedEndTime);
		
		currentTask.addShift(s);
		return s;
	}
	//TODO
	public void addEmployeeToShift(Shift shift, String employeeID) {	
	}
	//TODO
	public boolean saveTask() {
		return false;
	}
}
