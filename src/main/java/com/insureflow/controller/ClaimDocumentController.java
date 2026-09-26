package com.insureflow.controller;

import com.insureflow.entity.ClaimDocument;
import com.insureflow.service.ClaimService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/claim-documents")
@CrossOrigin(origins = "*")
public class ClaimDocumentController {

    private final ClaimService claimService;

    public ClaimDocumentController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping
    public ResponseEntity<List<ClaimDocument>> getDocumentsByClaimId(@RequestParam Long claimId) {
        return ResponseEntity.ok(claimService.getClaimDocuments(claimId));
    }

    @PostMapping
    public ResponseEntity<ClaimDocument> uploadDocument(@RequestBody Map<String, String> payload) {
        Long claimId = Long.parseLong(payload.get("claimId"));
        String docName = payload.get("documentName");
        String docType = payload.get("documentType");
        String filePath = payload.getOrDefault("filePath", "/uploads/documents/" + docName);

        ClaimDocument doc = claimService.addClaimDocument(claimId, docName, docType, filePath);
        return new ResponseEntity<>(doc, HttpStatus.CREATED);
    }
}
