package model;

public class Rating {
    private int securityScore;
    private String securityComment;
    private int serviceScore;
    private String serviceComment;
    private Report report; // Association with Report (1..*)
    private Employee employee;
    
    public Rating(Report report, Employee empolyee) {
    	this.employee = empolyee;
    	this.report = report;
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

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}

