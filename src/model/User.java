package model;

public class User {
	private String userName;
    private String passWord;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNr;
    private String address;
    private AccountPrivileges accountPrivileges;

    // Constructor
    public User(String userName, String passWord, String firstName, String lastName, String email, String phoneNr, String address, AccountPrivileges accountPrivileges) {
        this.userName = userName;
        this.passWord = passWord;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNr = phoneNr;
        this.address = address;
        this.accountPrivileges = accountPrivileges;
    }

    // Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AccountPrivileges getAccountPrivileges() {
	return accountPrivileges;
    }

    public void setAccountPrivileges(AccountPrivileges accountPrivileges) {
	this.accountPrivileges = accountPrivileges;
    }

}
