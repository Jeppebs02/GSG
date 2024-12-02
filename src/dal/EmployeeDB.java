package dal;

import model.AccountPrivileges;
import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dal.DBConnection;

public class EmployeeDB implements EmployeeDBIF {

	private DBConnection dbConecction = DBConnection.getInstance();
	private Connection connection = DBConnection.getInstance().getConnection();
	private static final String find_employee_id_by_user_id =
			"SELECT User_ID,Employee_ID from EmployeeUser  WHERE User_ID = ?;";
	private static final String find_employee_info_from_user_id_and_employee_id =
			"SELECT Employee.ID AS EmployeeID, Employee.CPR, Employee.SecurityClearance, Employee.AccountNr, Employee.Certification, Employee.RegistrationNr, Employee.Department, [User].ID AS UserID, [User].UserName, [User].Password, [User].FirstName, [User].LastName, [User].Email, [User].PhoneNr, [User].Type, [User].Address_ID, [User].AccountPrivileges FROM Employee JOIN [User] ON Employee.ID = [User].ID WHERE Employee.ID = ? AND [User].ID = ?;";
	
	
	private PreparedStatement findEmployeeIDByUserID;
	private PreparedStatement findEmployeeInfoFromUserIDAndEmployeeID;
	
	@Override
	public Employee findEmployeeByUserID(int userID) throws Exception {
		Employee employee = null;
		try {

			
			findEmployeeInfoFromUserIDAndEmployeeID = connection.prepareStatement(find_employee_info_from_user_id_and_employee_id);
			
			findEmployeeIDByUserID.setInt(1, findEmployeeIDFromUserID(userID));
			findEmployeeIDByUserID.setInt(2, userID);
			
			ResultSet rs = dbConecction.getResultSetWithPS(findEmployeeInfoFromUserIDAndEmployeeID);
			
			employee=createEmployeeFromResultSet(rs);
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		return employee;
	}
	
	
	
	
	public Employee createEmployeeFromResultSet(ResultSet rs) throws SQLException {
		// Employee-specific fields
        int employeeID = rs.getInt("EmployeeID");
        String cpr = rs.getString("CPR");
        String securityClearance = rs.getString("SecurityClearance");
        String certification = rs.getString("Certification");
        String accountNr = rs.getString("AccountNr");
        String registrationNr = rs.getString("RegistrationNr");
        String department = rs.getString("Department");

        // User-specific fields
        String userName = rs.getString("UserName");
        String passWord = rs.getString("Password");
        String firstName = rs.getString("FirstName");
        String lastName = rs.getString("LastName");
        String email = rs.getString("Email");
        String phoneNr = rs.getString("PhoneNr");
        
        //TODO create address object and pass its name as  a string
        String address = ""; 
        AccountPrivileges accountPrivileges = AccountPrivileges.valueOf(rs.getString("AccountPrivileges"));

        // Create and return the Employee object
        Employee employee = new Employee(userName, passWord, firstName, lastName, email, phoneNr, address, 
                                          accountPrivileges, employeeID, cpr, securityClearance, 
                                          certification, accountNr, registrationNr, department);
 
        return employee;
    }
    

	
	
	public int findEmployeeIDFromUserID(int userID) {
		int employeeID = 0;
		try {
			findEmployeeIDByUserID = connection.prepareStatement(find_employee_id_by_user_id);
			ResultSet rs = dbConecction.getResultSetWithPS(findEmployeeIDByUserID);
			
			if(rs.next()) {
				employeeID = rs.getInt("Employee_ID");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return employeeID;
	}

}
