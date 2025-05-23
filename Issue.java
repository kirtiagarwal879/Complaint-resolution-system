package model;
/**
 * Issue class 
 */


public class Issue {
    public final String issueId;
    public final String transactionId;
    public final String issueType;
    public final String subject;
    public final String description;
    public final String email;
    public IssueStatus status;
    public String resolution;
    public Agent assignedAgent;

    public Issue(String issueId, String transactionId, String issueType, String subject, String description, String email) {
        this.issueId = issueId;
        this.transactionId = transactionId;
        this.issueType = issueType;
        this.subject = subject;
        this.description = description;
        this.email = email;
        this.status = IssueStatus.OPEN;
        this.resolution = "";
        this.assignedAgent = null;
    }

    public String toString() {
        return issueId + " {\"" + transactionId + "\", \"" + issueType + "\", \"" + subject + "\", \"" + description + "\", \"" + email + "\", \"" + status + "\"}";
    }
}