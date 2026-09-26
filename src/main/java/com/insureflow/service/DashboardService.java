package com.insureflow.service;

import java.util.Map;

public interface DashboardService {
    Map<String, Object> getAdminDashboardStats();
    Map<String, Object> getCustomerDashboardStats(Long customerId);
    Map<String, Object> getAgentDashboardStats(Long agentId);
}
