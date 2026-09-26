-- =====================================================================
-- InsureFlow Insurance System - 25+ Essential SQL Queries
-- Designed for College DBMS Project Evaluation & Viva Demonstration
-- =====================================================================

USE insureflow_db;

-- ---------------------------------------------------------------------
-- 1. BASIC SELECTION & FILTERING (WHERE, ORDER BY)
-- ---------------------------------------------------------------------

-- Query 1: Retrieve all active customers ordered alphabetically by last name
SELECT id, customer_code, CONCAT(first_name, ' ', last_name) AS full_name, email, phone, city 
FROM customers 
WHERE status = 'ACTIVE' 
ORDER BY last_name ASC, first_name ASC;

-- Query 2: Find all active insurance policies with coverage greater than $500,000
SELECT policy_number, coverage_amount, premium_amount, start_date, expiry_date 
FROM policies 
WHERE status = 'ACTIVE' AND coverage_amount > 500000 
ORDER BY coverage_amount DESC;

-- Query 3: Search claims filed with priority 'HIGH' or 'URGENT'
SELECT claim_number, claim_amount, incident_date, status, priority, risk_level 
FROM claims 
WHERE priority IN ('HIGH', 'URGENT') 
ORDER BY claim_amount DESC;

-- ---------------------------------------------------------------------
-- 2. AGGREGATIONS & GROUPING (COUNT, SUM, AVG, GROUP BY, HAVING)
-- ---------------------------------------------------------------------

-- Query 4: Total premium collected per payment method
SELECT payment_method, COUNT(*) AS transaction_count, SUM(amount) AS total_amount_collected 
FROM premium_payments 
WHERE status = 'SUCCESS' 
GROUP BY payment_method 
ORDER BY total_amount_collected DESC;

-- Query 5: Count of claims by status with total claimed amount
SELECT status, COUNT(*) AS total_claims, SUM(claim_amount) AS total_claimed_value, AVG(claim_amount) AS average_claim_value 
FROM claims 
GROUP BY status;

-- Query 6: Find agents managing more than 1 active policy (HAVING clause)
SELECT agent_id, COUNT(*) AS active_policies_managed 
FROM policies 
WHERE status = 'ACTIVE' 
GROUP BY agent_id 
HAVING COUNT(*) >= 1;

-- Query 7: Total revenue generated per policy category
SELECT pt.category, COUNT(p.id) AS policies_count, SUM(p.premium_amount) AS total_annual_revenue 
FROM policies p 
JOIN policy_types pt ON p.policy_type_id = pt.id 
GROUP BY pt.category;

-- ---------------------------------------------------------------------
-- 3. RELATIONAL JOINS (INNER JOIN, LEFT JOIN, MULTI-TABLE JOIN)
-- ---------------------------------------------------------------------

-- Query 8: Inner Join: Customer policy details with policy type & agent names
SELECT 
    p.policy_number,
    CONCAT(c.first_name, ' ', c.last_name) AS customer_name,
    c.email AS customer_email,
    pt.name AS policy_type,
    CONCAT(a.first_name, ' ', a.last_name) AS agent_name,
    p.coverage_amount,
    p.status
FROM policies p
JOIN customers c ON p.customer_id = c.id
JOIN policy_types pt ON p.policy_type_id = pt.id
LEFT JOIN agents a ON p.agent_id = a.id;

-- Query 9: Multi-table Join: Full claim life-cycle tracking
SELECT 
    cl.claim_number,
    CONCAT(c.first_name, ' ', c.last_name) AS customer_name,
    p.policy_number,
    cl.claim_amount,
    cl.status AS claim_status,
    ca.assessed_amount,
    ca.recommendation,
    cs.approved_amount AS settlement_amount,
    cs.payment_reference
FROM claims cl
JOIN customers c ON cl.customer_id = c.id
JOIN policies p ON cl.policy_id = p.id
LEFT JOIN claim_assessments ca ON cl.id = ca.claim_id
LEFT JOIN claim_settlements cs ON cl.id = cs.claim_id;

-- Query 10: Left Join: Find customers who currently have NO insurance policy
SELECT c.id, c.customer_code, c.first_name, c.last_name, c.email 
FROM customers c 
LEFT JOIN policies p ON c.id = p.customer_id 
WHERE p.id IS NULL;

-- Query 11: Left Join: Find policies that have zero filed claims
SELECT p.policy_number, p.coverage_amount, p.status 
FROM policies p 
LEFT JOIN claims cl ON p.id = cl.policy_id 
WHERE cl.id IS NULL;

-- ---------------------------------------------------------------------
-- 4. SUBQUERIES & NESTED QUERIES
-- ---------------------------------------------------------------------

-- Query 12: Subquery: Customers whose total claims exceed the average claim amount
SELECT customer_code, first_name, last_name, email 
FROM customers 
WHERE id IN (
    SELECT customer_id 
    FROM claims 
    WHERE claim_amount > (SELECT AVG(claim_amount) FROM claims)
);

