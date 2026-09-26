/* =====================================================================
   InsureFlow - REST API Client
   Communicates with Spring Boot Backend Endpoints on Single Port (8080)
   ===================================================================== */

const API_BASE = '/api';

const API = {
    // Customers
    async getCustomers(search = '') {
        const url = search ? `${API_BASE}/customers?search=${encodeURIComponent(search)}` : `${API_BASE}/customers`;
        const res = await fetch(url);
        return res.json();
    },
    async createCustomer(data) {
        const res = await fetch(`${API_BASE}/customers`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!res.ok) throw new Error((await res.json()).message || 'Failed to create customer');
        return res.json();
    },
    async updateCustomer(id, data) {
        const res = await fetch(`${API_BASE}/customers/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!res.ok) throw new Error((await res.json()).message || 'Failed to update customer');
        return res.json();
    },
    async deleteCustomer(id) {
        const res = await fetch(`${API_BASE}/customers/${id}`, { method: 'DELETE' });
        if (!res.ok) throw new Error('Failed to delete customer');
    },

    // Agents
    async getAgents(search = '') {
        const url = search ? `${API_BASE}/agents?search=${encodeURIComponent(search)}` : `${API_BASE}/agents`;
        const res = await fetch(url);
        return res.json();
    },
    async createAgent(data) {
        const res = await fetch(`${API_BASE}/agents`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!res.ok) throw new Error((await res.json()).message || 'Failed to create agent');
        return res.json();
    },
    async updateAgent(id, data) {
        const res = await fetch(`${API_BASE}/agents/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!res.ok) throw new Error((await res.json()).message || 'Failed to update agent');
        return res.json();
    },
    async deleteAgent(id) {
        const res = await fetch(`${API_BASE}/agents/${id}`, { method: 'DELETE' });
        if (!res.ok) throw new Error('Failed to delete agent');
    },

    // Policy Types
    async getPolicyTypes(category = '') {
        const url = category ? `${API_BASE}/policy-types?category=${encodeURIComponent(category)}` : `${API_BASE}/policy-types`;
        const res = await fetch(url);
        return res.json();
    },
    async createPolicyType(data) {
        const res = await fetch(`${API_BASE}/policy-types`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!res.ok) throw new Error((await res.json()).message || 'Failed to create policy type');
        return res.json();
    },

    // Policies
    async getPolicies(params = {}) {
        const query = new URLSearchParams(params).toString();
        const url = query ? `${API_BASE}/policies?${query}` : `${API_BASE}/policies`;
        const res = await fetch(url);
        return res.json();
    },
    async createPolicy(data) {
        const res = await fetch(`${API_BASE}/policies`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!res.ok) throw new Error((await res.json()).message || 'Failed to create policy');
        return res.json();
    },
    async updatePolicyStatus(id, status) {
        const res = await fetch(`${API_BASE}/policies/${id}/status?status=${status}`, { method: 'PATCH' });
        if (!res.ok) throw new Error('Failed to update policy status');
        return res.json();
    },

    // Premium Payments
    async getPayments(params = {}) {
        const query = new URLSearchParams(params).toString();
        const url = query ? `${API_BASE}/payments?${query}` : `${API_BASE}/payments`;
        const res = await fetch(url);
        return res.json();
    },
    async recordPayment(data) {
        const res = await fetch(`${API_BASE}/payments`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!res.ok) throw new Error((await res.json()).message || 'Failed to record payment');
        return res.json();
    },

    // Renewals
    async getRenewals(params = {}) {
        const query = new URLSearchParams(params).toString();
        const url = query ? `${API_BASE}/renewals?${query}` : `${API_BASE}/renewals`;
        const res = await fetch(url);
        return res.json();
    },
    async processRenewal(data) {
        const res = await fetch(`${API_BASE}/renewals`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!res.ok) throw new Error((await res.json()).message || 'Failed to process renewal');
        return res.json();
    },

    // Claims
    async getClaims(params = {}) {
        const query = new URLSearchParams(params).toString();
        const url = query ? `${API_BASE}/claims?${query}` : `${API_BASE}/claims`;
        const res = await fetch(url);
        return res.json();
    },
    async submitClaim(data) {
        const res = await fetch(`${API_BASE}/claims`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!res.ok) throw new Error((await res.json()).message || 'Failed to submit claim');
        return res.json();
    },

    // Claim Assessment & Settlement
    async assessClaim(data) {
        const res = await fetch(`${API_BASE}/claim-assessments`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!res.ok) throw new Error((await res.json()).message || 'Failed to assess claim');
        return res.json();
    },
    async settleClaim(data) {
        const res = await fetch(`${API_BASE}/claim-settlements`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!res.ok) throw new Error((await res.json()).message || 'Failed to settle claim');
        return res.json();
    },

    // Dashboard Stats
    async getAdminDashboardStats() {
        const res = await fetch(`${API_BASE}/dashboard/admin`);
        return res.json();
    },
    async getCustomerDashboardStats(customerId) {
        const res = await fetch(`${API_BASE}/dashboard/customer/${customerId}`);
        return res.json();
    },
    async getAgentDashboardStats(agentId) {
        const res = await fetch(`${API_BASE}/dashboard/agent/${agentId}`);
        return res.json();
    },

    // DSA Endpoints
    async runDsaTest(concept, params = {}) {
        const query = new URLSearchParams(params).toString();
        const res = await fetch(`${API_BASE}/dsa/${concept}?${query}`);
        return res.json();
    },

    // Chatbot Endpoint
    async sendChatMessage(data) {
        const res = await fetch(`${API_BASE}/chat`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!res.ok) throw new Error('Failed to get chatbot response');
        return res.json();
    }
};

