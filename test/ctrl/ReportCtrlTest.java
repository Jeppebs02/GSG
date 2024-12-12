package ctrl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Report;
import model.Task;
import ctrl.TaskCtrl;
import sqlscripts.SQLManager;

class ReportCtrlTest {
	private Report testReport; 
	private ReportCtrl rc;
	private TaskCtrl tc;
	private int rejectionsAge;
	private int rejectionsAttitude;
	private int rejectionsAlternative;
	private String alternativeRemarks;
	private String empSig;
	private String cusSig;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		rc = new ReportCtrl();
		tc = new TaskCtrl();
		rejectionsAge = 4;
		rejectionsAttitude = 2;
		rejectionsAlternative = 1;
		alternativeRemarks = "Generelt god aften";
		empSig = "OH";
		cusSig = "KL";
	}

	@AfterEach
	void tearDown() throws Exception {
		SQLManager.tearDown();
	}

	@Test
	void testCreateReport() {
		// arrange
		
		// act
		testReport = rc.createReport(rejectionsAge, rejectionsAttitude, rejectionsAlternative, alternativeRemarks, empSig, cusSig);
		
		// assert
		assertEquals("OH", testReport.getEmployeeSignature());
	}
	
	@Test
	void testSaveReport() throws Exception {
		// arrange
		tc.createTask(LocalDate.of(2024, 12, 9), "TestTask", "TestLocation", 15);
		//tc.saveTask();
		
		// act
		try {
			rc.saveReport(tc.getCurrentTask(), rejectionsAge, rejectionsAttitude, rejectionsAlternative, alternativeRemarks, empSig, empSig);
		} catch (Exception e) {
			System.out.println("Saving report with task ID failed");
			e.printStackTrace();
		}
		
		// assert
		assertEquals(4 ,rc.findReportByTaskID(7).getRejectionsAge());
	}
	
	@Test 
	void testFindReportByTaskID() throws Exception {
		// arrange
		
		// act
		testReport = rc.findReportByTaskID(tc.findTaskByID(1).getTaskID());
		
		// assert
		assertEquals(1 , testReport.getRejectionsAttitude());
	}
	
	@Test
	void testDeleteReportByTaskID() {
		// arrange
		
		// act
		
		// assert
	}
	
	@Test
	void testUpdateReportByTaskID() {
		// arrange
		
		// act
		
		// assert
	}

}
