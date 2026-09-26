package com.insureflow.service;

import com.insureflow.dto.PaymentRequestDTO;
import com.insureflow.entity.PremiumPayment;

import java.util.List;

public interface PremiumPaymentService {
    List<PremiumPayment> getAllPayments();
    PremiumPayment getPaymentById(Long id);
    PremiumPayment recordPayment(PaymentRequestDTO requestDTO);
    List<PremiumPayment> getPaymentsByPolicyId(Long policyId);
    List<PremiumPayment> getPaymentsByCustomerId(Long customerId);
    List<PremiumPayment> searchPayments(String keyword);
}
