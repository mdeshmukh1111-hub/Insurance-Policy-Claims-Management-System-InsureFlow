# InsureFlow - Entity Relationship (ER) Diagram & Model

This document outlines the ER Model for **InsureFlow**, a complete Insurance Policy & Claims Management System.

---

## 1. Entities & Attributes

### 1. `CUSTOMERS`
- **Primary Key**: `id` (BIGINT, AUTO_INCREMENT)
- **Unique Candidate Keys**: `customer_code`, `email`
- **Attributes**: `first_name`, `last_name`, `phone`, `address`, `city`, `state`, `postal_code`, `status`, `created_at`, `updated_at`

### 2. `AGENTS`
- **Primary Key**: `id` (BIGINT, AUTO_INCREMENT)
- **Unique Candidate Keys**: `agent_code`, `email`
- **Attributes**: `first_name`, `last_name`, `phone`, `agency_name`, `status`, `created_at`

### 3. `POLICY_TYPES`
- **Primary Key**: `id` (BIGINT, AUTO_INCREMENT)
- **Unique Candidate Keys**: `type_code`
- **Attributes**: `name`, `description`, `category` (HEALTH, AUTO, LIFE, PROPERTY), `base_premium`, `min_coverage`, `max_coverage`, `default_term_months`, `active`, `created_at`

### 4. `POLICIES`
- **Primary Key**: `id` (BIGINT, AUTO_INCREMENT)
- **Unique Candidate Keys**: `policy_number`
- **Foreign Keys**: `customer_id` -> `CUSTOMERS(id)`, `agent_id` -> `AGENTS(id)`, `policy_type_id` -> `POLICY_TYPES(id)`
- **Attributes**: `coverage_amount`, `premium_amount`, `payment_frequency`, `start_date`, `expiry_date`, `status`, `created_at`, `updated_at`

### 5. `PREMIUM_PAYMENTS`
- **Primary Key**: `id` (BIGINT, AUTO_INCREMENT)
- **Unique Candidate Keys**: `payment_number`
- **Foreign Key**: `policy_id` -> `POLICIES(id)`
- **Attributes**: `amount`, `payment_date`, `payment_method`, `status`, `transaction_ref`, `notes`, `created_at`

### 6. `RENEWALS`
- **Primary Key**: `id` (BIGINT, AUTO_INCREMENT)
- **Unique Candidate Keys**: `renewal_number`
- **Foreign Keys**: `policy_id` -> `POLICIES(id)`, `processed_by_agent_id` -> `AGENTS(id)`
- **Attributes**: `renewal_date`, `previous_expiry_date`, `new_expiry_date`, `renewal_premium`, `status`, `created_at`

### 7. `CLAIMS`
- **Primary Key**: `id` (BIGINT, AUTO_INCREMENT)
- **Unique Candidate Keys**: `claim_number`
- **Foreign Keys**: `policy_id` -> `POLICIES(id)`, `customer_id` -> `CUSTOMERS(id)`
- **Attributes**: `claim_amount`, `incident_date`, `incident_description`, `claim_date`, `status`, `priority`, `risk_level`, `waiting_days`, `created_at`, `updated_at`

### 8. `CLAIM_DOCUMENTS`
- **Primary Key**: `id` (BIGINT, AUTO_INCREMENT)
- **Foreign Key**: `claim_id` -> `CLAIMS(id)`
- **Attributes**: `document_name`, `document_type`, `file_path`, `uploaded_at`

### 9. `CLAIM_ASSESSMENTS`
- **Primary Key**: `id` (BIGINT, AUTO_INCREMENT)
- **Foreign Keys**: `claim_id` (UNIQUE) -> `CLAIMS(id)`, `agent_id` -> `AGENTS(id)`
- **Attributes**: `assessed_amount`, `assessment_notes`, `assessment_date`, `recommendation`, `status`

### 10. `CLAIM_SETTLEMENTS`
- **Primary Key**: `id` (BIGINT, AUTO_INCREMENT)
- **Unique Candidate Keys**: `payment_reference`
- **Foreign Key**: `claim_id` (UNIQUE) -> `CLAIMS(id)`
- **Attributes**: `approved_amount`, `settlement_date`, `settlement_status`, `payment_method`, `notes`

---

## 2. Cardinalities & Relationships

1. **Customer 1 ---- M Policy**: A customer can hold multiple policies; each policy belongs to exactly one customer.
2. **Agent 1 ---- M Policy**: An agent can manage multiple policies; each policy is assigned to an agent.
3. **Policy_Type 1 ---- M Policy**: A policy type classifies multiple policies.
4. **Policy 1 ---- M Premium_Payment**: A policy can have multiple periodic premium payments.
5. **Policy 1 ---- M Renewal**: A policy can undergo multiple annual renewals.
6. **Policy 1 ---- M Claim**: A policy can have multiple claims filed over its term.
7. **Claim 1 ---- M Claim_Document**: A claim can include multiple supporting verification documents.
8. **Claim 1 ---- 1 Claim_Assessment**: Each claim has at most one official assessment report.
9. **Claim 1 ---- 1 Claim_Settlement**: Each claim has at most one financial settlement transaction.

---

## 3. Mermaid ER Diagram

```mermaid
erDiagram
    CUSTOMERS ||--o{ POLICIES : holds
    AGENTS ||--o{ POLICIES : manages
    POLICY_TYPES ||--o{ POLICIES : categorizes
    POLICIES ||--o{ PREMIUM_PAYMENTS : receives
    POLICIES ||--o{ RENEWALS : undergoes
    POLICIES ||--o{ CLAIMS : triggers
    CUSTOMERS ||--o{ CLAIMS : files
    CLAIMS ||--o{ CLAIM_DOCUMENTS : contains
    CLAIMS ||--o| CLAIM_ASSESSMENTS : assessed_by
    CLAIMS ||--o| CLAIM_SETTLEMENTS : settled_by

    CUSTOMERS {
        bigint id PK
        string customer_code UK
        string first_name
        string last_name
        string email UK
        string phone
    }

    AGENTS {
        bigint id PK
        string agent_code UK
        string first_name
        string last_name
        string email UK
    }

    POLICY_TYPES {
        bigint id PK
        string type_code UK
        string name
        double base_premium
    }

    POLICIES {
        bigint id PK
        string policy_number UK
        bigint customer_id FK
        bigint agent_id FK
        bigint policy_type_id FK
        double coverage_amount
        double premium_amount
        string status
    }

    CLAIMS {
        bigint id PK
        string claim_number UK
        bigint policy_id FK
        bigint customer_id FK
        double claim_amount
        string status
        string priority
    }

    CLAIM_ASSESSMENTS {
        bigint id PK
        bigint claim_id FK_UK
        bigint agent_id FK
        double assessed_amount
        string recommendation
    }

    CLAIM_SETTLEMENTS {
        bigint id PK
        bigint claim_id FK_UK
        double approved_amount
        string payment_reference UK
    }
```
