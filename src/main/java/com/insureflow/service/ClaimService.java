package com.insureflow.service;

import com.insureflow.dto.AssessmentRequestDTO;
import com.insureflow.dto.ClaimRequestDTO;
import com.insureflow.dto.SettlementRequestDTO;
import com.insureflow.entity.Claim;
import com.insureflow.entity.ClaimAssessment;
import com.insureflow.entity.ClaimDocument;
import com.insureflow.entity.ClaimSettlement;

import java.util.List;

public interface ClaimService {
    List<Claim> getAllClaims();
    Claim getClaimById(Long id);
    Claim getClaimByNumber(String claimNumber);
    Claim submitClaim(ClaimRequestDTO requestDTO);
    Claim updateClaimStatus(Long id, String status);
    List<Claim> getClaimsByCustomerId(Long customerId);
    List<Claim> getClaimsByPolicyId(Long policyId);
    List<Claim> searchClaims(String keyword, String status, String priority);

    // Document Management
    ClaimDocument addClaimDocument(Long claimId, String documentName, String documentType, String filePath);
    List<ClaimDocument> getClaimDocuments(Long claimId);

    // Assessment Management
    ClaimAssessment assessClaim(AssessmentRequestDTO requestDTO);
    ClaimAssessment getClaimAssessment(Long claimId);

    // Settlement Management
    ClaimSettlement settleClaim(SettlementRequestDTO requestDTO);
    ClaimSettlement getClaimSettlement(Long claimId);
}
