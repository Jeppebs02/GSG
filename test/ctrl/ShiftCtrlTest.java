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
import model.AccountPrivileges;
import model.Employee;
import model.Shift;

class ShiftCtrlTest {
	private static final AccountPrivileges EMPLOYEE = null;
	private ShiftCtrl sc;
	private Shift currentShift;
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
		e = new Employee("a1", "b1", "Jane", "Dough", "JD@hot.com", "22112233", "Parken", EMPLOYEE, 10, "111100-3434", "Nix", "nej", "123456789", "1111", "Aalborg");
		// Set test TastID
		taskID = 21;
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() throws Exception {
		//Arrange
		sc = new ShiftCtrl();
		sc.createShift(startTime, endTime);
		currentShift.setEmployee(e);
		//Act
		sc.saveShift(taskID);
		//Assert
		
	}

}
