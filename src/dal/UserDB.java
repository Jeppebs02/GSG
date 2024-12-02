package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.User;

public class UserDB implements UserDBIF	{
	
	private DBConnection dbConecction = DBConnection.getInstance();
	private Connection connection = DBConnection.getInstance().getConnection();
	
	private static final String find_customer_info_by_user_id =
			"SELECT * FROM [User] WHERE ID = 1 AND Type IN ('CUSTOMER', 'GUEST');";
	
	PreparedStatement findCustomerIdByUserId;

	@Override
	public User findCustomerByUserID(int userID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	

	public int findCustomerIDFromUserID(int userID) {
		int employeeID = 0;
		try {
			findEmployeeIDByUserID = connection.prepareStatement(find_employee_id_by_user_id);
			findEmployeeIDByUserID.setInt(1, userID);
			ResultSet rs = dbConecction.getResultSetWithPS(findEmployeeIDByUserID);
			
			if(rs.next()) {
				System.out.println("Result set is not empty");
				
				employeeID = rs.getInt("Employee_ID");
				System.out.println(employeeID);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return employeeID;
	}

}
