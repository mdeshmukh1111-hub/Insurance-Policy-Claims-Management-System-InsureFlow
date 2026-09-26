package com.insureflow.service.impl;

import com.insureflow.entity.Agent;
import com.insureflow.exception.BadRequestException;
import com.insureflow.exception.ResourceNotFoundException;
import com.insureflow.repository.AgentRepository;
import com.insureflow.service.AgentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    public AgentServiceImpl(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    @Override
    public Agent getAgentById(Long id) {
        return agentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found with ID: " + id));
    }

    @Override
    public Agent createAgent(Agent agent) {
        if (agentRepository.existsByEmail(agent.getEmail())) {
            throw new BadRequestException("Agent with email '" + agent.getEmail() + "' already exists!");
        }

        if (agent.getAgentCode() == null || agent.getAgentCode().trim().isEmpty()) {
            agent.setAgentCode("AGT-" + (2000 + new Random().nextInt(8000)));
        }

        return agentRepository.save(agent);
    }

    @Override
    public Agent updateAgent(Long id, Agent agentDetails) {
        Agent existing = getAgentById(id);

        if (!existing.getEmail().equalsIgnoreCase(agentDetails.getEmail()) &&
                agentRepository.existsByEmail(agentDetails.getEmail())) {
            throw new BadRequestException("Agent with email '" + agentDetails.getEmail() + "' already exists!");
        }

        existing.setFirstName(agentDetails.getFirstName());
        existing.setLastName(agentDetails.getLastName());
        existing.setEmail(agentDetails.getEmail());
        existing.setPhone(agentDetails.getPhone());
        existing.setAgencyName(agentDetails.getAgencyName());
        if (agentDetails.getStatus() != null) {
            existing.setStatus(agentDetails.getStatus());
        }

        return agentRepository.save(existing);
    }

    @Override
    public void deleteAgent(Long id) {
        Agent existing = getAgentById(id);
        agentRepository.delete(existing);
    }

    @Override
    public List<Agent> searchAgents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return agentRepository.findAll();
        }
        return agentRepository.searchAgents(keyword.trim());
    }
}
