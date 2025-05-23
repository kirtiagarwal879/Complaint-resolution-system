
package model;
/**
 * Writing this class to check the agent , which issue he is working is the issue resolved or how many issues he have in the queue
 * Every agent have an id, name , issue based on his expertise
 * So, that agent can work on single issue , if other issue comes based on his experties then it will be add in his queue ,work is shared properly and issues go to the right agents.
 */


import java.util.*;

public class Agent {
    public final String agentId;
    public final String name;
    public final List<String> expertise;
    private final Queue<Issue> waitingQueue = new LinkedList<>();
    public Issue currentIssue;
    public final List<Issue> resolvedIssues = new ArrayList<>();

public Agent(String agentId, String name, List<String> expertise) {
        this.agentId = agentId;
        this.name = name;
        this.expertise = expertise;
    }

    public boolean isAvailable() {
        return currentIssue == null;
    }

public void assignIssue(Issue issue) {
        if (isAvailable()) {
            currentIssue = issue;
            issue.assignedAgent = this;
        } else {
            waitingQueue.offer(issue);
        }
    }

    public void resolveCurrentIssue(String resolution) {
        if (currentIssue != null) {
            currentIssue.status = IssueStatus.RESOLVED;
            currentIssue.resolution = resolution;
            resolvedIssues.add(currentIssue);
            currentIssue = null;
            if (!waitingQueue.isEmpty()) {
                assignIssue(waitingQueue.poll());
            }
        }
    }
}
