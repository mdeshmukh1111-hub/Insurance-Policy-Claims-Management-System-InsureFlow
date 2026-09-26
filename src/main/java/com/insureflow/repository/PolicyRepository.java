package com.insureflow.repository;

import com.insureflow.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

    Optional<Policy> findByPolicyNumber(String policyNumber);

    List<Policy> findByCustomerId(Long customerId);

    List<Policy> findByAgentId(Long agentId);

    List<Policy> findByStatus(String status);

    long countByStatus(String status);

    @Query("SELECT p FROM Policy p WHERE p.expiryDate <= :date AND p.status = 'ACTIVE'")
    List<Policy> findPoliciesExpiringBefore(@Param("date") LocalDate date);

    @Query("SELECT p FROM Policy p WHERE " +
           "(:keyword IS NULL OR LOWER(p.policyNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.customer.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.customer.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:status IS NULL OR p.status = :status) AND " +
           "(:categoryId IS NULL OR p.policyType.id = :categoryId)")
    List<Policy> searchPolicies(@Param("keyword") String keyword, @Param("status") String status, @Param("categoryId") Long categoryId);
}
