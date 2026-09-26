package com.insureflow.service;

import com.insureflow.entity.PolicyType;
import java.util.List;

public interface PolicyTypeService {
    List<PolicyType> getAllPolicyTypes();
    PolicyType getPolicyTypeById(Long id);
    PolicyType createPolicyType(PolicyType policyType);
    PolicyType updatePolicyType(Long id, PolicyType policyTypeDetails);
    void deletePolicyType(Long id);
    List<PolicyType> getPolicyTypesByCategory(String category);
}
