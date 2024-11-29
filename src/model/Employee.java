package model;

import java.util.ArrayList;
import java.util.List;

public class Employee extends User {
    private int employeeID;
	private String cpr;
    private String securityClearance;
    private String certification;
    private String accountNr;
    private String registrationNr;
    private String department;
    private List<Availability> unavailabilities;

    // Constructor
    public Employee(String userName, String passWord, String firstName, String lastName, String email, String phoneNr, String address, AccountPrivileges accountPrivileges, int employeeID, String cpr, String securityClearance, String certification, String accountNr, String registrationNr, String department) {
    	super(userName, passWord, firstName, lastName, email, phoneNr, address, accountPrivileges);
    	this.employeeID = employeeID;
    	this.cpr = cpr;
    	this.securityClearance = securityClearance;
    	this.certification = certification;
    	this.accountNr = accountNr;
    	this.registrationNr = registrationNr;
    	this.department = department;
    	unavailabilities = new ArrayList<>();
}

    public Employee(String userName, String passWord, String firstName, String lastName, String email, String phoneNr, String address,
            AccountPrivileges accountPrivileges, String cpr, String securityClearance, String certification,
            String accountNr, String registrationNr, String department) {
    		this(userName, passWord, firstName, lastName, email, phoneNr, address, accountPrivileges, 10, cpr, securityClearance, certification, accountNr, registrationNr, department);
    }
   
    // Getters and Setters
    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getSecurityClearance() {
        return securityClearance;
    }

    public void setSecurityClearance(String securityClearance) {
        this.securityClearance = securityClearance;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getAccountNr() {
        return accountNr;
    }

    public void setAccountNr(String accountNr) {
        this.accountNr = accountNr;
    }

    public String getRegistrationNr() {
        return registrationNr;
    }

    public void setRegistrationNr(String registrationNr) {
        this.registrationNr = registrationNr;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public List<Availability> getUnavailabilities() {
		return new ArrayList<>(unavailabilities);
	}
}
