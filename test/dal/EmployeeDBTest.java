package dal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import dal.EmployeeDB;
import dal.*;
import model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({})
public class EmployeeDBTest {
	EmployeeDB edb;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testEmployeeDb() throws Exception {
		 edb = new EmployeeDB();
		 
		 Employee emp = edb.findEmployeeByUserID(1);
		 assertNotNull(emp);
		
		
		fail("Not yet implemented");
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
