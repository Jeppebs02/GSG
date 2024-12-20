package dal;

import model.AccountPrivileges;
import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The {@code EmployeeDB} class provides database operations related to
 * employees, including finding employees by user ID and creating
 * {@link Employee} objects from database query results.
 * 
 * <p>
 * This class interacts with a SQL Server database and relies on the
 * {@link DBConnection} class for managing the database connection and
 * transactions.
 * </p>
 */
public class EmployeeDB implements EmployeeDBIF {

	private DBConnection dbConnection = DBConnection.getInstance();
	private Connection connection = DBConnection.getInstance().getConnection();

	// SQL Queries
	private static final String find_employee_id_by_user_id = "SELECT User_ID, Employee_ID FROM EmployeeUser WHERE User_ID = ?;";
	private static final String find_employee_info_from_user_id_and_employee_id = "SELECT Employee.ID AS EmployeeID, Employee.CPR, Employee.SecurityClearance, Employee.AccountNr, "
			+ "Employee.Certification, Employee.RegistrationNr, Employee.Department, U.ID AS UserID, U.UserName, "
			+ "U.Password, U.FirstName, U.LastName, U.Email, U.PhoneNr, U.Type, U.Address_ID, U.AccountPrivileges "
			+ "FROM Employee CROSS JOIN [User] AS U " + "WHERE Employee.ID = ? AND U.ID = ?;";

	private PreparedStatement findEmployeeIDByUserID;
	private PreparedStatement findEmployeeInfoFromUserIDAndEmployeeID;

	/**
	 * Constructs a new {@code EmployeeDB} object and prepares SQL statements for
	 * finding employee information from the database.
	 * 
	 * @throws SQLException if a database access error occurs or if the prepared
	 *                      statements cannot be created.
	 */
	public EmployeeDB() throws SQLException {
		

		findEmployeeIDByUserID = connection.prepareStatement(find_employee_id_by_user_id);
		findEmployeeInfoFromUserIDAndEmployeeID = connection
				.prepareStatement(find_employee_info_from_user_id_and_employee_id);
	}

	/**
	 * Finds an {@link Employee} object based on the given user ID.
	 * 
	 * <p>
	 * This method first retrieves the associated employee ID from the user ID, then
	 * uses that employee ID along with the user ID to query all relevant employee
	 * and user details from the database.
	 * </p>
	 * 
	 * @param userID the user ID to find the associated Employee for.
	 * @return an Employee object if found; otherwise null.
	 * @throws Exception if a database or other error occurs during retrieval.
	 */
	@Override
	public Employee findEmployeeByUserID(int userID) throws Exception {
		Employee employee = null;
		int employeeId;

		try {
			employeeId = findEmployeeIDFromUserID(userID);
			System.out.println("EmployeeID: " + employeeId);
			System.out.println("UserID: " + userID);

			findEmployeeInfoFromUserIDAndEmployeeID.setInt(1, employeeId);
			findEmployeeInfoFromUserIDAndEmployeeID.setInt(2, userID);

			ResultSet rs = dbConnection.getResultSetWithPS(findEmployeeInfoFromUserIDAndEmployeeID);
			if (rs.next()) {
				employee = createEmployeeFromResultSet(rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return employee;
	}

	/**
	 * Creates an {@link Employee} object from the given ResultSet.
	 * 
	 * <p>
	 * The ResultSet should contain the columns needed to construct both the
	 * employee-specific data (e.g., CPR, SecurityClearance, etc.) and the
	 * user-specific data (e.g., UserName, Email, PhoneNr, etc.).
	 * </p>
	 * 
	 * @param rs the ResultSet containing all required fields for constructing an
	 *           Employee.
	 * @return an Employee object populated with data from the ResultSet.
	 * @throws SQLException if a database access error occurs or if the ResultSet is
	 *                      invalid.
	 */
	@Override
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

		// For simplicity, we don't handle address retrieval in this code snippet.
		String address = "";
		AccountPrivileges accountPrivileges = AccountPrivileges.valueOf(rs.getString("AccountPrivileges"));

		// Create and return the Employee object
		return new Employee(userName, passWord, firstName, lastName, email, phoneNr, address, accountPrivileges,
				employeeID, cpr, securityClearance, certification, accountNr, registrationNr, department);
	}

	/**
	 * Finds the employee ID associated with a given user ID.
	 * 
	 * <p>
	 * This method queries the database for a record in EmployeeUser that matches
	 * the specified user ID and returns the corresponding employee ID.
	 * </p>
	 * 
	 * @param userID the user ID whose associated employee ID is to be found.
	 * @return the employee ID if found; otherwise 0.
	 */
	@Override
	public int findEmployeeIDFromUserID(int userID) {
		int employeeID = 0;
		try {
			findEmployeeIDByUserID.setInt(1, userID);
			ResultSet rs = dbConnection.getResultSetWithPS(findEmployeeIDByUserID);

			if (rs.next()) {
				System.out.println("Result set is not empty");
				employeeID = rs.getInt("Employee_ID");
				System.out.println(employeeID);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return employeeID;
	}

}
