package ctrl;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ctrl.TaskCtrl;
import model.Shift;
import model.Task;

class TaskCtrltestCase {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCreateTask() throws Exception {
		TaskCtrl tc = new TaskCtrl();
		LocalDate date = LocalDate.of(2024, 03, 3);
		LocalDateTime StartTime = LocalDateTime.of(2024, 03, 3, 18, 0, 0);
		LocalDateTime EndTime = LocalDateTime.of(2024, 03, 3, 23, 0, 0);
		
		Task currentTask = tc.createTask(date, "ZWEI", "Aalborg", 2);
		
		Shift currentShift = tc.addShift(StartTime, EndTime);
		
		tc.addEmployeeToShift(currentShift, 1);
		
	}
	
	@Test 
	void testCreateTaskMultibleShifts() throws Exception {
		TaskCtrl tc = new TaskCtrl();
		LocalDate date = LocalDate.of(2024, 03, 3);
		LocalDateTime StartTimeOne = LocalDateTime.of(2024, 03, 3, 18, 0, 0);
		LocalDateTime EndTimeOne = LocalDateTime.of(2024, 03, 3, 23, 0, 0);
		LocalDateTime StartTimeTwo = LocalDateTime.of(2024, 03, 3, 14, 0, 0);
		LocalDateTime EndTimeTwo = LocalDateTime.of(2024, 03, 3, 20, 0, 0);
		
		Task currentTask = tc.createTask(date, "Heidis", "Aarhus", 2);
		
		Shift currentShiftOne = tc.addShift(StartTimeOne, EndTimeOne);
		
		tc.addEmployeeToShift(currentShiftOne, 1);
		
		Shift currentShiftTwo = tc.addShift(StartTimeTwo, EndTimeTwo);
		
		tc.addEmployeeToShift(currentShiftTwo, 3);
		
		
		
	}

}
