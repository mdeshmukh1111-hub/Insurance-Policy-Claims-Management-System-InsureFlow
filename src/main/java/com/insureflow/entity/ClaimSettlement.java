package com.insureflow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Entity
@Table(name = "claim_settlements")
public class ClaimSettlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "claim_id", nullable = false, unique = true)
    private Claim claim;

    @PositiveOrZero(message = "Approved amount cannot be negative")
    @Column(name = "approved_amount", nullable = false)
    private Double approvedAmount;

    @Column(name = "settlement_date", nullable = false)
    private LocalDateTime settlementDate;

    @Column(name = "payment_reference", unique = true, nullable = false, length = 100)
    private String paymentReference;

    @Column(name = "settlement_status", nullable = false, length = 30)
    private String settlementStatus = "COMPLETED"; // COMPLETED, PROCESSING, FAILED

    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod; // BANK_TRANSFER, CHEQUE, DIRECT_DEPOSIT

    @Column(name = "notes", length = 500)
    private String notes;

    public ClaimSettlement() {}

    public ClaimSettlement(Claim claim, Double approvedAmount, String paymentReference, String settlementStatus, String paymentMethod, String notes) {
        this.claim = claim;
        this.approvedAmount = approvedAmount;
        this.paymentReference = paymentReference;
        this.settlementStatus = settlementStatus;
        this.paymentMethod = paymentMethod;
        this.notes = notes;
    }

    @PrePersist
    protected void onCreate() {
        this.settlementDate = LocalDateTime.now();
        if (this.settlementStatus == null) this.settlementStatus = "COMPLETED";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Claim getClaim() { return claim; }
    public void setClaim(Claim claim) { this.claim = claim; }

    public Double getApprovedAmount() { return approvedAmount; }
    public void setApprovedAmount(Double approvedAmount) { this.approvedAmount = approvedAmount; }

    public LocalDateTime getSettlementDate() { return settlementDate; }
    public void setSettlementDate(LocalDateTime settlementDate) { this.settlementDate = settlementDate; }

    public String getPaymentReference() { return paymentReference; }
    public void setPaymentReference(String paymentReference) { this.paymentReference = paymentReference; }

    public String getSettlementStatus() { return settlementStatus; }
    public void setSettlementStatus(String settlementStatus) { this.settlementStatus = settlementStatus; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
