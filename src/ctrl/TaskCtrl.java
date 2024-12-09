package ctrl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import dal.TaskDB;
import model.Report;
import dal.ReportDB;
import model.Employee;
import model.Shift;
import model.Task;
import model.User;

/**
 * The TaskCtrl class manages the lifecycle of {@link Task} objects, including creation,
 * association with shifts, employees, and reports. It also interacts with the database
 * via {@link TaskDB} and {@link ReportDB} to persist and retrieve task-related data.
 */
public class TaskCtrl {

    private Task currentTask;
    private ShiftCtrl sc;
    private TaskDB tb;
    private ReportCtrl rc;

    /**
     * Constructs a TaskCtrl instance and initializes a TaskDB instance 
     * for database operations.
     * 
     * @throws SQLException if a database access error occurs.
     */
    public TaskCtrl() throws SQLException {
        this.tb = new TaskDB();
        this.sc = new ShiftCtrl();
        this.rc = new ReportCtrl();
    }

    /**
     * Creates a new {@link Task} with the specified date, description, location, 
     * and user ID. The user is retrieved from the database, associated with the task,
     * and the task is then saved to the database.
     *
     * @param date the date associated with the task.
     * @param description a textual description of the task.
     * @param location the location where the task is to be performed.
     * @param userID the ID of the user (customer or guest) associated with the task.
     * @return the newly created and saved {@link Task}.
     * @throws Exception if an error occurs during user retrieval or task saving.
     */
    public Task createTask(LocalDate date, String description, String location, int userID) throws Exception {
        UserCtrl uc = new UserCtrl();
        User u = uc.findCustomerByUserID(userID);

        currentTask = new Task(date, description, location, u);
        saveTask();
        return currentTask;
    }

    /**
     * Adds a new {@link Shift} to the current task using the specified start and expected end times.
     * The shift is created using a {@link ShiftCtrl} instance and then associated with the current task.
     * 
     * @param startTime the start time of the shift.
     * @param expectedEndTime the expected end time of the shift.
     * @return the newly created {@link Shift} object.
     * @throws Exception if an error occurs during shift creation.
     */
    public Shift addShift(LocalDateTime startTime, LocalDateTime expectedEndTime) throws Exception {
        Shift s = sc.createShift(startTime, expectedEndTime);
        currentTask.addShift(s);
        return s;
    }

    /**
     * Assigns an {@link Employee} to the current shift of the current task by using the user ID.
     * The employee is retrieved from the database and then assigned to the current shift through
     * the ShiftCtrl instance. The shift is saved to the database afterwards.
     * 
     * @param userID the user ID associated with the employee to be assigned.
     * @throws Exception if an error occurs during employee retrieval or shift saving.
     */
    public void addEmployeeToShift(int userID) throws Exception {
        UserCtrl uc = new UserCtrl();
        Employee e = uc.findEmployeeByUserID(userID);

        sc.addEmployeeToShift(e);
        sc.saveShift(currentTask.getTaskID());
    }

    /**
     * Saves the current task to the database. This method creates a new {@link Report}
     * with default or placeholder values, associates it with the task, and then saves both the task 
     * (using TaskDB) and the report (using ReportDB) to the database.
     * 
     * @throws Exception if an error occurs during the task or report saving processes.
     */
    public void saveTask() throws Exception {
       
        // Save the current task to the database and retrieve the newly assigned Task ID
        Task createdTask = tb.saveTask(currentTask);
        rc.saveReport(createdTask);
    }

    

    /**
     * Retrieves the current task managed by this controller.
     * 
     * @return the current {@link Task} object.
     * @throws NullPointerException if the current task is null.
     */
    public Task getCurrentTask() throws NullPointerException {
        if (this.currentTask == null) {
            throw new NullPointerException("Task is null");
        }
        return this.currentTask;
    }

    /**
     * Retrieves all tasks from the database that fall within the specified month and year.
     * 
     * @param year the year to filter tasks by.
     * @param month the name of the month (e.g. "JANUARY", "FEBRUARY") to filter tasks by.
     * @return a list of {@link Task} objects that match the criteria.
     * @throws Exception if a database access error or other issue occurs during retrieval.
     */
    public List<Task> findAllTasks(int year, String month) throws Exception {
        return tb.findAllTasksFromDB(year, month);
    }
    
    public Task findTaskByID(int taskID) throws Exception {
    	TaskDB taskDB = new TaskDB();
    	Task t = taskDB.getTaskByID(taskID);
    	return t;
    }
}
