package dal;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ctrl.TaskCtrl;
import model.Shift;
import model.Task;

class TaskDBtestCase {

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
		LocalDateTime date = LocalDateTime.of(2024, 03, 3, 12, 12, 12);
		LocalTime dateStart = LocalTime.of(18, 0);
		LocalTime dateEnd = LocalTime.of(23, 0);
		
		Task currentTask = tc.createTask(date, "ZWEI", "Aalborg", 2);
		
		Shift currentShift = tc.addShift(dateStart, dateEnd);
		
		tc.addEmployeeToShift(currentShift, 1);
		
		tc.saveTask();
		
		
	}
	
	@Test
	public void testConnection() {
		try {
			Connection c = DBConnection.getInstance().getConnection();
			assertNotNull(c);
		} catch (Exception e) {
			fail("Issues with connection");
		}
	}

}
