package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.AccountPrivileges;
import model.User;

/**
 * The UserDB class handles database operations related to {@link User} entities.
 * 
 * <p>This class provides methods to retrieve user data from the database, 
 * focusing primarily on customer or guest type users. It uses a 
 * {@link DBConnection} singleton instance to manage database connectivity 
 * and transactions.</p>
 */
public class UserDB implements UserDBIF {

    private DBConnection dbConnection = DBConnection.getInstance();
    private Connection connection = DBConnection.getInstance().getConnection();

    // SQL query for retrieving user information by user ID and ensuring the user is either a customer or guest.
    private static final String find_customer_info_by_user_id =
        "SELECT * FROM [User] WHERE ID = ? AND AccountPrivileges IN ('CUSTOMER', 'GUEST');";

    private PreparedStatement findCustomerInfoByUserId;

    /**
     * Finds a User object (specifically a customer or guest type) based on the given user ID.
     *
     * <p>This method prepares and executes a SELECT query to find a user record that matches 
     * the provided user ID and is of type CUSTOMER or GUEST. If a matching record is found, 
     * it creates a User object from the ResultSet. If not found, it returns null.</p>
     *
     * @param userID the ID of the user to find.
     * @return a {@link User} object if a matching user is found; otherwise null.
     */
    @Override
    public User findCustomerByUserID(int userID) {
        User user = null;
        try {
            findCustomerInfoByUserId = connection.prepareStatement(find_customer_info_by_user_id);
            findCustomerInfoByUserId.setInt(1, userID);
            ResultSet rs = dbConnection.getResultSetWithPS(findCustomerInfoByUserId);

            if (rs.next()) {
                user = createUserFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Creates and returns a User object based on the current row of the provided ResultSet.
     *
     * <p>This method extracts user-related fields (e.g., userName, password, firstName, 
     * lastName, email, phoneNr) from the ResultSet, as well as the accountPrivileges. 
     * The address field is currently not retrieved from the database and is set to an 
     * empty string. After the object is created, the userID is assigned to the User object.</p>
     *
     * @param rs the ResultSet positioned at a valid user record.
     * @return a fully populated {@link User} object based on the ResultSet data.
     * @throws SQLException if a database access error occurs or if the ResultSet is invalid.
     */
    @Override
    public User createUserFromResultSet(ResultSet rs) throws SQLException {
        // Extract fields from ResultSet
        int userID = rs.getInt("ID");
        String userName = rs.getString("UserName");
        String passWord = rs.getString("Password");
        String firstName = rs.getString("FirstName");
        String lastName = rs.getString("LastName");
        String email = rs.getString("Email");
        String phoneNr = rs.getString("PhoneNr");
        String address = ""; // Placeholder, as address is not retrieved from the database
        AccountPrivileges accountPrivileges = AccountPrivileges.valueOf(rs.getString("AccountPrivileges"));

        // Create the User object
        User user = new User(userName, passWord, firstName, lastName, email, phoneNr, address, accountPrivileges);
        user.setUserID(userID); // Assign the user ID after creation

        return user;
    }

}
