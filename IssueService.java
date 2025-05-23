
package service;

import model.*;
import java.util.*;

public class IssueService {
    private final Map<String, Issue> issues = new HashMap<>();
    private int issueCounter = 1;
    private final AgentService agentService;

    public IssueService(AgentService agentService) {
        this.agentService = agentService;
    }

    public void createIssue(String transactionId, String issueType, String subject, String description, String email) {
        String issueId = "I" + issueCounter++;
        Issue issue = new Issue(issueId, transactionId, issueType, subject, description, email);
        issues.put(issueId, issue);
        System.out.println(">>> Issue " + issueId + " created against transaction \"" + transactionId + "\"");
    }

    public void assignIssue(String issueId) {
        Issue issue = issues.get(issueId);
        if (issue == null || issue.assignedAgent != null) return;
        Agent agent = agentService.getAvailableAgent(issue.issueType);
        if (agent != null) {
            agent.assignIssue(issue);
            if (agent.currentIssue == issue)
                System.out.println(">>> Issue " + issueId + " assigned to agent " + agent.agentId);
            else
                System.out.println(">>> Issue " + issueId + " added to waitlist of Agent " + agent.agentId);
        } else {

            System.out.println(">>> No agent available for issue " + issueId);
        }
    }

    public List<Issue> getIssues(Map<String, String> filter) {
        List<Issue> result = new ArrayList<>();
        for (Issue issue : issues.values()) {
            boolean match = true;
            if (filter.containsKey("email") && !issue.email.equals(filter.get("email"))) match = false;
            if (filter.containsKey("type") && !issue.issueType.equals(filter.get("type"))) match = false;
            if (match) result.add(issue);
        }
        return result;
    }

    public void updateIssue(String issueId, IssueStatus status, String resolution) {
        Issue issue = issues.get(issueId);
        if (issue != null) {
            issue.status = status;
            issue.resolution = resolution;
            System.out.println(">>> " + issueId + " status updated to " + status);
        }
    }

public void resolveIssue(String issueId, String resolution) {
        Issue issue = issues.get(issueId);
        if (issue != null && issue.assignedAgent != null) {
            issue.assignedAgent.resolveCurrentIssue(resolution);
            System.out.println(">>> " + issueId + " issue marked resolved");
        }
    }

    public void viewAgentsWorkHistory() {
        for (Map.Entry<String, Agent> entry : agentService.getAllAgents().entrySet()) {
            Agent agent = entry.getValue();
            System.out.print(agent.agentId + " -> {");
            for (int i = 0; i < agent.resolvedIssues.size(); i++) {
                System.out.print(agent.resolvedIssues.get(i).issueId);
                if (i < agent.resolvedIssues.size() - 1) System.out.print(", ");
            }
            System.out.println("}");
        }
    }
}
