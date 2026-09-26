package com.insureflow.repository;

import com.insureflow.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    Optional<Claim> findByClaimNumber(String claimNumber);

    List<Claim> findByCustomerId(Long customerId);

    List<Claim> findByPolicyId(Long policyId);

    List<Claim> findByPolicyAgentId(Long agentId);

    List<Claim> findByStatus(String status);

    long countByStatus(String status);

    @Query("SELECT c FROM Claim c WHERE " +
           "(:keyword IS NULL OR LOWER(c.claimNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.policy.policyNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.customer.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.customer.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:status IS NULL OR c.status = :status) AND " +
           "(:priority IS NULL OR c.priority = :priority)")
    List<Claim> searchClaims(@Param("keyword") String keyword, @Param("status") String status, @Param("priority") String priority);
}
