package com.insureflow.controller;

import com.insureflow.dto.ClaimRequestDTO;
import com.insureflow.entity.Claim;
import com.insureflow.service.ClaimService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
@CrossOrigin(origins = "*")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping
    public ResponseEntity<List<Claim>> getAllClaims(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long policyId) {

        if (customerId != null) {
            return ResponseEntity.ok(claimService.getClaimsByCustomerId(customerId));
        }
        if (policyId != null) {
            return ResponseEntity.ok(claimService.getClaimsByPolicyId(policyId));
        }
        if (search != null || status != null || priority != null) {
            return ResponseEntity.ok(claimService.searchClaims(search, status, priority));
        }

        return ResponseEntity.ok(claimService.getAllClaims());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Claim> getClaimById(@PathVariable Long id) {
        return ResponseEntity.ok(claimService.getClaimById(id));
    }

    @PostMapping
    public ResponseEntity<Claim> submitClaim(@Valid @RequestBody ClaimRequestDTO dto) {
        Claim created = claimService.submitClaim(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Claim> updateClaimStatus(@PathVariable Long id, @RequestParam String status) {
        Claim updated = claimService.updateClaimStatus(id, status);
        return ResponseEntity.ok(updated);
    }
}
