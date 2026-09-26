package com.insureflow.service.impl;

import com.insureflow.dto.RenewalRequestDTO;
import com.insureflow.entity.Agent;
import com.insureflow.entity.Policy;
import com.insureflow.entity.Renewal;
import com.insureflow.exception.ResourceNotFoundException;
import com.insureflow.repository.AgentRepository;
import com.insureflow.repository.PolicyRepository;
import com.insureflow.repository.RenewalRepository;
import com.insureflow.service.RenewalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class RenewalServiceImpl implements RenewalService {

    private final RenewalRepository renewalRepository;
    private final PolicyRepository policyRepository;
    private final AgentRepository agentRepository;

    public RenewalServiceImpl(RenewalRepository renewalRepository, PolicyRepository policyRepository, AgentRepository agentRepository) {
        this.renewalRepository = renewalRepository;
        this.policyRepository = policyRepository;
        this.agentRepository = agentRepository;
    }

    @Override
    public List<Renewal> getAllRenewals() {
        return renewalRepository.findAll();
    }

    @Override
    public Renewal getRenewalById(Long id) {
        return renewalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Renewal record not found with ID: " + id));
    }

    @Override
    public Renewal processRenewal(RenewalRequestDTO dto) {
        Policy policy = policyRepository.findById(dto.getPolicyId())
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with ID: " + dto.getPolicyId()));

        Agent agent = null;
        if (dto.getAgentId() != null) {
            agent = agentRepository.findById(dto.getAgentId()).orElse(null);
        }

        LocalDate previousExpiry = policy.getExpiryDate();
        int termMonths = dto.getTermMonths() != null ? dto.getTermMonths() : 12;
        
        // If policy is already expired, extend from today, else extend from previous expiry
        LocalDate baseDate = previousExpiry.isBefore(LocalDate.now()) ? LocalDate.now() : previousExpiry;
        LocalDate newExpiry = baseDate.plusMonths(termMonths);

        String renewalNumber = "RNW-" + (100 + new Random().nextInt(900));

        Renewal renewal = new Renewal(
                renewalNumber,
                policy,
                LocalDate.now(),
                previousExpiry,
                newExpiry,
                policy.getPremiumAmount(),
                "COMPLETED",
                agent
        );

        Renewal savedRenewal = renewalRepository.save(renewal);

        // Update Policy Expiry Date and set status to ACTIVE
        policy.setExpiryDate(newExpiry);
        policy.setStatus("ACTIVE");
        policyRepository.save(policy);

        return savedRenewal;
    }

    @Override
    public List<Renewal> getRenewalsByPolicyId(Long policyId) {
        return renewalRepository.findByPolicyId(policyId);
    }

    @Override
    public List<Renewal> getRenewalsByCustomerId(Long customerId) {
        return renewalRepository.findByPolicyCustomerId(customerId);
    }
}
