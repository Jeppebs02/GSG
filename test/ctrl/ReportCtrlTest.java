package ctrl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Task;
import ctrl.TaskCtrl;
import sqlscripts.SQLManager;

class ReportCtrlTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		ReportCtrl rc = new ReportCtrl();
		TaskCtrl tc = new TaskCtrl(); 
		int rejectionsAge = 4;
		int rejectionsAttitude = 2;
		int rejectionsAlternative = 1;
		String alternativeRemarks = "Generelt god aften";
		String empSig = "OH";
		String cusSig = "KL";
		Task testTask = tc.findTaskByID(1);
	}

	@AfterEach
	void tearDown() throws Exception {
		SQLManager.tearDown();
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
