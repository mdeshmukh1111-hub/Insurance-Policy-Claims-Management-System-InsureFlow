package com.insureflow.service.impl;

import com.insureflow.entity.PolicyType;
import com.insureflow.exception.ResourceNotFoundException;
import com.insureflow.repository.PolicyTypeRepository;
import com.insureflow.service.PolicyTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional
public class PolicyTypeServiceImpl implements PolicyTypeService {

    private final PolicyTypeRepository policyTypeRepository;

    public PolicyTypeServiceImpl(PolicyTypeRepository policyTypeRepository) {
        this.policyTypeRepository = policyTypeRepository;
    }

    @Override
    public List<PolicyType> getAllPolicyTypes() {
        return policyTypeRepository.findAll();
    }

    @Override
    public PolicyType getPolicyTypeById(Long id) {
        return policyTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy Type not found with ID: " + id));
    }

    @Override
    public PolicyType createPolicyType(PolicyType policyType) {
        if (policyType.getTypeCode() == null || policyType.getTypeCode().trim().isEmpty()) {
            policyType.setTypeCode(policyType.getCategory().substring(0, 3).toUpperCase() + "-" + (100 + new Random().nextInt(900)));
        }
        return policyTypeRepository.save(policyType);
    }

    @Override
    public PolicyType updatePolicyType(Long id, PolicyType policyTypeDetails) {
        PolicyType existing = getPolicyTypeById(id);
        existing.setName(policyTypeDetails.getName());
        existing.setDescription(policyTypeDetails.getDescription());
        existing.setCategory(policyTypeDetails.getCategory());
        existing.setBasePremium(policyTypeDetails.getBasePremium());
        existing.setMinCoverage(policyTypeDetails.getMinCoverage());
        existing.setMaxCoverage(policyTypeDetails.getMaxCoverage());
        existing.setDefaultTermMonths(policyTypeDetails.getDefaultTermMonths());
        if (policyTypeDetails.getActive() != null) {
            existing.setActive(policyTypeDetails.getActive());
        }
        return policyTypeRepository.save(existing);
    }

    @Override
    public void deletePolicyType(Long id) {
        PolicyType existing = getPolicyTypeById(id);
        policyTypeRepository.delete(existing);
    }

    @Override
    public List<PolicyType> getPolicyTypesByCategory(String category) {
        return policyTypeRepository.findByCategory(category);
    }
}
