package dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Shift;

public interface ShiftDBIF {

	Shift saveShift(Shift shift) throws Exception;
	
	Shift buildObjectShift(ResultSet rs) throws SQLException;
}
