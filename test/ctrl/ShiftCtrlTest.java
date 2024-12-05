package ctrl;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dal.DBConnection;
import dal.EmployeeDB;
import model.AccountPrivileges;
import model.Employee;
import model.Shift;

class ShiftCtrlTest {
	private ShiftCtrl sc;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Employee e;
    private int taskID;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		// Create connection to DB
		Connection c = DBConnection.getInstance().getConnection();
		// Define startTime and endTime as LocalDateTime values		
		startTime = LocalDateTime.of(2024, 12, 3, 9, 0); // Example: Dec 3, 2024, 09:00
		endTime = LocalDateTime.of(2024, 12, 3, 17, 0);  // Example: Dec 3, 2024, 17:00
		// Create Test Employee with Stubs
		EmployeeDB eDB = new EmployeeDB();
		e = eDB.findEmployeeByUserID(1);
		// Set test TastID - stub
		taskID = 1;
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void createShiftAndSaveToDB() throws Exception {
		//Arrange
		sc = new ShiftCtrl();
		
		//Act
		sc.createShift(startTime, endTime);
		sc.saveShift(taskID);
		sc.addEmployeeToShift(e);
		//Assert
		assertNotNull(sc.getCurrentShift());
	}
	
	@Test
	void createShiftAndSaveToDBSpecific() throws Exception {
		//Arrange
		sc = new ShiftCtrl();
		
		//Act
		sc.createShift(startTime, endTime);
		sc.saveShift(taskID);
		sc.addEmployeeToShift(e);
		
		//Assert
		assertEquals();
	}

}
