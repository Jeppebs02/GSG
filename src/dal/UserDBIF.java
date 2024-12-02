package dal;

import model.Employee;
import model.User;

public interface UserDBIF {
	
	public User findCustomerByUserID(int userID) throws Exception ;	

}
