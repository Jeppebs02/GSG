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

class TaskCtrlTest {
	private LocalDate date;
	private LocalDateTime startTimeOne;
	private LocalDateTime endTimeOne;
	private LocalDateTime startTimeTwo;
	private LocalDateTime endTimeTwo;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		date = LocalDate.of(2024, 03, 3);
		startTimeOne = LocalDateTime.of(2024, 03, 3, 18, 0, 0);
		endTimeOne = LocalDateTime.of(2024, 03, 3, 23, 0, 0);
		startTimeTwo = LocalDateTime.of(2024, 03, 3, 14, 0, 0);
		endTimeTwo = LocalDateTime.of(2024, 03, 3, 20, 0, 0);
	}

	@Test
	void testCreateTask() throws Exception {
		//Arrange
		TaskCtrl tc = new TaskCtrl();
	
		//Act
		tc.createTask(date, "ZWEI", "Aalborg", 2);	
		Shift currentShift = tc.addShift(startTimeOne, endTimeOne);	
		tc.addEmployeeToShift(1);
		
		//Assert
		assertEquals(2, tc.getCurrentTask().getTaskID());
	}
	
	@Test 
	void testCreateTaskMultibleShifts() throws Exception {
		//Arrange
		TaskCtrl tc = new TaskCtrl();

		
		//Act
		tc.createTask(date, "Heidis", "Aarhus", 2);
		
		tc.addShift(startTimeOne, endTimeOne);
		tc.addEmployeeToShift(1);
		
		tc.addShift(startTimeTwo, endTimeTwo);
		tc.addEmployeeToShift(3);
		
		//Assert
		assertEquals(2, tc.getCurrentTask().getShifts().size());
	}
	
	@Test
	void testSaveTask() throws Exception {
		//Arrange
		TaskCtrl tc = new TaskCtrl();
		
		//Act
		tc.createTask(date, "Heidis", "Aarhus", 2);
		
		tc.addShift(startTimeOne, endTimeOne);
		tc.addEmployeeToShift(1);
		
		tc.saveTask();
		//Assert
		
		assertEquals(2, tc.findTaskByID(2));
	}

}
