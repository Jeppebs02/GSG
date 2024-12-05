package dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Employee;
import model.User;

public interface UserDBIF {
	
	public User findCustomerByUserID(int userID) throws Exception ;	
	
	User createUserFromResultSet(ResultSet rs) throws SQLException;

}
