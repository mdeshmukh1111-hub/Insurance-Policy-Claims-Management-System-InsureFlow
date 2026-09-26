package com.insureflow.service.impl;

import com.insureflow.dto.AssessmentRequestDTO;
import com.insureflow.dto.ClaimRequestDTO;
import com.insureflow.dto.SettlementRequestDTO;
import com.insureflow.entity.*;
import com.insureflow.exception.BadRequestException;
import com.insureflow.exception.ResourceNotFoundException;
import com.insureflow.repository.*;
import com.insureflow.service.ClaimService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final PolicyRepository policyRepository;
    private final CustomerRepository customerRepository;
    private final AgentRepository agentRepository;
    private final ClaimDocumentRepository claimDocumentRepository;
    private final ClaimAssessmentRepository claimAssessmentRepository;
    private final ClaimSettlementRepository claimSettlementRepository;

    public ClaimServiceImpl(ClaimRepository claimRepository, PolicyRepository policyRepository, CustomerRepository customerRepository, AgentRepository agentRepository, ClaimDocumentRepository claimDocumentRepository, ClaimAssessmentRepository claimAssessmentRepository, ClaimSettlementRepository claimSettlementRepository) {
        this.claimRepository = claimRepository;
        this.policyRepository = policyRepository;
        this.customerRepository = customerRepository;
        this.agentRepository = agentRepository;
        this.claimDocumentRepository = claimDocumentRepository;
        this.claimAssessmentRepository = claimAssessmentRepository;
        this.claimSettlementRepository = claimSettlementRepository;
    }

    @Override
    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    @Override
    public Claim getClaimById(Long id) {
        return claimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found with ID: " + id));
    }

    @Override
    public Claim getClaimByNumber(String claimNumber) {
        return claimRepository.findByClaimNumber(claimNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found with number: " + claimNumber));
    }

    @Override
    public Claim submitClaim(ClaimRequestDTO dto) {
        Policy policy = policyRepository.findById(dto.getPolicyId())
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with ID: " + dto.getPolicyId()));

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + dto.getCustomerId()));

        if (dto.getClaimAmount() > policy.getCoverageAmount()) {
            throw new BadRequestException("Claim amount ($" + dto.getClaimAmount() + ") cannot exceed policy coverage amount ($" + policy.getCoverageAmount() + ")!");
        }

        String claimNumber = "CLM-" + (1000 + new Random().nextInt(9000));

        Claim claim = new Claim(
                claimNumber,
                policy,
                customer,
                dto.getClaimAmount(),
                dto.getIncidentDate() != null ? dto.getIncidentDate() : LocalDate.now(),
                dto.getIncidentDescription(),
                "SUBMITTED",
                dto.getPriority() != null ? dto.getPriority() : "MEDIUM",
                dto.getRiskLevel() != null ? dto.getRiskLevel() : "LOW"
        );

        return claimRepository.save(claim);
    }

    @Override
    public Claim updateClaimStatus(Long id, String status) {
        Claim claim = getClaimById(id);
        claim.setStatus(status.toUpperCase());
        return claimRepository.save(claim);
    }

    @Override
    public List<Claim> getClaimsByCustomerId(Long customerId) {
        return claimRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Claim> getClaimsByPolicyId(Long policyId) {
        return claimRepository.findByPolicyId(policyId);
    }

    @Override
    public List<Claim> searchClaims(String keyword, String status, String priority) {
        return claimRepository.searchClaims(keyword, status, priority);
    }

    @Override
    public ClaimDocument addClaimDocument(Long claimId, String documentName, String documentType, String filePath) {
        Claim claim = getClaimById(claimId);
        ClaimDocument doc = new ClaimDocument(claim, documentName, documentType, filePath);
        return claimDocumentRepository.save(doc);
    }

    @Override
    public List<ClaimDocument> getClaimDocuments(Long claimId) {
        return claimDocumentRepository.findByClaimId(claimId);
    }

    @Override
    public ClaimAssessment assessClaim(AssessmentRequestDTO dto) {
        Claim claim = getClaimById(dto.getClaimId());

        Agent agent = null;
        if (dto.getAgentId() != null) {
            agent = agentRepository.findById(dto.getAgentId()).orElse(null);
        }

        ClaimAssessment assessment = claimAssessmentRepository.findByClaimId(dto.getClaimId())
                .orElse(new ClaimAssessment());

        assessment.setClaim(claim);
        assessment.setAgent(agent);
        assessment.setAssessedAmount(dto.getAssessedAmount() != null ? dto.getAssessedAmount() : claim.getClaimAmount());
        assessment.setAssessmentNotes(dto.getAssessmentNotes());
        assessment.setRecommendation(dto.getRecommendation());
        assessment.setStatus("COMPLETED");

        ClaimAssessment savedAssessment = claimAssessmentRepository.save(assessment);

        // Update claim status based on assessment recommendation
        if ("APPROVE".equalsIgnoreCase(dto.getRecommendation())) {
            claim.setStatus("APPROVED");
        } else if ("REJECT".equalsIgnoreCase(dto.getRecommendation())) {
            claim.setStatus("REJECTED");
        } else {
            claim.setStatus("UNDER_REVIEW");
        }

        claimRepository.save(claim);
        return savedAssessment;
    }

    @Override
    public ClaimAssessment getClaimAssessment(Long claimId) {
        return claimAssessmentRepository.findByClaimId(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("No assessment found for claim ID: " + claimId));
    }

    @Override
    public ClaimSettlement settleClaim(SettlementRequestDTO dto) {
        Claim claim = getClaimById(dto.getClaimId());

        if (!"APPROVED".equalsIgnoreCase(claim.getStatus())) {
            throw new BadRequestException("Only APPROVED claims can be settled! Current status: " + claim.getStatus());
        }

        ClaimSettlement settlement = claimSettlementRepository.findByClaimId(dto.getClaimId())
                .orElse(new ClaimSettlement());

        String paymentRef = "SETTL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        settlement.setClaim(claim);
        settlement.setApprovedAmount(dto.getApprovedAmount() != null ? dto.getApprovedAmount() : claim.getClaimAmount());
        settlement.setPaymentReference(paymentRef);
        settlement.setSettlementStatus("COMPLETED");
        settlement.setPaymentMethod(dto.getPaymentMethod() != null ? dto.getPaymentMethod() : "BANK_TRANSFER");
        settlement.setNotes(dto.getNotes());

        ClaimSettlement savedSettlement = claimSettlementRepository.save(settlement);

        // Update Claim Status to SETTLED
        claim.setStatus("SETTLED");
        claimRepository.save(claim);

        return savedSettlement;
    }

    @Override
    public ClaimSettlement getClaimSettlement(Long claimId) {
        return claimSettlementRepository.findByClaimId(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("No settlement record found for claim ID: " + claimId));
    }
}
