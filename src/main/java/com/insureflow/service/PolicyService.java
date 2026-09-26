package com.insureflow.service;

import com.insureflow.dto.PolicyRequestDTO;
import com.insureflow.entity.Policy;

import java.util.List;

public interface PolicyService {
    List<Policy> getAllPolicies();
    Policy getPolicyById(Long id);
    Policy getPolicyByNumber(String policyNumber);
    Policy createPolicy(PolicyRequestDTO requestDTO);
    Policy updatePolicyStatus(Long id, String status);
    List<Policy> getPoliciesByCustomerId(Long customerId);
    List<Policy> getPoliciesByAgentId(Long agentId);
    List<Policy> searchPolicies(String keyword, String status, Long categoryId);
}
