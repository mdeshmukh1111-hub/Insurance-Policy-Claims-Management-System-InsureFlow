package com.insureflow.controller;

import com.insureflow.dto.RenewalRequestDTO;
import com.insureflow.entity.Renewal;
import com.insureflow.service.RenewalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/renewals")
@CrossOrigin(origins = "*")
public class RenewalController {

    private final RenewalService renewalService;

    public RenewalController(RenewalService renewalService) {
        this.renewalService = renewalService;
    }

    @GetMapping
    public ResponseEntity<List<Renewal>> getAllRenewals(
            @RequestParam(required = false) Long policyId,
            @RequestParam(required = false) Long customerId) {

        if (policyId != null) {
            return ResponseEntity.ok(renewalService.getRenewalsByPolicyId(policyId));
        }
        if (customerId != null) {
            return ResponseEntity.ok(renewalService.getRenewalsByCustomerId(customerId));
        }

        return ResponseEntity.ok(renewalService.getAllRenewals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Renewal> getRenewalById(@PathVariable Long id) {
        return ResponseEntity.ok(renewalService.getRenewalById(id));
    }

    @PostMapping
    public ResponseEntity<Renewal> processRenewal(@Valid @RequestBody RenewalRequestDTO dto) {
        Renewal created = renewalService.processRenewal(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
