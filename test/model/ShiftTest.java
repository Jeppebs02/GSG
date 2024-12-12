package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sqlscripts.SQLManager;

class ShiftTest {

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
    void startTimeLaterThanEndTimeConstructorTest() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Shift(LocalDateTime.of(2024, 12, 3, 18, 0), LocalDateTime.of(2024, 12, 3, 17, 0));
        });
    }
    
    @Test
    void setStartTimeLaterThanEndTimeTest() {
        // Arrange
        Shift testShift = new Shift(LocalDateTime.of(2024, 12, 3, 18, 0), LocalDateTime.of(2024, 12, 3, 19, 0));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            testShift.setStartTime(LocalDateTime.of(2024, 12, 3, 21, 30));
        });
    }
    
    @Test
    void setEndTimeEarlierThanStartTimeTest() {
    	// Arrange
    	Shift testShift = new Shift(LocalDateTime.of(2024, 12, 3, 18, 0), LocalDateTime.of(2024, 12, 3, 19, 0));
    	
    	// Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            testShift.setEndTime(LocalDateTime.of(2024, 12, 3, 17, 30));
        });
    }
}

