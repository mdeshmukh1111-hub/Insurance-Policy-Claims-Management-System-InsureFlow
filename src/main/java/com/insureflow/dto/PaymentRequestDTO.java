package com.insureflow.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PaymentRequestDTO {

    @NotNull(message = "Policy ID is required")
    private Long policyId;

    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    private String paymentMethod = "CREDIT_CARD"; // CREDIT_CARD, BANK_TRANSFER, UPI, CHEQUE

    private String notes;

    public Long getPolicyId() { return policyId; }
    public void setPolicyId(Long policyId) { this.policyId = policyId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
