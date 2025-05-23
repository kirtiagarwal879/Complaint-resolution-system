package service;

/**
 *We have to maintain the agents based on their expertises so we have to maintain based on some identifiers whcih we have to create as 
 email and id 
and now we have to check is our agent is available based on the particular issue then we will return the agent 
 */


import java.util.*;
import model.Agent;

public class AgentService {
    private final Map<String, Agent> agents = new HashMap<>();
    private int agentCounter = 1;

    public void addAgent(String email, String name, List<String> expertise) {
        String agentId = "A" + agentCounter++;
        Agent agent = new Agent(agentId, name, expertise);
        agents.put(email, agent);
        System.out.println(">>> Agent " + agentId + " created");
    }

    public Agent getAvailableAgent(String issueType) {
        for (Agent agent : agents.values()) {
            if (agent.expertise.contains(issueType)) {
                return agent;
            }
        }
        return null;
    }

    public Agent getAgentByEmail(String email) {
        return agents.get(email);
    }

    public Map<String, Agent> getAllAgents() {
        return agents;
    }
}