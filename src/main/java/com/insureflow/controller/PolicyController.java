package com.insureflow.controller;

import com.insureflow.dto.PolicyRequestDTO;
import com.insureflow.entity.Policy;
import com.insureflow.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
@CrossOrigin(origins = "*")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping
    public ResponseEntity<List<Policy>> getAllPolicies(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long agentId) {

        if (customerId != null) {
            return ResponseEntity.ok(policyService.getPoliciesByCustomerId(customerId));
        }
        if (agentId != null) {
            return ResponseEntity.ok(policyService.getPoliciesByAgentId(agentId));
        }
        if (search != null || status != null || categoryId != null) {
            return ResponseEntity.ok(policyService.searchPolicies(search, status, categoryId));
        }

        return ResponseEntity.ok(policyService.getAllPolicies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Policy> getPolicyById(@PathVariable Long id) {
        return ResponseEntity.ok(policyService.getPolicyById(id));
    }

    @PostMapping
    public ResponseEntity<Policy> createPolicy(@Valid @RequestBody PolicyRequestDTO dto) {
        Policy created = policyService.createPolicy(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Policy> updatePolicyStatus(@PathVariable Long id, @RequestParam String status) {
        Policy updated = policyService.updatePolicyStatus(id, status);
        return ResponseEntity.ok(updated);
    }
}
