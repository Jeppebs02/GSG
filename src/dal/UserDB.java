package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import model.AccountPrivileges;
import model.User;

public class UserDB implements UserDBIF	{
	
	private DBConnection dbConnection = DBConnection.getInstance();
	private Connection connection = DBConnection.getInstance().getConnection();
	
	private static final String find_customer_info_by_user_id =
			"SELECT * FROM [User] WHERE ID = ? AND Type IN ('CUSTOMER', 'GUEST');";
	
	PreparedStatement findCustomerInfoByUserId;

	/**
     * Creates and returns a User object based on a given userID.
     *
     * @return User
     */
	
	@Override
	public User findCustomerByUserID(int userID) {
		User user = null;
		try {
			findCustomerInfoByUserId = connection.prepareStatement(find_customer_info_by_user_id);
			findCustomerInfoByUserId.setInt(1, userID);
			ResultSet rs = dbConnection.getResultSetWithPS(findCustomerInfoByUserId);
			
			if(rs.next()) {
				user = createUserFromResultSet(rs);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	
	
	//TODO implement address get functionality. 
	
	/**
     * Creates and returns a User object based on information in a ResultSet.
     *
     * @return User
     */
	
	public User createUserFromResultSet(ResultSet rs) throws SQLException {
		
		// Get fields from ResultSet
        int userID = rs.getInt("ID"); // User ID
        String userName = rs.getString("UserName");
        String passWord = rs.getString("Password");
        String firstName = rs.getString("FirstName");
        String lastName = rs.getString("LastName");
        String email = rs.getString("Email");
        String phoneNr = rs.getString("PhoneNr");
        String address = ""; // Placeholder, `Address` is not in result set
        AccountPrivileges accountPrivileges = AccountPrivileges.valueOf(rs.getString("AccountPrivileges"));

        // Create and return the User object
        User user = new User(userName, passWord, firstName, lastName, email, phoneNr, address, accountPrivileges);
        user.setUserID(userID); // Set the user ID after creation of object
        return user;
    }
		
	

}
