package com.insureflow.service.impl;

import com.insureflow.dsa.*;
import com.insureflow.entity.Claim;
import com.insureflow.entity.Policy;
import com.insureflow.repository.ClaimRepository;
import com.insureflow.repository.CustomerRepository;
import com.insureflow.repository.PolicyRepository;
import com.insureflow.service.DsaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class DsaServiceImpl implements DsaService {

    private final PolicyRepository policyRepository;
    private final ClaimRepository claimRepository;
    private final CustomerRepository customerRepository;

    public DsaServiceImpl(PolicyRepository policyRepository, ClaimRepository claimRepository, CustomerRepository customerRepository) {
        this.policyRepository = policyRepository;
        this.claimRepository = claimRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Map<String, Object> testHashMapLookup(String policyNumber) {
        List<Policy> allPolicies = policyRepository.findAll();
        CustomHashMap<String, Policy> map = new CustomHashMap<>(32);

        for (Policy p : allPolicies) {
            map.put(p.getPolicyNumber(), p);
        }

        long startTime = System.nanoTime();
        Policy found = map.get(policyNumber);
        long endTime = System.nanoTime();

        Map<String, Object> response = new HashMap<>();
        response.put("dsaConcept", "Hashing (Custom HashMap)");
        response.put("timeComplexity", "O(1) Average Case");
        response.put("totalIndexedPolicies", map.size());
        response.put("executionTimeNanos", endTime - startTime);
        response.put("found", found != null);
        response.put("result", found);
        return response;
    }

    @Override
    public Map<String, Object> compareSearchAlgorithms(String targetPolicyNumber) {
        List<Policy> policies = policyRepository.findAll();
        // Sort policies for Binary Search requirement
        policies.sort(Comparator.comparing(Policy::getPolicyNumber));

        // Linear Search
        long t1 = System.nanoTime();
        int idxLinear = LinearBinarySearch.linearSearch(policies, Policy::getPolicyNumber, targetPolicyNumber);
        long t2 = System.nanoTime();

        // Binary Search
        long t3 = System.nanoTime();
        int idxBinary = LinearBinarySearch.binarySearch(policies, Policy::getPolicyNumber, targetPolicyNumber);
        long t4 = System.nanoTime();

        Map<String, Object> response = new HashMap<>();
        response.put("dsaConcept", "Linear Search O(N) vs Binary Search O(log N)");
        response.put("totalItems", policies.size());
        response.put("target", targetPolicyNumber);
        response.put("linearSearchTimeNanos", t2 - t1);
        response.put("binarySearchTimeNanos", t4 - t3);
        response.put("linearIndex", idxLinear);
        response.put("binaryIndex", idxBinary);
        return response;
    }

    @Override
    public Map<String, Object> testQuickSortPolicies(String sortBy) {
        List<Policy> policies = new ArrayList<>(policyRepository.findAll());

        Comparator<Policy> comp;
        if ("premium".equalsIgnoreCase(sortBy)) {
            comp = Comparator.comparing(Policy::getPremiumAmount);
        } else if ("coverage".equalsIgnoreCase(sortBy)) {
            comp = Comparator.comparing(Policy::getCoverageAmount);
        } else {
            comp = Comparator.comparing(Policy::getPolicyNumber);
        }

        long start = System.nanoTime();
        QuickSort.sort(policies, comp);
        long end = System.nanoTime();

        Map<String, Object> response = new HashMap<>();
        response.put("dsaConcept", "QuickSort Algorithm");
        response.put("timeComplexity", "O(N log N) Average");
        response.put("sortedBy", sortBy);
        response.put("executionTimeNanos", end - start);
        response.put("totalSorted", policies.size());
        response.put("sortedList", policies);
        return response;
    }

    @Override
    public Map<String, Object> testLinkedListAuditHistory(Long claimId) {
        CustomLinkedList<String> auditTrail = new CustomLinkedList<>();
        
        Optional<Claim> claimOpt = claimRepository.findById(claimId);
        if (claimOpt.isPresent()) {
            Claim claim = claimOpt.get();
            auditTrail.add("Event 1: Claim " + claim.getClaimNumber() + " submitted on " + claim.getClaimDate());
            auditTrail.add("Event 2: Assigned priority level [" + claim.getPriority() + "] and risk level [" + claim.getRiskLevel() + "]");
            auditTrail.add("Event 3: Initial status updated to " + claim.getStatus());
            if ("APPROVED".equalsIgnoreCase(claim.getStatus()) || "SETTLED".equalsIgnoreCase(claim.getStatus())) {
                auditTrail.add("Event 4: Technical assessment completed by claims officer.");
            }
            if ("SETTLED".equalsIgnoreCase(claim.getStatus())) {
                auditTrail.add("Event 5: Settlement payment disburse finalized.");
            }
        } else {
            auditTrail.add("Audit Entry 1: Policy validation initiated.");
            auditTrail.add("Audit Entry 2: Customer documentation verified.");
            auditTrail.add("Audit Entry 3: Payment schedule updated.");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("dsaConcept", "Singly Linked List (Audit Trail)");
        response.put("size", auditTrail.getSize());
        response.put("history", auditTrail.toList());
        return response;
    }

    @Override
    public Map<String, Object> testPendingClaimsQueue() {
        List<Claim> pendingList = claimRepository.findByStatus("SUBMITTED");
        CustomQueue<Claim> queue = new CustomQueue<>();

        for (Claim c : pendingList) {
            queue.enqueue(c);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("dsaConcept", "Queue (FIFO Processing)");
        response.put("pendingCount", queue.size());
        response.put("queueContent", queue.toList());
        if (!queue.isEmpty()) {
            response.put("nextToProcess", queue.peek());
        }
        return response;
    }

    @Override
    public Map<String, Object> testPriorityQueueClaims() {
        List<Claim> claims = claimRepository.findAll();

        // Priority formula: URGENT > HIGH > MEDIUM > LOW, then by claim amount desc
        Comparator<Claim> priorityComp = (c1, c2) -> {
            int p1 = getPriorityWeight(c1.getPriority());
            int p2 = getPriorityWeight(c2.getPriority());
            if (p1 != p2) return Integer.compare(p1, p2);
            return Double.compare(c1.getClaimAmount(), c2.getClaimAmount());
        };

        CustomPriorityQueue<Claim> pq = new CustomPriorityQueue<>(priorityComp);
        for (Claim c : claims) {
            pq.insert(c);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("dsaConcept", "Priority Queue (Max Heap)");
        response.put("timeComplexity", "O(log N) Insert & Extract-Max");
        response.put("totalClaimsInHeap", pq.size());
        response.put("prioritizedList", pq.toSortedList());
        return response;
    }

    private int getPriorityWeight(String p) {
        if (p == null) return 1;
        switch (p.toUpperCase()) {
            case "URGENT": return 4;
            case "HIGH": return 3;
            case "MEDIUM": return 2;
            default: return 1;
        }
    }

    @Override
    public Map<String, Object> testPolicyBST() {
        List<Policy> all = policyRepository.findAll();
        PolicyBST bst = new PolicyBST();

        for (Policy p : all) {
            bst.insert(p);
        }

        List<Policy> sortedInOrder = bst.inOrderTraversal();

        Map<String, Object> response = new HashMap<>();
        response.put("dsaConcept", "Binary Search Tree (BST)");
        response.put("totalNodes", sortedInOrder.size());
        response.put("inOrderTraversal", sortedInOrder);
        return response;
    }

    @Override
    public Map<String, Object> testInsuranceGraph(String startNodeId, String algorithm) {
        InsuranceGraph graph = new InsuranceGraph();

        // Build Graph from actual repository data
        List<Policy> policies = policyRepository.findAll();
        List<Claim> claims = claimRepository.findAll();

        for (Policy p : policies) {
            String custId = "CUST-" + p.getCustomer().getId();
            String polId = "POL-" + p.getId();

            graph.addNode(custId, p.getCustomer().getFirstName() + " " + p.getCustomer().getLastName(), "CUSTOMER");
            graph.addNode(polId, p.getPolicyNumber() + " (" + p.getPolicyType().getName() + ")", "POLICY");
            graph.addEdge(custId, polId);
        }

        for (Claim c : claims) {
            String polId = "POL-" + c.getPolicy().getId();
            String clmId = "CLM-" + c.getId();

            graph.addNode(clmId, c.getClaimNumber() + " (₹" + c.getClaimAmount() + ")", "CLAIM");
            graph.addEdge(polId, clmId);
        }

        String start = (startNodeId != null && graph.getNodeMap().containsKey(startNodeId)) ?
                startNodeId : graph.getNodeMap().keySet().stream().findFirst().orElse("");

        List<InsuranceGraph.Node> traversal;
        if ("dfs".equalsIgnoreCase(algorithm)) {
            traversal = graph.dfs(start);
        } else {
            traversal = graph.bfs(start);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("dsaConcept", "Graph Data Structure (Adjacency List)");
        response.put("algorithmUsed", algorithm.toUpperCase());
        response.put("startNodeId", start);
        response.put("totalNodesInGraph", graph.getNodeMap().size());
        response.put("traversalResult", traversal);
        return response;
    }
}
