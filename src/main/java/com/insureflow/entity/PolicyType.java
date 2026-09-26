package com.insureflow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "policy_types")
public class PolicyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_code", unique = true, nullable = false, length = 30)
    private String typeCode;

    @NotBlank(message = "Policy type name is required")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @NotBlank(message = "Category is required")
    @Column(name = "category", nullable = false, length = 50)
    private String category; // HEALTH, AUTO, LIFE, PROPERTY

    @Positive(message = "Base premium must be greater than 0")
    @Column(name = "base_premium", nullable = false)
    private Double basePremium;

    @Positive(message = "Min coverage must be greater than 0")
    @Column(name = "min_coverage", nullable = false)
    private Double minCoverage;

    @Positive(message = "Max coverage must be greater than 0")
    @Column(name = "max_coverage", nullable = false)
    private Double maxCoverage;

    @Column(name = "default_term_months", nullable = false)
    private Integer defaultTermMonths = 12;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public PolicyType() {}

    public PolicyType(String typeCode, String name, String description, String category, Double basePremium, Double minCoverage, Double maxCoverage, Integer defaultTermMonths) {
        this.typeCode = typeCode;
        this.name = name;
        this.description = description;
        this.category = category;
        this.basePremium = basePremium;
        this.minCoverage = minCoverage;
        this.maxCoverage = maxCoverage;
        this.defaultTermMonths = defaultTermMonths;
        this.active = true;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.active == null) this.active = true;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTypeCode() { return typeCode; }
    public void setTypeCode(String typeCode) { this.typeCode = typeCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getBasePremium() { return basePremium; }
    public void setBasePremium(Double basePremium) { this.basePremium = basePremium; }

    public Double getMinCoverage() { return minCoverage; }
    public void setMinCoverage(Double minCoverage) { this.minCoverage = minCoverage; }

    public Double getMaxCoverage() { return maxCoverage; }
    public void setMaxCoverage(Double maxCoverage) { this.maxCoverage = maxCoverage; }

    public Integer getDefaultTermMonths() { return defaultTermMonths; }
    public void setDefaultTermMonths(Integer defaultTermMonths) { this.defaultTermMonths = defaultTermMonths; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
