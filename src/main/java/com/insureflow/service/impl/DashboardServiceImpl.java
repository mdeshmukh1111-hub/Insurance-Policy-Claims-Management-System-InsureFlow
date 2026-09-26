package com.insureflow.service.impl;

import com.insureflow.entity.Claim;
import com.insureflow.entity.Policy;
import com.insureflow.repository.*;
import com.insureflow.service.DashboardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final CustomerRepository customerRepository;
    private final AgentRepository agentRepository;
    private final PolicyRepository policyRepository;
    private final ClaimRepository claimRepository;
    private final PremiumPaymentRepository premiumPaymentRepository;
    private final ClaimSettlementRepository claimSettlementRepository;

    public DashboardServiceImpl(CustomerRepository customerRepository, AgentRepository agentRepository, PolicyRepository policyRepository, ClaimRepository claimRepository, PremiumPaymentRepository premiumPaymentRepository, ClaimSettlementRepository claimSettlementRepository) {
        this.customerRepository = customerRepository;
        this.agentRepository = agentRepository;
        this.policyRepository = policyRepository;
        this.claimRepository = claimRepository;
        this.premiumPaymentRepository = premiumPaymentRepository;
        this.claimSettlementRepository = claimSettlementRepository;
    }

    @Override
    public Map<String, Object> getAdminDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalCustomers", customerRepository.count());
        stats.put("totalAgents", agentRepository.count());
        stats.put("activePolicies", policyRepository.countByStatus("ACTIVE"));
        stats.put("expiredPolicies", policyRepository.countByStatus("EXPIRED"));
        stats.put("pendingClaims", claimRepository.countByStatus("SUBMITTED") + claimRepository.countByStatus("UNDER_REVIEW"));
        stats.put("approvedClaims", claimRepository.countByStatus("APPROVED") + claimRepository.countByStatus("SETTLED"));

        Double totalCollected = premiumPaymentRepository.sumTotalPremiumCollected();
        stats.put("totalPremiumCollected", totalCollected != null ? totalCollected : 0.0);

        Double totalSettled = claimSettlementRepository.sumTotalSettlements();
        stats.put("totalClaimSettlementAmount", totalSettled != null ? totalSettled : 0.0);

        return stats;
    }

    @Override
    public Map<String, Object> getCustomerDashboardStats(Long customerId) {
        Map<String, Object> stats = new HashMap<>();

        List<Policy> customerPolicies = policyRepository.findByCustomerId(customerId);
        List<Claim> customerClaims = claimRepository.findByCustomerId(customerId);

        long activeCount = customerPolicies.stream().filter(p -> "ACTIVE".equalsIgnoreCase(p.getStatus())).count();
        long renewalDueCount = customerPolicies.stream().filter(p -> "RENEWAL_DUE".equalsIgnoreCase(p.getStatus()) || p.getExpiryDate().isBefore(LocalDate.now().plusDays(30))).count();

        double premiumDue = customerPolicies.stream()
                .filter(p -> "PENDING".equalsIgnoreCase(p.getStatus()) || "RENEWAL_DUE".equalsIgnoreCase(p.getStatus()))
                .mapToDouble(Policy::getPremiumAmount)
                .sum();

        long pendingClaimsCount = customerClaims.stream()
                .filter(c -> "SUBMITTED".equalsIgnoreCase(c.getStatus()) || "UNDER_REVIEW".equalsIgnoreCase(c.getStatus()))
                .count();

        stats.put("totalPolicies", customerPolicies.size());
        stats.put("activePolicies", activeCount);
        stats.put("upcomingRenewals", renewalDueCount);
        stats.put("premiumDue", premiumDue);
        stats.put("totalClaims", customerClaims.size());
        stats.put("pendingClaims", pendingClaimsCount);

        return stats;
    }

    @Override
    public Map<String, Object> getAgentDashboardStats(Long agentId) {
        Map<String, Object> stats = new HashMap<>();

        List<Policy> agentPolicies = policyRepository.findByAgentId(agentId);
        List<Claim> agentClaims = claimRepository.findByPolicyAgentId(agentId);

        long uniqueCustomers = agentPolicies.stream()
                .map(p -> p.getCustomer().getId())
                .distinct()
                .count();

        long activePolicies = agentPolicies.stream().filter(p -> "ACTIVE".equalsIgnoreCase(p.getStatus())).count();
        long pendingRenewals = agentPolicies.stream().filter(p -> "RENEWAL_DUE".equalsIgnoreCase(p.getStatus()) || p.getExpiryDate().isBefore(LocalDate.now().plusDays(30))).count();
        long pendingClaims = agentClaims.stream().filter(c -> "SUBMITTED".equalsIgnoreCase(c.getStatus()) || "UNDER_REVIEW".equalsIgnoreCase(c.getStatus())).count();

        stats.put("assignedCustomers", uniqueCustomers);
        stats.put("activePolicies", activePolicies);
        stats.put("pendingRenewals", pendingRenewals);
        stats.put("pendingClaims", pendingClaims);

        return stats;
    }
}
