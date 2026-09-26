package com.insureflow.controller;

import com.insureflow.entity.PolicyType;
import com.insureflow.service.PolicyTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policy-types")
@CrossOrigin(origins = "*")
public class PolicyTypeController {

    private final PolicyTypeService policyTypeService;

    public PolicyTypeController(PolicyTypeService policyTypeService) {
        this.policyTypeService = policyTypeService;
    }

    @GetMapping
    public ResponseEntity<List<PolicyType>> getAllPolicyTypes(@RequestParam(required = false) String category) {
        if (category != null && !category.trim().isEmpty()) {
            return ResponseEntity.ok(policyTypeService.getPolicyTypesByCategory(category));
        }
        return ResponseEntity.ok(policyTypeService.getAllPolicyTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolicyType> getPolicyTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(policyTypeService.getPolicyTypeById(id));
    }

    @PostMapping
    public ResponseEntity<PolicyType> createPolicyType(@Valid @RequestBody PolicyType policyType) {
        PolicyType created = policyTypeService.createPolicyType(policyType);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PolicyType> updatePolicyType(@PathVariable Long id, @Valid @RequestBody PolicyType policyType) {
        PolicyType updated = policyTypeService.updatePolicyType(id, policyType);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicyType(@PathVariable Long id) {
        policyTypeService.deletePolicyType(id);
        return ResponseEntity.noContent().build();
    }
}
