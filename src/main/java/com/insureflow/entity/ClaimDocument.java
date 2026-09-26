package com.insureflow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "claim_documents")
public class ClaimDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "claim_id", nullable = false)
    private Claim claim;

    @NotBlank(message = "Document name is required")
    @Column(name = "document_name", nullable = false, length = 100)
    private String documentName;

    @NotBlank(message = "Document type is required")
    @Column(name = "document_type", nullable = false, length = 50)
    private String documentType; // MEDICAL_REPORT, POLICE_FIR, REPAIR_ESTIMATE, INVOICE, ID_PROOF

    @Column(name = "file_path", nullable = false, length = 255)
    private String filePath;

    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    public ClaimDocument() {}

    public ClaimDocument(Claim claim, String documentName, String documentType, String filePath) {
        this.claim = claim;
        this.documentName = documentName;
        this.documentType = documentType;
        this.filePath = filePath;
    }

    @PrePersist
    protected void onCreate() {
        this.uploadedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Claim getClaim() { return claim; }
    public void setClaim(Claim claim) { this.claim = claim; }

    public String getDocumentName() { return documentName; }
    public void setDocumentName(String documentName) { this.documentName = documentName; }

    public String getDocumentType() { return documentType; }
    public void setDocumentType(String documentType) { this.documentType = documentType; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
}