-- Query 13: Subquery: Policies expiring within the next 30 days
SELECT policy_number, customer_id, expiry_date, status 
FROM policies 
WHERE expiry_date <= DATE_ADD(CURDATE(), INTERVAL 30 DAY) AND status = 'ACTIVE';

-- Query 14: Subquery: Find the highest single settlement payment record
SELECT * FROM claim_settlements 
WHERE approved_amount = (SELECT MAX(approved_amount) FROM claim_settlements);

-- ---------------------------------------------------------------------
-- 5. DATA MANIPULATION & UPDATES (INSERT, UPDATE, DELETE)
-- ---------------------------------------------------------------------

-- Query 15: Insert new customer record
INSERT INTO customers (customer_code, first_name, last_name, email, phone, city, state, status, created_at, updated_at) 
VALUES ('CUST-1005', 'Karan', 'Joshi', 'karan.j@example.com', '+91-9876001122', 'Delhi', 'NCR', 'ACTIVE', NOW(), NOW());

-- Query 16: Update policy status to RENEWAL_DUE for policies expiring within 15 days
UPDATE policies 
SET status = 'RENEWAL_DUE', updated_at = NOW() 
WHERE expiry_date <= DATE_ADD(CURDATE(), INTERVAL 15 DAY) AND status = 'ACTIVE';

-- Query 17: Update claim priority based on high claim amount
UPDATE claims 
SET priority = 'URGENT' 
WHERE claim_amount >= 100000.00 AND status = 'SUBMITTED';

-- Query 18: Delete inactive customers with no active policies
DELETE FROM customers 
WHERE status = 'INACTIVE' AND id NOT IN (SELECT DISTINCT customer_id FROM policies);

-- ---------------------------------------------------------------------
-- 6. VIEWS & ADVANCED REPORTING
-- ---------------------------------------------------------------------

-- Query 19: Create View: Active Policy Summary View
CREATE OR REPLACE VIEW vw_active_policy_summary AS
SELECT 
    p.id AS policy_id,
    p.policy_number,
    CONCAT(c.first_name, ' ', c.last_name) AS customer_name,
    c.phone AS customer_phone,
    pt.name AS policy_type_name,
    p.premium_amount,
    p.expiry_date
FROM policies p
JOIN customers c ON p.customer_id = c.id
JOIN policy_types pt ON p.policy_type_id = pt.id
WHERE p.status = 'ACTIVE';

-- Query 20: Select from Active Policy Summary View
SELECT * FROM vw_active_policy_summary WHERE expiry_date <= DATE_ADD(CURDATE(), INTERVAL 60 DAY);

-- Query 21: Create View: Claim Processing Status Dashboard
CREATE OR REPLACE VIEW vw_claim_dashboard AS
SELECT 
    c.id AS claim_id,
    c.claim_number,
    c.customer_id,
    c.claim_amount,
    c.status AS claim_status,
    c.priority,
    ca.assessed_amount,
    ca.recommendation,
    cs.payment_reference
FROM claims c
LEFT JOIN claim_assessments ca ON c.id = ca.claim_id
LEFT JOIN claim_settlements cs ON c.id = cs.claim_id;

-- Query 22: Select pending claims from View
SELECT * FROM vw_claim_dashboard WHERE claim_status IN ('SUBMITTED', 'UNDER_REVIEW');

-- Query 23: Agent performance metrics
SELECT 
    a.agent_code,
    CONCAT(a.first_name, ' ', a.last_name) AS agent_name,
    COUNT(p.id) AS policies_sold,
    COALESCE(SUM(p.premium_amount), 0) AS total_premium_value
FROM agents a
LEFT JOIN policies p ON a.id = p.agent_id
GROUP BY a.id, a.agent_code, a.first_name, a.last_name;

-- Query 24: Monthly premium collection report
SELECT 
    DATE_FORMAT(payment_date, '%Y-%m') AS payment_month,
    COUNT(*) AS total_payments,
    SUM(amount) AS total_collected
FROM premium_payments
WHERE status = 'SUCCESS'
GROUP BY DATE_FORMAT(payment_date, '%Y-%m')
ORDER BY payment_month DESC;

-- Query 25: Financial Summary: Total Premiums vs Total Settlements Ratio
SELECT 
    (SELECT COALESCE(SUM(amount), 0) FROM premium_payments WHERE status = 'SUCCESS') AS total_premiums_collected,
    (SELECT COALESCE(SUM(approved_amount), 0) FROM claim_settlements WHERE settlement_status = 'COMPLETED') AS total_claims_settled,
    ((SELECT COALESCE(SUM(approved_amount), 0) FROM claim_settlements WHERE settlement_status = 'COMPLETED') / 
     (SELECT COALESCE(SUM(amount), 0) FROM premium_payments WHERE status = 'SUCCESS') * 100) AS loss_ratio_percentage;
