package com.insureflow.service.impl;

import com.insureflow.dto.PaymentRequestDTO;
import com.insureflow.entity.Policy;
import com.insureflow.entity.PremiumPayment;
import com.insureflow.exception.ResourceNotFoundException;
import com.insureflow.repository.PolicyRepository;
import com.insureflow.repository.PremiumPaymentRepository;
import com.insureflow.service.PremiumPaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
public class PremiumPaymentServiceImpl implements PremiumPaymentService {

    private final PremiumPaymentRepository paymentRepository;
    private final PolicyRepository policyRepository;

    public PremiumPaymentServiceImpl(PremiumPaymentRepository paymentRepository, PolicyRepository policyRepository) {
        this.paymentRepository = paymentRepository;
        this.policyRepository = policyRepository;
    }

    @Override
    public List<PremiumPayment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public PremiumPayment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));
    }

    @Override
    public PremiumPayment recordPayment(PaymentRequestDTO dto) {
        Policy policy = policyRepository.findById(dto.getPolicyId())
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with ID: " + dto.getPolicyId()));

        String paymentNumber = "PAY-" + (1000 + new Random().nextInt(9000));
        String txnRef = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        PremiumPayment payment = new PremiumPayment(
                paymentNumber,
                policy,
                dto.getAmount(),
                LocalDateTime.now(),
                dto.getPaymentMethod() != null ? dto.getPaymentMethod() : "CREDIT_CARD",
                "SUCCESS",
                txnRef,
                dto.getNotes()
        );

        PremiumPayment savedPayment = paymentRepository.save(payment);

        // Update Policy Status to ACTIVE upon successful payment
        if ("PENDING".equalsIgnoreCase(policy.getStatus()) || "RENEWAL_DUE".equalsIgnoreCase(policy.getStatus())) {
            policy.setStatus("ACTIVE");
            policyRepository.save(policy);
        }

        return savedPayment;
    }

    @Override
    public List<PremiumPayment> getPaymentsByPolicyId(Long policyId) {
        return paymentRepository.findByPolicyId(policyId);
    }

    @Override
    public List<PremiumPayment> getPaymentsByCustomerId(Long customerId) {
        return paymentRepository.findByPolicyCustomerId(customerId);
    }

    @Override
    public List<PremiumPayment> searchPayments(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return paymentRepository.findAll();
        }
        return paymentRepository.searchPayments(keyword.trim());
    }
}
