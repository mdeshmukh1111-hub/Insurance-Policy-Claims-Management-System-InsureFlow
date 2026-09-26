package com.insureflow.service;

import com.insureflow.entity.Agent;
import java.util.List;

public interface AgentService {
    List<Agent> getAllAgents();
    Agent getAgentById(Long id);
    Agent createAgent(Agent agent);
    Agent updateAgent(Long id, Agent agentDetails);
    void deleteAgent(Long id);
    List<Agent> searchAgents(String keyword);
}
