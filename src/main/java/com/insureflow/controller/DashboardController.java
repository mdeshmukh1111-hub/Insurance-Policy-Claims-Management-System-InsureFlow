package com.insureflow.controller;

import com.insureflow.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/admin")
    public ResponseEntity<Map<String, Object>> getAdminStats() {
        return ResponseEntity.ok(dashboardService.getAdminDashboardStats());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Map<String, Object>> getCustomerStats(@PathVariable Long customerId) {
        return ResponseEntity.ok(dashboardService.getCustomerDashboardStats(customerId));
    }

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<Map<String, Object>> getAgentStats(@PathVariable Long agentId) {
        return ResponseEntity.ok(dashboardService.getAgentDashboardStats(agentId));
    }
}
