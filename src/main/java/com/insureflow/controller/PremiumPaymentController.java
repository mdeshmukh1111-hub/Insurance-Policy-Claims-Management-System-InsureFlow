package com.insureflow.controller;

import com.insureflow.dto.PaymentRequestDTO;
import com.insureflow.entity.PremiumPayment;
import com.insureflow.service.PremiumPaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PremiumPaymentController {

    private final PremiumPaymentService paymentService;

    public PremiumPaymentController(PremiumPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<PremiumPayment>> getAllPayments(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long policyId,
            @RequestParam(required = false) Long customerId) {

        if (policyId != null) {
            return ResponseEntity.ok(paymentService.getPaymentsByPolicyId(policyId));
        }
        if (customerId != null) {
            return ResponseEntity.ok(paymentService.getPaymentsByCustomerId(customerId));
        }
        if (search != null && !search.trim().isEmpty()) {
            return ResponseEntity.ok(paymentService.searchPayments(search));
        }

        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PremiumPayment> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @PostMapping
    public ResponseEntity<PremiumPayment> recordPayment(@Valid @RequestBody PaymentRequestDTO dto) {
        PremiumPayment created = paymentService.recordPayment(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
