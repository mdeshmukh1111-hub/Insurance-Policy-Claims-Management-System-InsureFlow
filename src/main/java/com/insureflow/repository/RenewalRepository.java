package com.insureflow.repository;

import com.insureflow.entity.Renewal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RenewalRepository extends JpaRepository<Renewal, Long> {

    Optional<Renewal> findByRenewalNumber(String renewalNumber);

    List<Renewal> findByPolicyId(Long policyId);

    List<Renewal> findByPolicyCustomerId(Long customerId);

    List<Renewal> findByPolicyAgentId(Long agentId);

    List<Renewal> findByStatus(String status);
}
