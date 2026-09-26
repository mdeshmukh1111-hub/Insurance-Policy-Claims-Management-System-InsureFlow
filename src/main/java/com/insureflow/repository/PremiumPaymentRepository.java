package com.insureflow.repository;

import com.insureflow.entity.PremiumPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PremiumPaymentRepository extends JpaRepository<PremiumPayment, Long> {

    Optional<PremiumPayment> findByPaymentNumber(String paymentNumber);

    List<PremiumPayment> findByPolicyId(Long policyId);

    List<PremiumPayment> findByPolicyCustomerId(Long customerId);

    @Query("SELECT SUM(p.amount) FROM PremiumPayment p WHERE p.status = 'SUCCESS'")
    Double sumTotalPremiumCollected();

    @Query("SELECT p FROM PremiumPayment p WHERE " +
           "(:keyword IS NULL OR LOWER(p.paymentNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.policy.policyNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.policy.customer.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<PremiumPayment> searchPayments(@Param("keyword") String keyword);
}
