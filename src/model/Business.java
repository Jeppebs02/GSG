package model;

public class Business extends User {
    private String cvr;
    private String companyName;

    // Constructor
    public Business(String userName, String passWord, String firstName, String lastName, String email, String phoneNr, String address, AccountPrivileges accountPrivileges,
                    String cvr, String companyName) {
        super(userName, passWord, firstName, lastName, email, phoneNr, address, accountPrivileges);
        this.cvr = cvr;
        this.companyName = companyName;
    }

    // Getters and Setters
    public String getCvr() {
        return cvr;
    }

    public void setCvr(String cvr) {
        this.cvr = cvr;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "Business{" +
                "cvr='" + cvr + '\'' +
                ", companyName='" + companyName + '\'' +
                "} " + super.toString();
    }
}
