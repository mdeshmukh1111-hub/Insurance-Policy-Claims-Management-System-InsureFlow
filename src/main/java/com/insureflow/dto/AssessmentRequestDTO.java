package com.insureflow.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class AssessmentRequestDTO {

    @NotNull(message = "Claim ID is required")
    private Long claimId;

    private Long agentId;

    @PositiveOrZero(message = "Assessed amount cannot be negative")
    private Double assessedAmount;

    private String assessmentNotes;

    @NotNull(message = "Recommendation is required")
    private String recommendation; // APPROVE, REJECT, REQUEST_INFO

    public Long getClaimId() { return claimId; }
    public void setClaimId(Long claimId) { this.claimId = claimId; }

    public Long getAgentId() { return agentId; }
    public void setAgentId(Long agentId) { this.agentId = agentId; }

    public Double getAssessedAmount() { return assessedAmount; }
    public void setAssessedAmount(Double assessedAmount) { this.assessedAmount = assessedAmount; }

    public String getAssessmentNotes() { return assessmentNotes; }
    public void setAssessmentNotes(String assessmentNotes) { this.assessmentNotes = assessmentNotes; }

    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
}
