package com.insureflow.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class RenewalRequestDTO {

    @NotNull(message = "Policy ID is required")
    private Long policyId;

    private Integer termMonths = 12;

    private Long agentId;

    public Long getPolicyId() { return policyId; }
    public void setPolicyId(Long policyId) { this.policyId = policyId; }

    public Integer getTermMonths() { return termMonths; }
    public void setTermMonths(Integer termMonths) { this.termMonths = termMonths; }

    public Long getAgentId() { return agentId; }
    public void setAgentId(Long agentId) { this.agentId = agentId; }
}
