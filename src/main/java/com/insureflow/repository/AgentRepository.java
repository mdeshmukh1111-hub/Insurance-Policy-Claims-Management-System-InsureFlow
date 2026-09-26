package com.insureflow.repository;

import com.insureflow.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

    Optional<Agent> findByAgentCode(String agentCode);

    Optional<Agent> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByAgentCode(String agentCode);

    @Query("SELECT a FROM Agent a WHERE LOWER(a.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(a.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(a.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(a.agentCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(a.agencyName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Agent> searchAgents(@Param("keyword") String keyword);
}
