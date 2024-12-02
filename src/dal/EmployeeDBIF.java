package dal;

import model.Employee;

public interface EmployeeDBIF {
	

	
	public Employee findEmployeeByUserID(int userID) throws Exception ;	
	
}
