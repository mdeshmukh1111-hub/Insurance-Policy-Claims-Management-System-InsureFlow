package com.insureflow.controller;

import com.insureflow.dto.SettlementRequestDTO;
import com.insureflow.entity.ClaimSettlement;
import com.insureflow.service.ClaimService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/claim-settlements")
@CrossOrigin(origins = "*")
public class ClaimSettlementController {

    private final ClaimService claimService;

    public ClaimSettlementController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping
    public ResponseEntity<ClaimSettlement> getSettlementByClaimId(@RequestParam Long claimId) {
        return ResponseEntity.ok(claimService.getClaimSettlement(claimId));
    }

    @PostMapping
    public ResponseEntity<ClaimSettlement> settleClaim(@Valid @RequestBody SettlementRequestDTO dto) {
        ClaimSettlement settlement = claimService.settleClaim(dto);
        return new ResponseEntity<>(settlement, HttpStatus.CREATED);
    }
}
