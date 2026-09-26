package com.insureflow.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class SettlementRequestDTO {

    @NotNull(message = "Claim ID is required")
    private Long claimId;

    @PositiveOrZero(message = "Approved amount cannot be negative")
    private Double approvedAmount;

    private String paymentMethod = "BANK_TRANSFER";

    private String notes;

    public Long getClaimId() { return claimId; }
    public void setClaimId(Long claimId) { this.claimId = claimId; }

    public Double getApprovedAmount() { return approvedAmount; }
    public void setApprovedAmount(Double approvedAmount) { this.approvedAmount = approvedAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
