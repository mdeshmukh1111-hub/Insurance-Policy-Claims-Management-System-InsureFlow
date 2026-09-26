package com.insureflow.service;

import com.insureflow.dto.RenewalRequestDTO;
import com.insureflow.entity.Renewal;

import java.util.List;

public interface RenewalService {
    List<Renewal> getAllRenewals();
    Renewal getRenewalById(Long id);
    Renewal processRenewal(RenewalRequestDTO requestDTO);
    List<Renewal> getRenewalsByPolicyId(Long policyId);
    List<Renewal> getRenewalsByCustomerId(Long customerId);
}
