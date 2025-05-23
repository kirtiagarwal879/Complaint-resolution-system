import service.*;
import model.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        AgentService agentService = new AgentService();
        IssueService issueService = new IssueService(agentService);

        issueService.createIssue("T1", "Payment Related", "Payment Failed", "My payment failed but money is debited", "testUser1@test.com");
        issueService.createIssue("T2", "Mutual Fund Related", "Purchase Failed", "Unable to purchase Mutual Fund", "testUser2@test.com");
        issueService.createIssue("T3", "Payment Related", "Payment Failed", "My payment failed but money is debited", "testUser2@test.com");

        agentService.addAgent("agent1@test.com", "Agent 1", Arrays.asList("Payment Related", "Gold Related"));
        agentService.addAgent("agent2@test.com", "Agent 2", Arrays.asList("Mutual Fund Related"));

        issueService.assignIssue("I1");
        issueService.assignIssue("I2");
        issueService.assignIssue("I3");

        System.out.println("\nGet by email:");
        Map<String, String> filter = new HashMap<>();
        filter.put("email", "testUser2@test.com");
        for (Issue issue : issueService.getIssues(filter)) {
                System.out.println(issue);
        }

        System.out.println("\nGet by type:");
        Map<String, String> typeFilter = new HashMap<>();
        typeFilter.put("type", "Payment Related");
        for (Issue issue : issueService.getIssues(typeFilter)) {
            System.out.println(issue);
        }


        issueService.updateIssue("I3", IssueStatus.IN_PROGRESS, "Waiting for payment confirmation");
        issueService.resolveIssue("I2", "PaymentFailed debited amount will get reversed");
        issueService.resolveIssue("I1", "PaymentFailed debited amount will get reversed");
        issueService.resolveIssue("I3", "PaymentFailed debited amount will get reversed");

        System.out.println("\nWork History:");
        issueService.viewAgentsWorkHistory();
    }
}