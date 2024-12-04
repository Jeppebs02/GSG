package dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Shift;

public interface ShiftDBIF {

	Shift saveShiftWithEmployee(Shift shift, int taskID) throws Exception;
	
	Shift saveShiftWithoutEmployee(Shift shift, int taskID) throws Exception;
	
	Shift saveShift(Shift shift, int taskID) throws Exception;
	
	List<Shift> findAllShiftsByTaskIDFromDB(int taskID) throws SQLException;
}
