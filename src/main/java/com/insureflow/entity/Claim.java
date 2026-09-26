package com.insureflow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "claims")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "claim_number", unique = true, nullable = false, length = 50)
    private String claimNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "policy_id", nullable = false)
    private Policy policy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Positive(message = "Claim amount must be greater than 0")
    @Column(name = "claim_amount", nullable = false)
    private Double claimAmount;

    @NotNull(message = "Incident date is required")
    @Column(name = "incident_date", nullable = false)
    private LocalDate incidentDate;

    @NotBlank(message = "Incident description is required")
    @Column(name = "incident_description", nullable = false, length = 1000)
    private String incidentDescription;

    @Column(name = "claim_date", nullable = false, updatable = false)
    private LocalDateTime claimDate;

    @Column(name = "status", nullable = false, length = 30)
    private String status = "SUBMITTED"; // SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED, SETTLED

    @Column(name = "priority", nullable = false, length = 20)
    private String priority = "MEDIUM"; // LOW, MEDIUM, HIGH, URGENT

    @Column(name = "risk_level", length = 20)
    private String riskLevel = "LOW"; // LOW, MEDIUM, HIGH

    @Column(name = "waiting_days")
    private Integer waitingDays = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Claim() {}

    public Claim(String claimNumber, Policy policy, Customer customer, Double claimAmount, LocalDate incidentDate, String incidentDescription, String status, String priority, String riskLevel) {
        this.claimNumber = claimNumber;
        this.policy = policy;
        this.customer = customer;
        this.claimAmount = claimAmount;
        this.incidentDate = incidentDate;
        this.incidentDescription = incidentDescription;
        this.status = status;
        this.priority = priority;
        this.riskLevel = riskLevel;
    }

    @PrePersist
    protected void onCreate() {
        this.claimDate = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) this.status = "SUBMITTED";
        if (this.priority == null) this.priority = "MEDIUM";
        if (this.riskLevel == null) this.riskLevel = "LOW";
        if (this.waitingDays == null) this.waitingDays = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClaimNumber() { return claimNumber; }
    public void setClaimNumber(String claimNumber) { this.claimNumber = claimNumber; }

    public Policy getPolicy() { return policy; }
    public void setPolicy(Policy policy) { this.policy = policy; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Double getClaimAmount() { return claimAmount; }
    public void setClaimAmount(Double claimAmount) { this.claimAmount = claimAmount; }

    public LocalDate getIncidentDate() { return incidentDate; }
    public void setIncidentDate(LocalDate incidentDate) { this.incidentDate = incidentDate; }

    public String getIncidentDescription() { return incidentDescription; }
    public void setIncidentDescription(String incidentDescription) { this.incidentDescription = incidentDescription; }

    public LocalDateTime getClaimDate() { return claimDate; }
    public void setClaimDate(LocalDateTime claimDate) { this.claimDate = claimDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }

    public Integer getWaitingDays() { return waitingDays; }
    public void setWaitingDays(Integer waitingDays) { this.waitingDays = waitingDays; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
