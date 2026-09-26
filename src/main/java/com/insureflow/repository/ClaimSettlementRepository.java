package com.insureflow.repository;

import com.insureflow.entity.ClaimSettlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClaimSettlementRepository extends JpaRepository<ClaimSettlement, Long> {

    Optional<ClaimSettlement> findByClaimId(Long claimId);

    @Query("SELECT SUM(s.approvedAmount) FROM ClaimSettlement s WHERE s.settlementStatus = 'COMPLETED'")
    Double sumTotalSettlements();
}
