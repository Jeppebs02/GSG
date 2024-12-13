package dal;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Report;
import model.Task;
import model.User;
import sqlscripts.SQLManager;

class ReportDBTest {
	private ReportDB rDB;
	private Report testReport;
	private int rejectionsAge;
	private int rejectionsAttitude;
	private int rejectionsAlternative;
	private String alternativeRemarks;
	private String empSig;
	private String cusSig;
	
	private TaskDB tDB;
	private Task testTask;
	private UserDB uDB;
	private User testUser; 

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		rDB = new ReportDB();
		tDB = new TaskDB();
		uDB = new UserDB();
	}
	
	@AfterEach
	void tearDown() throws Exception {
		SQLManager.tearDown();
	}
	
	@Test
	void saveReportTest() throws Exception {
		// arrange
		rejectionsAge = 4;
		rejectionsAttitude = 2;
		rejectionsAlternative = 1;
		alternativeRemarks = "Generelt god aften";
		empSig = "OH";
		cusSig = "KL";
		
		testUser = uDB.findCustomerByUserID(11);
		testTask = new Task(LocalDate.of(2024, 12, 9), "TestTask", "TestLocation", testUser);
		
		try {
			testTask = tDB.saveTask(testTask);
		} catch (Exception e) {
			System.out.println("Saving Task went wrong");
			e.printStackTrace();
		}
		
		testReport = new Report(rejectionsAge, rejectionsAttitude, rejectionsAlternative, alternativeRemarks, empSig, cusSig);
		testReport.setTaskID(testTask.getTaskID());
		
		// act
		try {
			rDB.saveReportToDb(testReport);
		} catch (Exception e) {
			System.out.println("Saving Report went wrong");
			e.printStackTrace();
		}
		
		// Assert
		assertEquals("Generelt god aften", rDB.findReportByTaskID(testTask.getTaskID()).getAlternativeRemarks());
	}

}
