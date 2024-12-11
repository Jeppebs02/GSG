package model;

public class Rating {
    private int ratingID;
    private int securityScore;
    private String securityComment;
    private int serviceScore;
    private String serviceComment;
    private Employee employee;

    public Rating(int securityScore, String securityComment, int serviceScore, String serviceComment, Employee employee) {
        if (securityScore < 1 || securityScore > 5) {
            throw new IllegalArgumentException("Security score must be between 1 and 5.");
        }
        if (serviceScore < 1 || serviceScore > 5) {
            throw new IllegalArgumentException("Service score must be between 1 and 5.");
        }
        this.securityScore = securityScore;
        this.securityComment = securityComment;
        this.serviceScore = serviceScore;
        this.serviceComment = serviceComment;
        this.employee = employee;
    }

    // Getters and Setters
    public int getSecurityScore() {
        return securityScore;
    }

    public void setSecurityScore(int securityScore) {
        if (securityScore < 1 || securityScore > 5) {
            throw new IllegalArgumentException("Security score must be between 1 and 5.");
        }
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
        if (serviceScore < 1 || serviceScore > 5) {
            throw new IllegalArgumentException("Service score must be between 1 and 5.");
        }
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


