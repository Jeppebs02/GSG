package dal;

import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dal.DBConnection;

public class EmployeeDB implements EmployeeDBIF {

	private Connection dbConnection = DBConnection.getInstance().getConnection();
	private static final String find_employee_by_ID =
			"SELECT * FROM Employee WHERE ID = ?;";
	
	private PreparedStatement findEmployeeByID;
	
	@Override
	public Employee findEmployeeByID(int employeeID) {
		
		try {
			findEmployeeByID = dbConnection.prepareStatement(find_employee_by_ID);
			
			
		}catch(SQLException e){
			
		}
		
		return null;
	}

}
