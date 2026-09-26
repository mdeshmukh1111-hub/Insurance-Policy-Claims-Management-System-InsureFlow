package com.insureflow.service;

import com.insureflow.entity.Claim;
import com.insureflow.entity.Policy;

import java.util.List;
import java.util.Map;

public interface DsaService {
    Map<String, Object> testHashMapLookup(String policyNumber);
    Map<String, Object> compareSearchAlgorithms(String targetPolicyNumber);
    Map<String, Object> testQuickSortPolicies(String sortBy);
    Map<String, Object> testLinkedListAuditHistory(Long claimId);
    Map<String, Object> testPendingClaimsQueue();
    Map<String, Object> testPriorityQueueClaims();
    Map<String, Object> testPolicyBST();
    Map<String, Object> testInsuranceGraph(String startNodeId, String algorithm);
}
