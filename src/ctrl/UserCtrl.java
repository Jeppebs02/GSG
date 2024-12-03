package ctrl;

import dal.EmployeeDB;
import dal.UserDB;
import model.Employee;
import model.User;

public class UserCtrl {

	public User findCustomerByUserID(int userID) {
		
		UserDB udb = new UserDB();
		User u = udb.findCustomerByUserID(userID);
		return u;
	}

	public  Employee findEmployeeByUserID(int userID) throws Exception {
		
		EmployeeDB empDB = new EmployeeDB();
		Employee e = empDB.findEmployeeByUserID(userID);
		return e;
	}

}
