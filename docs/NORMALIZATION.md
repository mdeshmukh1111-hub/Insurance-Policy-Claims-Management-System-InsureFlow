# InsureFlow - Relational Database Normalization Report

This document explains the step-by-step normalization process applied to the **InsureFlow** database schema up to **3rd Normal Form (3NF)**.

---

## 1. First Normal Form (1NF)

### Requirement:
- Atomic values (no multi-valued or composite attributes in any column).
- Each record must have a unique identifier (Primary Key).

### Implementation in InsureFlow:
- Composite fields such as Customer Name and Customer Address are split into atomic columns:
  - `first_name`, `last_name`
  - `address`, `city`, `state`, `postal_code`
- Document lists attached to claims are NOT stored as comma-separated values in the `CLAIMS` table; instead, a separate `CLAIM_DOCUMENTS` relation is created where each document is its own row with an atomic `document_name` and `file_path`.
- Every table has a surrogate surrogate auto-increment Primary Key (`id`).

---

## 2. Second Normal Form (2NF)

### Requirement:
- Must be in 1NF.
- Elimination of Partial Functional Dependencies (No non-prime attribute should depend on a subset of a composite candidate key).

### Implementation in InsureFlow:
- All entities use single-attribute surrogate keys (`id`) or single-attribute business keys (`customer_code`, `policy_number`, `claim_number`).
- Therefore, every non-key attribute depends on the *entire* Primary Key, completely eliminating partial dependencies.
- For example, in `POLICIES`, attributes like `coverage_amount`, `premium_amount`, and `expiry_date` depend strictly on `policy_id`.

---

## 3. Third Normal Form (3NF)

### Requirement:
- Must be in 2NF.
- Elimination of Transitive Dependencies (No non-prime attribute should depend on another non-prime attribute, i.e., $X \rightarrow Y$ where neither $X$ is a super key nor $Y$ is a prime attribute).

### Functional Dependencies (FD) & Decomposition Analysis:

#### Case 1: Policy Category & Base Rates
- **Un-normalized Anti-Pattern**: Storing policy type attributes (`category`, `base_premium`, `min_coverage`, `max_coverage`, `default_term_months`) directly inside the `POLICIES` table.
- **Transitive Dependency**: `policy_id -> policy_type_name -> base_premium`
- **3NF Solution**: Decomposed into `POLICY_TYPES` relation. `POLICIES` references `policy_type_id` via foreign key.

#### Case 2: Claim Assessment & Agent Details
- **Un-normalized Anti-Pattern**: Storing assessment outcome and agent agency details inside `CLAIMS`.
- **Transitive Dependency**: `claim_id -> agent_id -> agency_name`
- **3NF Solution**: Decomposed into `CLAIM_ASSESSMENTS` and `AGENTS`. `CLAIM_ASSESSMENTS` references `claim_id` and `agent_id`.

#### Case 3: Customer Details in Policy & Claims
- **Un-normalized Anti-Pattern**: Storing customer email or phone inside `POLICIES` or `CLAIMS`.
- **Transitive Dependency**: `claim_id -> policy_id -> customer_id -> email`
- **3NF Solution**: Decomposed into `CUSTOMERS`. `POLICIES` and `CLAIMS` store foreign keys `customer_id`.

---

## Summary Table of Normalized Database Schema

| Entity | PK | Foreign Keys | Key Functional Dependencies | Normal Form |
| :--- | :--- | :--- | :--- | :--- |
| `CUSTOMERS` | `id` | None | `id -> customer_code, email, phone, city` | **3NF** |
| `AGENTS` | `id` | None | `id -> agent_code, email, agency_name` | **3NF** |
| `POLICY_TYPES` | `id` | None | `id -> type_code, base_premium, category` | **3NF** |
| `POLICIES` | `id` | `customer_id`, `agent_id`, `policy_type_id` | `id -> policy_number, coverage_amount, expiry_date` | **3NF** |
| `PREMIUM_PAYMENTS` | `id` | `policy_id` | `id -> payment_number, amount, payment_date` | **3NF** |
| `RENEWALS` | `id` | `policy_id`, `processed_by_agent_id` | `id -> renewal_number, new_expiry_date` | **3NF** |
| `CLAIMS` | `id` | `policy_id`, `customer_id` | `id -> claim_number, claim_amount, status` | **3NF** |
| `CLAIM_DOCUMENTS` | `id` | `claim_id` | `id -> claim_id, document_name, file_path` | **3NF** |
| `CLAIM_ASSESSMENTS` | `id` | `claim_id`, `agent_id` | `claim_id -> assessed_amount, recommendation` | **3NF** |
| `CLAIM_SETTLEMENTS` | `id` | `claim_id` | `claim_id -> approved_amount, payment_reference` | **3NF** |

---
Conclusion: The InsureFlow schema satisfies **3rd Normal Form (3NF)** completely, ensuring zero redundancy, data integrity, and fast relational joins.
