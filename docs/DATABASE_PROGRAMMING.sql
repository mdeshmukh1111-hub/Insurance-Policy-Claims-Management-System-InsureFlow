-- =====================================================================
-- InsureFlow Insurance System - MySQL Stored Procedures, Functions & Triggers
-- Designed for College DBMS Evaluation & PL/SQL Feature Demonstration
-- =====================================================================

USE insureflow_db;

DELIMITER $$

-- ---------------------------------------------------------------------
-- FEATURE 1: STORED PROCEDURE
-- Name: sp_process_claim_settlement
-- Purpose: Atomically updates claim status to 'SETTLED' and creates a settlement record.
-- ---------------------------------------------------------------------
DROP PROCEDURE IF EXISTS sp_process_claim_settlement$$

CREATE PROCEDURE sp_process_claim_settlement(
    IN p_claim_id BIGINT,
    IN p_approved_amount DOUBLE,
    IN p_payment_method VARCHAR(50),
    IN p_notes VARCHAR(500),
    OUT p_settlement_ref VARCHAR(100)
)
BEGIN
    DECLARE current_claim_status VARCHAR(30);
    
    -- Check claim status
    SELECT status INTO current_claim_status FROM claims WHERE id = p_claim_id;
    
    IF current_claim_status IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Claim ID not found.';
    ELSEIF current_claim_status != 'APPROVED' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Only APPROVED claims can be settled.';
    ELSE
        -- Generate unique settlement reference
        SET p_settlement_ref = CONCAT('SETTL-', UPPER(HEX(RANDOM_BYTES(4))));
        
        -- Insert into claim_settlements
        INSERT INTO claim_settlements (claim_id, approved_amount, settlement_date, payment_reference, settlement_status, payment_method, notes)
        VALUES (p_claim_id, p_approved_amount, NOW(), p_settlement_ref, 'COMPLETED', p_payment_method, p_notes);
        
        -- Update claim status to SETTLED
        UPDATE claims SET status = 'SETTLED', updated_at = NOW() WHERE id = p_claim_id;
        
        SELECT CONCAT('Claim ', p_claim_id, ' successfully settled with Ref: ', p_settlement_ref) AS result_message;
    END IF;
END$$


-- ---------------------------------------------------------------------
-- FEATURE 2: STORED FUNCTION
-- Name: fn_calculate_total_customer_premium
-- Purpose: Returns total cumulative premium successfully paid by a customer ID.
-- ---------------------------------------------------------------------
DROP FUNCTION IF EXISTS fn_calculate_total_customer_premium$$

CREATE FUNCTION fn_calculate_total_customer_premium(p_customer_id BIGINT) 
RETURNS DOUBLE
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE total_paid DOUBLE DEFAULT 0.0;
    
    SELECT COALESCE(SUM(pp.amount), 0.0) INTO total_paid
    FROM premium_payments pp
    JOIN policies p ON pp.policy_id = p.id
    WHERE p.customer_id = p_customer_id AND pp.status = 'SUCCESS';
    
    RETURN total_paid;
END$$


-- ---------------------------------------------------------------------
-- FEATURE 3: TRIGGER
-- Name: trg_update_policy_status_on_renewal
-- Purpose: Automatically updates Policy status to 'ACTIVE' and sets new expiry date
--          whenever a new Renewal record is inserted.
-- ---------------------------------------------------------------------
DROP TRIGGER IF EXISTS trg_update_policy_status_on_renewal$$

CREATE TRIGGER trg_update_policy_status_on_renewal
AFTER INSERT ON renewals
FOR EACH ROW
BEGIN
    UPDATE policies 
    SET expiry_date = NEW.new_expiry_date,
        status = 'ACTIVE',
        updated_at = NOW()
    WHERE id = NEW.policy_id;
END$$


-- ---------------------------------------------------------------------
-- FEATURE 4: STORED PROCEDURE WITH CURSOR
-- Name: sp_generate_agent_statistics
-- Purpose: Iterates through each active agent using a Cursor and calculates total
--          policies sold, active policies count, and cumulative premium revenue.
-- ---------------------------------------------------------------------
DROP PROCEDURE IF EXISTS sp_generate_agent_statistics$$

CREATE PROCEDURE sp_generate_agent_statistics()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE v_agent_id BIGINT;
    DECLARE v_agent_code VARCHAR(30);
    DECLARE v_agent_name VARCHAR(100);
    DECLARE v_policies_count INT;
    DECLARE v_total_premium DOUBLE;
    
    -- Declare Cursor for iterating through active agents
    DECLARE agent_cursor CURSOR FOR 
        SELECT id, agent_code, CONCAT(first_name, ' ', last_name) FROM agents WHERE status = 'ACTIVE';
        
    -- Declare NOT FOUND handler
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    -- Create temporary table to store generated statistics
    CREATE TEMPORARY TABLE IF NOT EXISTS temp_agent_stats (
        agent_code VARCHAR(30),
        agent_name VARCHAR(100),
        total_policies INT,
        total_premium_value DOUBLE
    );
    
    DELETE FROM temp_agent_stats;
    
    OPEN agent_cursor;
    
    read_loop: LOOP
        FETCH agent_cursor INTO v_agent_id, v_agent_code, v_agent_name;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        -- Calculate metrics for current agent
        SELECT COUNT(*), COALESCE(SUM(premium_amount), 0.0) 
        INTO v_policies_count, v_total_premium
        FROM policies 
        WHERE agent_id = v_agent_id;
        
        -- Insert into temporary results table
        INSERT INTO temp_agent_stats VALUES (v_agent_code, v_agent_name, v_policies_count, v_total_premium);
    END LOOP;
    
    CLOSE agent_cursor;
    
    -- Return compiled statistics
    SELECT * FROM temp_agent_stats ORDER BY total_premium_value DESC;
END$$

DELIMITER ;

-- =====================================================================
-- Verification & Testing Queries for PL/SQL Features:
-- =====================================================================

-- Test Function:
-- SELECT fn_calculate_total_customer_premium(1) AS total_customer_1_premium;

-- Test Cursor Procedure:
-- CALL sp_generate_agent_statistics();
