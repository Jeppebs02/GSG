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
import model.User;
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
	void testFindReportByTaskID() throws Exception {
		// arrange
		
		// act
		testReport = rc.findReportByTaskID(tc.findTaskByID(1).getTaskID());
		
		// assert
		assertEquals(2 , testReport.getRejectionsAttitude());
	}
	
	@Test
	void testDeleteReportByTaskID() throws Exception {
		// arrange
		
		// act
		boolean result = rc.deleteReportByTaskID(1);
		// assert
		assertTrue(result);
	    
	}
	
	@Test
	void testUpdateReportByTaskID() throws Exception {
		// arrange
		Task testTask = tc.findTaskByID(1);
		
		// act
		rc.updateReportByTaskID(rejectionsAlternative, rejectionsAge, rejectionsAge, alternativeRemarks, empSig, cusSig, testTask);
		
		// assert
		assertEquals("Generelt god aften", rc.findReportByTaskID(1).getAlternativeRemarks());
	}

}
