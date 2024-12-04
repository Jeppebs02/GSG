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
		//Arrange
		TaskCtrl tc = new TaskCtrl();
		LocalDate date = LocalDate.of(2024, 03, 3);
		LocalDateTime StartTime = LocalDateTime.of(2024, 03, 3, 18, 0, 0);
		LocalDateTime EndTime = LocalDateTime.of(2024, 03, 3, 23, 0, 0);
		
		//Act
		tc.createTask(date, "ZWEI", "Aalborg", 2);
		
		Shift currentShift = tc.addShift(StartTime, EndTime);
		
		tc.addEmployeeToShift(1);
		
		//Assert
		assertNotNull(tc.getCurrentTask());
	}
	
	@Test 
	void testCreateTaskMultibleShifts() throws Exception {
		//Arrange
		TaskCtrl tc = new TaskCtrl();
		LocalDate date = LocalDate.of(2024, 03, 3);
		LocalDateTime StartTimeOne = LocalDateTime.of(2024, 03, 3, 18, 0, 0);
		LocalDateTime EndTimeOne = LocalDateTime.of(2024, 03, 3, 23, 0, 0);
		LocalDateTime StartTimeTwo = LocalDateTime.of(2024, 03, 3, 14, 0, 0);
		LocalDateTime EndTimeTwo = LocalDateTime.of(2024, 03, 3, 20, 0, 0);
		
		//Act
		tc.createTask(date, "Heidis", "Aarhus", 2);
		
		tc.addShift(StartTimeOne, EndTimeOne);
		
		tc.addEmployeeToShift(1);
		
		tc.addShift(StartTimeTwo, EndTimeTwo);
		
		tc.addEmployeeToShift(3);
		
		//Assert
		assertNotNull(tc.getCurrentTask());
	}

}
