package com.insureflow.controller;

import com.insureflow.service.DsaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dsa")
@CrossOrigin(origins = "*")
public class DsaController {

    private final DsaService dsaService;

    public DsaController(DsaService dsaService) {
        this.dsaService = dsaService;
    }

    @GetMapping("/hashmap")
    public ResponseEntity<Map<String, Object>> testHashMap(@RequestParam(defaultValue = "POL-10001") String policyNumber) {
        return ResponseEntity.ok(dsaService.testHashMapLookup(policyNumber));
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> compareSearch(@RequestParam(defaultValue = "POL-10001") String target) {
        return ResponseEntity.ok(dsaService.compareSearchAlgorithms(target));
    }

    @GetMapping("/quicksort")
    public ResponseEntity<Map<String, Object>> testQuickSort(@RequestParam(defaultValue = "premium") String sortBy) {
        return ResponseEntity.ok(dsaService.testQuickSortPolicies(sortBy));
    }

    @GetMapping("/linkedlist")
    public ResponseEntity<Map<String, Object>> testLinkedList(@RequestParam(defaultValue = "1") Long claimId) {
        return ResponseEntity.ok(dsaService.testLinkedListAuditHistory(claimId));
    }

    @GetMapping("/queue")
    public ResponseEntity<Map<String, Object>> testQueue() {
        return ResponseEntity.ok(dsaService.testPendingClaimsQueue());
    }

    @GetMapping("/priority-queue")
    public ResponseEntity<Map<String, Object>> testPriorityQueue() {
        return ResponseEntity.ok(dsaService.testPriorityQueueClaims());
    }

    @GetMapping("/bst")
    public ResponseEntity<Map<String, Object>> testBST() {
        return ResponseEntity.ok(dsaService.testPolicyBST());
    }

    @GetMapping("/graph")
    public ResponseEntity<Map<String, Object>> testGraph(
            @RequestParam(required = false) String startNodeId,
            @RequestParam(defaultValue = "bfs") String algorithm) {
        return ResponseEntity.ok(dsaService.testInsuranceGraph(startNodeId, algorithm));
    }
}
