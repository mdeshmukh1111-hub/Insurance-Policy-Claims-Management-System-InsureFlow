package com.insureflow.service.impl;

import com.insureflow.dto.PolicyRequestDTO;
import com.insureflow.entity.Agent;
import com.insureflow.entity.Customer;
import com.insureflow.entity.Policy;
import com.insureflow.entity.PolicyType;
import com.insureflow.exception.BadRequestException;
import com.insureflow.exception.ResourceNotFoundException;
import com.insureflow.repository.AgentRepository;
import com.insureflow.repository.CustomerRepository;
import com.insureflow.repository.PolicyRepository;
import com.insureflow.repository.PolicyTypeRepository;
import com.insureflow.service.PolicyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository policyRepository;
    private final CustomerRepository customerRepository;
    private final AgentRepository agentRepository;
    private final PolicyTypeRepository policyTypeRepository;

    public PolicyServiceImpl(PolicyRepository policyRepository, CustomerRepository customerRepository, AgentRepository agentRepository, PolicyTypeRepository policyTypeRepository) {
        this.policyRepository = policyRepository;
        this.customerRepository = customerRepository;
        this.agentRepository = agentRepository;
        this.policyTypeRepository = policyTypeRepository;
    }

    @Override
    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    @Override
    public Policy getPolicyById(Long id) {
        return policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with ID: " + id));
    }

    @Override
    public Policy getPolicyByNumber(String policyNumber) {
        return policyRepository.findByPolicyNumber(policyNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with number: " + policyNumber));
    }

    @Override
    public Policy createPolicy(PolicyRequestDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + dto.getCustomerId()));

        PolicyType policyType = policyTypeRepository.findById(dto.getPolicyTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Policy Type not found with ID: " + dto.getPolicyTypeId()));

        Agent agent = null;
        if (dto.getAgentId() != null) {
            agent = agentRepository.findById(dto.getAgentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agent not found with ID: " + dto.getAgentId()));
        }

        if (dto.getCoverageAmount() < policyType.getMinCoverage() || dto.getCoverageAmount() > policyType.getMaxCoverage()) {
            throw new BadRequestException("Coverage amount must be between ₹" + policyType.getMinCoverage() + " and ₹" + policyType.getMaxCoverage());
        }

        String policyNumber = "POL-" + (10000 + new Random().nextInt(90000));
        LocalDate startDate = dto.getStartDate() != null ? dto.getStartDate() : LocalDate.now();
        int termMonths = dto.getTermMonths() != null ? dto.getTermMonths() : policyType.getDefaultTermMonths();
        LocalDate expiryDate = startDate.plusMonths(termMonths);

        Policy policy = new Policy(
                policyNumber,
                customer,
                agent,
                policyType,
                dto.getCoverageAmount(),
                dto.getPremiumAmount(),
                dto.getPaymentFrequency() != null ? dto.getPaymentFrequency() : "ANNUALLY",
                startDate,
                expiryDate,
                "PENDING"
        );

        return policyRepository.save(policy);
    }

    @Override
    public Policy updatePolicyStatus(Long id, String status) {
        Policy policy = getPolicyById(id);
        policy.setStatus(status.toUpperCase());
        return policyRepository.save(policy);
    }

    @Override
    public List<Policy> getPoliciesByCustomerId(Long customerId) {
        return policyRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Policy> getPoliciesByAgentId(Long agentId) {
        return policyRepository.findByAgentId(agentId);
    }

    @Override
    public List<Policy> searchPolicies(String keyword, String status, Long categoryId) {
        return policyRepository.searchPolicies(keyword, status, categoryId);
    }
}
