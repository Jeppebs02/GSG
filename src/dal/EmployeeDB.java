package dal;

import model.AccountPrivileges;
import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dal.DBConnection;

public class EmployeeDB implements EmployeeDBIF {

	private DBConnection dbConecction = DBConnection.getInstance();
	private Connection connection = DBConnection.getInstance().getConnection();
	private static final String find_employee_by_ID =
			"SELECT * FROM Employee WHERE ID = ?;";
	private static final String find_employee_id_from_user_id =
			"SELECT Employee_ID FROM EmployeeUser WHERE User_ID = ?";
	private PreparedStatement findEmployeeByID;
	private PreparedStatement findEmployeeIDFromUserID;
	
	@Override
	public Employee findEmployeeByUserID(int userID) throws Exception {
		Employee employee = null;
		try {
			findEmployeeByID = connection.prepareStatement(find_employee_by_ID);
			ResultSet rs = dbConecction.getResultSetWithPS(findEmployeeByID);
			
			findEmployeeByID.setInt(1, findEmployeeIDFromUserID(userID));
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		return employee;
	}
	
	
	
	public Employee createEmployeeFromResultSet(ResultSet rs) throws SQLException {
        // Fetch required fields from the ResultSet
        int employeeID = rs.getInt("ID");
        String cpr = rs.getString("CPR");
        String securityClearance = rs.getString("SecurityClearance");
        String certification = rs.getString("Certification");
        String accountNr = rs.getString("AccountNr");
        String registrationNr = rs.getString("RegistrationNr");
        String department = rs.getString("Department");

        // Assuming the User fields are placeholders (you can modify as required)
        String userName = "defaultUser"; // Replace with actual field, if available
        String passWord = "defaultPass"; // Replace with actual field, if available
        String firstName = "defaultFirst"; // Replace with actual field, if available
        String lastName = "defaultLast"; // Replace with actual field, if available
        String email = "defaultEmail@example.com"; // Replace with actual field, if available
        String phoneNr = "0000000000"; // Replace with actual field, if available
        String address = "defaultAddress"; // Replace with actual field, if available
        AccountPrivileges accountPrivileges = AccountPrivileges.EMPLOYEE; // Replace with actual privileges, if available

        // Create and return the Employee object
        return new Employee(userName, passWord, firstName, lastName, email, phoneNr, address, accountPrivileges,
                employeeID, cpr, securityClearance, certification, accountNr, registrationNr, department);
    }
}
	
	
	public int findEmployeeIDFromUserID(int userID) {
		int employeeID = 0;
		try {
			findEmployeeIDFromUserID = connection.prepareStatement(find_employee_id_from_user_id);
			ResultSet rs = dbConecction.getResultSetWithPS(findEmployeeIDFromUserID);
			
			if(rs.next()) {
				employeeID = rs.getInt("Employee_ID");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return employeeID;
	}

}
