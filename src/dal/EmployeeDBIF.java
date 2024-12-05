package dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Employee;

public interface EmployeeDBIF {
	
	public Employee findEmployeeByUserID(int userID) throws Exception ;	
	
	public Employee createEmployeeFromResultSet(ResultSet rs) throws SQLException ;
	
	public int findEmployeeIDFromUserID(int userID);
}
