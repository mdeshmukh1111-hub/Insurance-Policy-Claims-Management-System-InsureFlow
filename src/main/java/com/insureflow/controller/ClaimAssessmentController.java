package com.insureflow.controller;

import com.insureflow.dto.AssessmentRequestDTO;
import com.insureflow.entity.ClaimAssessment;
import com.insureflow.service.ClaimService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/claim-assessments")
@CrossOrigin(origins = "*")
public class ClaimAssessmentController {

    private final ClaimService claimService;

    public ClaimAssessmentController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping
    public ResponseEntity<ClaimAssessment> getAssessmentByClaimId(@RequestParam Long claimId) {
        return ResponseEntity.ok(claimService.getClaimAssessment(claimId));
    }

    @PostMapping
    public ResponseEntity<ClaimAssessment> assessClaim(@Valid @RequestBody AssessmentRequestDTO dto) {
        ClaimAssessment assessment = claimService.assessClaim(dto);
        return new ResponseEntity<>(assessment, HttpStatus.CREATED);
    }
}
