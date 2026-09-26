package com.insureflow.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "renewals")
public class Renewal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "renewal_number", unique = true, nullable = false, length = 50)
    private String renewalNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "policy_id", nullable = false)
    private Policy policy;

    @Column(name = "renewal_date", nullable = false)
    private LocalDate renewalDate;

    @Column(name = "previous_expiry_date", nullable = false)
    private LocalDate previousExpiryDate;

    @Column(name = "new_expiry_date", nullable = false)
    private LocalDate newExpiryDate;

    @Column(name = "renewal_premium", nullable = false)
    private Double renewalPremium;

    @Column(name = "status", nullable = false, length = 30)
    private String status = "COMPLETED"; // REQUESTED, APPROVED, COMPLETED, REJECTED

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "processed_by_agent_id")
    private Agent processedByAgent;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Renewal() {}

    public Renewal(String renewalNumber, Policy policy, LocalDate renewalDate, LocalDate previousExpiryDate, LocalDate newExpiryDate, Double renewalPremium, String status, Agent processedByAgent) {
        this.renewalNumber = renewalNumber;
        this.policy = policy;
        this.renewalDate = renewalDate;
        this.previousExpiryDate = previousExpiryDate;
        this.newExpiryDate = newExpiryDate;
        this.renewalPremium = renewalPremium;
        this.status = status;
        this.processedByAgent = processedByAgent;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.renewalDate == null) this.renewalDate = LocalDate.now();
        if (this.status == null) this.status = "COMPLETED";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRenewalNumber() { return renewalNumber; }
    public void setRenewalNumber(String renewalNumber) { this.renewalNumber = renewalNumber; }

    public Policy getPolicy() { return policy; }
    public void setPolicy(Policy policy) { this.policy = policy; }

    public LocalDate getRenewalDate() { return renewalDate; }
    public void setRenewalDate(LocalDate renewalDate) { this.renewalDate = renewalDate; }

    public LocalDate getPreviousExpiryDate() { return previousExpiryDate; }
    public void setPreviousExpiryDate(LocalDate previousExpiryDate) { this.previousExpiryDate = previousExpiryDate; }

    public LocalDate getNewExpiryDate() { return newExpiryDate; }
    public void setNewExpiryDate(LocalDate newExpiryDate) { this.newExpiryDate = newExpiryDate; }

    public Double getRenewalPremium() { return renewalPremium; }
    public void setRenewalPremium(Double renewalPremium) { this.renewalPremium = renewalPremium; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Agent getProcessedByAgent() { return processedByAgent; }
    public void setProcessedByAgent(Agent processedByAgent) { this.processedByAgent = processedByAgent; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
