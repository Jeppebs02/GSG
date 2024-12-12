package model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;



class RatingTestWithNullEmployee {
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
    void testSecurityScoreBoundaryValidWithNullEmployee() {
        // Valid boundaries with Employee as null
        assertDoesNotThrow(() -> new Rating(1, "Low boundary", 3, "Valid service", null));
        assertDoesNotThrow(() -> new Rating(5, "Upper boundary", 3, "Valid service", null));
    }

    @Test
    void testSecurityScoreBoundaryInvalidWithNullEmployee() {
        // Invalid boundaries with Employee as null
        assertThrows(IllegalArgumentException.class, () -> new Rating(0, "Below boundary", 3, "Valid service", null));
        assertThrows(IllegalArgumentException.class, () -> new Rating(6, "Above boundary", 3, "Valid service", null));
    }

    @Test
    void testServiceScoreBoundaryValidWithNullEmployee() {
        // Valid boundaries with Employee as null
        assertDoesNotThrow(() -> new Rating(3, "Valid security", 1, "Low boundary", null));
        assertDoesNotThrow(() -> new Rating(3, "Valid security", 5, "Upper boundary", null));
    }

    @Test
    void testServiceScoreBoundaryInvalidWithNullEmployee() {
        // Invalid boundaries with Employee as null
        assertThrows(IllegalArgumentException.class, () -> new Rating(3, "Valid security", 0, "Below boundary", null));
        assertThrows(IllegalArgumentException.class, () -> new Rating(3, "Valid security", 6, "Above boundary", null));
    }
}
