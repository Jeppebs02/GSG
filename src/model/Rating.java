package model;

public class Rating {
	private int ratingID;
    private int securityScore;
    private String securityComment;
    private int serviceScore;
    private String serviceComment;
    private Employee employee;
    
    public Rating(int securityScore, String securityComment, int serviceScore, String serviceComment, Employee empolyee) {
    	this.employee = empolyee;
    }

    // Getters and Setters
    public int getSecurityScore() {
        return securityScore;
    }

    public void setSecurityScore(int securityScore) {
        this.securityScore = securityScore;
    }

    public String getSecurityComment() {
        return securityComment;
    }

    public void setSecurityComment(String securityComment) {
        this.securityComment = securityComment;
    }

    public int getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(int serviceScore) {
        this.serviceScore = serviceScore;
    }

    public String getServiceComment() {
        return serviceComment;
    }

    public void setServiceComment(String serviceComment) {
        this.serviceComment = serviceComment;
    }

	
	public Employee getEmployee() {
		return employee;
	}

	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public int getRatingID() {
		return ratingID;
	}

	public void setRatingID(int ratingID) {
		this.ratingID = ratingID;
	}

}

