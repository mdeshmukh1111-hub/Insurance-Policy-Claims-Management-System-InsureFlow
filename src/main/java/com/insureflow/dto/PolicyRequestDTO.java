package com.insureflow.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public class PolicyRequestDTO {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    private Long agentId;

    @NotNull(message = "Policy Type ID is required")
    private Long policyTypeId;

    @Positive(message = "Coverage amount must be greater than 0")
    private Double coverageAmount;

    @Positive(message = "Premium amount must be greater than 0")
    private Double premiumAmount;

    private String paymentFrequency = "ANNUALLY";

    private LocalDate startDate;

    private Integer termMonths = 12;

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getAgentId() { return agentId; }
    public void setAgentId(Long agentId) { this.agentId = agentId; }

    public Long getPolicyTypeId() { return policyTypeId; }
    public void setPolicyTypeId(Long policyTypeId) { this.policyTypeId = policyTypeId; }

    public Double getCoverageAmount() { return coverageAmount; }
    public void setCoverageAmount(Double coverageAmount) { this.coverageAmount = coverageAmount; }

    public Double getPremiumAmount() { return premiumAmount; }
    public void setPremiumAmount(Double premiumAmount) { this.premiumAmount = premiumAmount; }

    public String getPaymentFrequency() { return paymentFrequency; }
    public void setPaymentFrequency(String paymentFrequency) { this.paymentFrequency = paymentFrequency; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public Integer getTermMonths() { return termMonths; }
    public void setTermMonths(Integer termMonths) { this.termMonths = termMonths; }
}
