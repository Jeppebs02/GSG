package dal;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.User;

class UserDBTest {
	UserDB udb;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testUserDB() {
		udb = new UserDB();
		
		User usr = udb.findCustomerByUserID(18);
		
		System.out.println("user email is: "+usr.getEmail());
		
		assertNotNull(usr);
		
		
		
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
