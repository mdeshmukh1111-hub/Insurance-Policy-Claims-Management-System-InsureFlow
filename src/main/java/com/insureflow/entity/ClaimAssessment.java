package com.insureflow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Entity
@Table(name = "claim_assessments")
public class ClaimAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "claim_id", nullable = false, unique = true)
    private Claim claim;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent_id")
    private Agent agent;

    @PositiveOrZero(message = "Assessed amount cannot be negative")
    @Column(name = "assessed_amount", nullable = false)
    private Double assessedAmount;

    @Column(name = "assessment_notes", length = 1000)
    private String assessmentNotes;

    @Column(name = "assessment_date", nullable = false)
    private LocalDateTime assessmentDate;

    @Column(name = "recommendation", nullable = false, length = 30)
    private String recommendation; // APPROVE, REJECT, REQUEST_INFO

    @Column(name = "status", nullable = false, length = 20)
    private String status = "COMPLETED"; // COMPLETED, PENDING

    public ClaimAssessment() {}

    public ClaimAssessment(Claim claim, Agent agent, Double assessedAmount, String assessmentNotes, String recommendation, String status) {
        this.claim = claim;
        this.agent = agent;
        this.assessedAmount = assessedAmount;
        this.assessmentNotes = assessmentNotes;
        this.recommendation = recommendation;
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        this.assessmentDate = LocalDateTime.now();
        if (this.status == null) this.status = "COMPLETED";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Claim getClaim() { return claim; }
    public void setClaim(Claim claim) { this.claim = claim; }

    public Agent getAgent() { return agent; }
    public void setAgent(Agent agent) { this.agent = agent; }

    public Double getAssessedAmount() { return assessedAmount; }
    public void setAssessedAmount(Double assessedAmount) { this.assessedAmount = assessedAmount; }

    public String getAssessmentNotes() { return assessmentNotes; }
    public void setAssessmentNotes(String assessmentNotes) { this.assessmentNotes = assessmentNotes; }

    public LocalDateTime getAssessmentDate() { return assessmentDate; }
    public void setAssessmentDate(LocalDateTime assessmentDate) { this.assessmentDate = assessmentDate; }

    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
