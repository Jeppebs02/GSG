package dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Shift;

public interface ShiftDBIF {
	

	public Shift saveShiftWithEmployee(Shift shift, int taskID) throws Exception;
	
	public Shift saveShiftWithoutEmployee(Shift shift, int taskID) throws Exception;
	
	public Shift saveShift(Shift shift, int taskID) throws Exception;
	
<<<<<<< Updated upstream
	List<Shift> findAllShiftsByTaskIDFromDB(int taskID) throws SQLException;
	
	Shift createShiftFromResultSet(ResultSet rs) throws Exception;
=======
	public List<Shift> findAllShiftsByTaskIDFromDB(int taskID) throws SQLException;
	
	public Shift findShiftByShiftID(int shiftID) throws Exception;
>>>>>>> Stashed changes
}
