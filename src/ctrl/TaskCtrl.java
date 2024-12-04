package ctrl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import dal.TaskDB;
import model.Report;
import dal.ReportDB;
import model.Employee;
import model.Shift;
import model.Task;
import model.User;

public class TaskCtrl {

	private Task currentTask;
	private ShiftCtrl sc = new ShiftCtrl();;
	
	
	public Task createTask(LocalDate date, String description, String location, int userID) throws Exception {
		
		UserCtrl uc = new UserCtrl();
		User u = uc.findCustomerByUserID(userID);
		
		currentTask = new Task(date, description, location, u);
		
		saveTask();
		return currentTask;
	}
	
	
	public Shift addShift(LocalDateTime startTime, LocalDateTime expectedEndTime) throws Exception {
		
		Shift s = sc.createShift(startTime, expectedEndTime);
		
		currentTask.addShift(s);
		//sc.saveShift(currentTask.getTaskID());
		return s;
	}
	
	
	public void addEmployeeToShift(int userID) throws Exception {
		
		UserCtrl uc = new UserCtrl();
		Employee e = uc.findEmployeeByUserID(userID);
		
		sc.addEmployeeToShift(e);
		
		sc.saveShift(currentTask.getTaskID());
	}
	
	
	public void saveTask() throws Exception {
		//ReportDB to save the report
		ReportDB repDB = new ReportDB();
		
		//This is the current task but with an ID
		Task createdTask;
		Report report = createReport(0, 0, 0, "", "", "");
		
		TaskDB tb = new TaskDB();
		createdTask = tb.saveTask(currentTask);
		
		report.setTaskID(createdTask.getTaskID());
		repDB.saveReportToDb(report);
		
	}
	
	//create a new report object
	public Report createReport(int rejectionsAge, int rejectionsAttitude, int rejectionsAlternative, String alternativeRemarks, String employeeSignature, String customerSignature) {
		Report rep = new Report(rejectionsAge,rejectionsAttitude,rejectionsAlternative,alternativeRemarks,employeeSignature,customerSignature);
		return rep;
	}
	
	public Task getCurrentTask() throws NullPointerException {
		if (this.currentTask == null) {
			throw new NullPointerException("Task is null");
		}
		return this.currentTask;
	}
}
