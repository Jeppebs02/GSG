package ctrl;

import dal.EmployeeDB;
import dal.UserDB;
import model.Employee;
import model.User;

/**
 * The UserCtrl class manages user-related operations, including retrieving customer 
 * and employee information from the database by user ID.
 */
public class UserCtrl {

    /**
     * Finds a {@link User} object that is either a customer or a guest based on the given user ID.
     * 
     * <p>This method uses the {@link UserDB} class to retrieve the user data from the database. 
     * If a matching user is found, it returns the User object; otherwise, it returns null.</p>
     *
     * @param userID the unique identifier of the user to find.
     * @return a User object if found; null otherwise.
     */
    public User findCustomerByUserID(int userID) {
        UserDB udb = new UserDB();
        User u = udb.findCustomerByUserID(userID);
        return u;
    }

    /**
     * Finds an {@link Employee} object based on the given user ID.
     * 
     * <p>This method uses the {@link EmployeeDB} class to retrieve and create an Employee object 
     * that is associated with the given user ID.</p>
     * 
     * @param userID the unique identifier of the user whose associated employee is to be found.
     * @return an Employee object if found; otherwise null.
     * @throws Exception if a database access error or other issue occurs during the retrieval.
     */
    public Employee findEmployeeByUserID(int userID) throws Exception {
        EmployeeDB empDB = new EmployeeDB();
        Employee e = empDB.findEmployeeByUserID(userID);
        return e;
    }

}
