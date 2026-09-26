/* =====================================================================
   InsureFlow - Application Logic & Single-Page Router (Vanilla JS)
   ===================================================================== */

let currentRole = 'ADMIN'; // ADMIN, CUSTOMER, AGENT
let activeTab = 'dashboard';
let currentCustomers = [];
let currentAgents = [];
let currentPolicies = [];

document.addEventListener('DOMContentLoaded', () => {
    initApp();
});

async function initApp() {
    setupRoleSwitcher();
    setupNavEvents();
    await loadActiveUserDropdowns();
    renderActiveTab();
}

function setupRoleSwitcher() {
    const pills = document.querySelectorAll('.role-pill');
    pills.forEach(pill => {
        pill.addEventListener('click', (e) => {
            pills.forEach(p => p.classList.remove('active'));
            pill.classList.add('active');
            currentRole = pill.dataset.role;
            updateNavForRole();
            renderActiveTab();
            showToast(`Switched active view to ${currentRole} Mode`);
        });
    });
}

function updateNavForRole() {
    const navButtons = document.querySelectorAll('.nav-item button');
    navButtons.forEach(btn => {
        const tab = btn.dataset.tab;
        if (currentRole === 'CUSTOMER') {
            if (['customers', 'agents', 'policy-types'].includes(tab)) {
                btn.parentElement.style.display = 'none';
            } else {
                btn.parentElement.style.display = 'inline-block';
            }
        } else if (currentRole === 'AGENT') {
            if (['agents', 'policy-types'].includes(tab)) {
                btn.parentElement.style.display = 'none';
            } else {
                btn.parentElement.style.display = 'inline-block';
            }
        } else {
            btn.parentElement.style.display = 'inline-block';
        }
    });

    if (currentRole === 'CUSTOMER' && ['customers', 'agents', 'policy-types'].includes(activeTab)) {
        activeTab = 'dashboard';
    }
}

function setupNavEvents() {
    const navButtons = document.querySelectorAll('.nav-item button');
    navButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            navButtons.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
            activeTab = btn.dataset.tab;
            renderActiveTab();
        });
    });
}

async function loadActiveUserDropdowns() {
    try {
        currentCustomers = await API.getCustomers();
        currentAgents = await API.getAgents();

        const custSelect = document.getElementById('active-customer-select');
        if (custSelect && currentCustomers.length > 0) {
            custSelect.innerHTML = currentCustomers.map(c => `<option value="${c.id}">${c.firstName} ${c.lastName} (${c.customerCode})</option>`).join('');
        }

        const agentSelect = document.getElementById('active-agent-select');
        if (agentSelect && currentAgents.length > 0) {
            agentSelect.innerHTML = currentAgents.map(a => `<option value="${a.id}">${a.firstName} ${a.lastName} (${a.agentCode})</option>`).join('');
        }
    } catch (err) {
        console.error('Failed loading dropdowns:', err);
    }
}

function getSelectedCustomerId() {
    const select = document.getElementById('active-customer-select');
    return select ? select.value : 1;
}

function getSelectedAgentId() {
    const select = document.getElementById('active-agent-select');
    return select ? select.value : 1;
}

async function renderActiveTab() {
    const content = document.getElementById('app-content');
    content.innerHTML = '<div style="text-align: center; padding: 3rem;"><p>Loading data...</p></div>';

    try {
        switch (activeTab) {
            case 'dashboard':
                await renderDashboardView(content);
                break;
            case 'customers':
                await renderCustomersView(content);
                break;
            case 'agents':
                await renderAgentsView(content);
                break;
            case 'policies':
                await renderPoliciesView(content);
                break;
            case 'claims':
                await renderClaimsView(content);
                break;
            case 'payments':
                await renderPaymentsView(content);
                break;
            case 'renewals':
                await renderRenewalsView(content);
                break;
            default:
                await renderDashboardView(content);
        }
    } catch (err) {
        content.innerHTML = `<div style="color: var(--accent-rose); padding: 2rem;">Error rendering view: ${err.message}</div>`;
    }
}

/* 1. DASHBOARD VIEW */
async function renderDashboardView(container) {
    let stats = {};
    if (currentRole === 'ADMIN') {
        stats = await API.getAdminDashboardStats();
    } else if (currentRole === 'CUSTOMER') {
        stats = await API.getCustomerDashboardStats(getSelectedCustomerId());
    } else {
        stats = await API.getAgentDashboardStats(getSelectedAgentId());
    }

    let html = `
        <div class="page-header">
            <div>
                <h1 class="page-title">${t('dash_title', 'Dashboard Overview')} (${t('role_' + currentRole.toLowerCase(), currentRole)})</h1>
                <p class="page-subtitle">${t('dash_subtitle', 'Real-time overview of key metrics, policy statuses, and claims workflow')}</p>
            </div>
            <div>
                <button class="btn btn-primary" onclick="openCreatePolicyModal()">${t('btn_issue_policy', '+ Issue Policy')}</button>
            </div>
        </div>
        <div class="stats-grid">
    `;

    if (currentRole === 'ADMIN') {
        html += `
            <div class="stat-card">
                <div class="stat-label">${t('kpi_total_cust', 'Total Customers')}</div>
                <div class="stat-value">${stats.totalCustomers || 0}</div>
            </div>
            <div class="stat-card teal">
                <div class="stat-label">${t('kpi_active_agt', 'Active Agents')}</div>
                <div class="stat-value">${stats.totalAgents || 0}</div>
            </div>
            <div class="stat-card amber">
                <div class="stat-label">${t('kpi_active_pol', 'Active Policies')}</div>
                <div class="stat-value">${stats.activePolicies || 0}</div>
            </div>
            <div class="stat-card rose">
                <div class="stat-label">${t('kpi_pending_clm', 'Pending Claims')}</div>
                <div class="stat-value">${stats.pendingClaims || 0}</div>
            </div>
            <div class="stat-card purple">
                <div class="stat-label">${t('kpi_prem_coll', 'Premium Collected')}</div>
                <div class="stat-value">₹${(stats.totalPremiumCollected || 0).toLocaleString()}</div>
            </div>
            <div class="stat-card">
                <div class="stat-label">${t('kpi_settle_disb', 'Settlement Disbursed')}</div>
                <div class="stat-value">₹${(stats.totalClaimSettlementAmount || 0).toLocaleString()}</div>
            </div>
        `;
    } else if (currentRole === 'CUSTOMER') {
        html += `
            <div class="stat-card">
                <div class="stat-label">${t('kpi_active_pol', 'My Total Policies')}</div>
                <div class="stat-value">${stats.totalPolicies || 0}</div>
            </div>
            <div class="stat-card teal">
                <div class="stat-label">${t('tbl_coverage', 'Active Coverage')}</div>
                <div class="stat-value">${stats.activePolicies || 0}</div>
            </div>
            <div class="stat-card amber">
                <div class="stat-label">${t('tbl_premium', 'Premium Due')}</div>
                <div class="stat-value">₹${(stats.premiumDue || 0).toLocaleString()}</div>
            </div>
            <div class="stat-card rose">
                <div class="stat-label">${t('kpi_pending_clm', 'Active Claims')}</div>
                <div class="stat-value">${stats.pendingClaims || 0}</div>
            </div>
        `;
    } else {
        html += `
            <div class="stat-card">
                <div class="stat-label">${t('kpi_total_cust', 'Assigned Customers')}</div>
                <div class="stat-value">${stats.assignedCustomers || 0}</div>
            </div>
            <div class="stat-card teal">
                <div class="stat-label">${t('kpi_active_pol', 'Active Policies')}</div>
                <div class="stat-value">${stats.activePolicies || 0}</div>
            </div>
            <div class="stat-card amber">
                <div class="stat-label">${t('nav_renewals', 'Pending Renewals')}</div>
                <div class="stat-value">${stats.pendingRenewals || 0}</div>
            </div>
            <div class="stat-card rose">
                <div class="stat-label">${t('kpi_pending_clm', 'Claims Under Review')}</div>
                <div class="stat-value">${stats.pendingClaims || 0}</div>
            </div>
        `;
    }

    html += `</div>`;

    // Add recent policies table
    const policies = await API.getPolicies();
    html += `
        <h2 style="font-size: 1.2rem; margin-bottom: 1rem;">${t('nav_policies', 'Recent Insurance Policies')}</h2>
        <div class="table-container">
            <table class="data-table">
                <thead>
                    <tr>
                        <th>${t('tbl_policy_num', 'Policy #')}</th>
                        <th>${t('tbl_customer', 'Customer')}</th>
                        <th>${t('tbl_type', 'Policy Type')}</th>
                        <th>${t('tbl_coverage', 'Coverage')}</th>
                        <th>${t('tbl_premium', 'Premium')}</th>
                        <th>${t('tbl_expiry', 'Expiry Date')}</th>
                        <th>${t('tbl_status', 'Status')}</th>
                        <th>${t('tbl_actions', 'Actions')}</th>
                    </tr>
                </thead>
                <tbody>
    `;

    policies.forEach(p => {
        html += `
            <tr>
                <td><strong>${p.policyNumber}</strong></td>
                <td>${p.customer ? p.customer.firstName + ' ' + p.customer.lastName : 'N/A'}</td>
                <td>${p.policyType ? p.policyType.name : 'N/A'}</td>
                <td>₹${p.coverageAmount.toLocaleString()}</td>
                <td>₹${p.premiumAmount.toLocaleString()}</td>
                <td>${p.expiryDate}</td>
                <td><span class="badge badge-${p.status.toLowerCase()}">${p.status}</span></td>
                <td>
                    <button class="btn btn-secondary btn-sm" onclick="openRecordPaymentModal(${p.id})">${t('btn_pay', 'Pay')}</button>
                    <button class="btn btn-primary btn-sm" onclick="openSubmitClaimModal(${p.id})">${t('btn_file_claim', 'Claim')}</button>
                </td>
            </tr>
        `;
    });

    html += `</tbody></table></div>`;
    container.innerHTML = html;
}

/* 2. CUSTOMERS VIEW */
/* 2. CUSTOMERS VIEW */
async function renderCustomersView(container) {
    const customers = await API.getCustomers();
    let html = `
        <div class="page-header">
            <div>
                <h1 class="page-title">${t('cust_title', 'Customer Management')}</h1>
                <p class="page-subtitle">${t('cust_subtitle', 'Register and manage insured policyholders')}</p>
            </div>
            <button class="btn btn-primary" onclick="openAddCustomerModal()">${t('btn_add_customer', '+ Register Customer')}</button>
        </div>
        <div class="controls-bar">
            <div class="search-group">
                <input type="text" id="customer-search-input" class="search-input" placeholder="Search by name, email, or code..." onkeyup="filterCustomers()">
            </div>
        </div>
        <div class="table-container">
            <table class="data-table" id="customers-table">
                <thead>
                    <tr>
                        <th>${t('tbl_code', 'Code')}</th>
                        <th>${t('tbl_name', 'Full Name')}</th>
                        <th>${t('tbl_email', 'Email')}</th>
                        <th>${t('tbl_phone', 'Phone')}</th>
                        <th>${t('tbl_city', 'City / State')}</th>
                        <th>${t('tbl_status', 'Status')}</th>
                        <th>${t('tbl_actions', 'Actions')}</th>
                    </tr>
                </thead>
                <tbody>
    `;

    customers.forEach(c => {
        html += `
            <tr>
                <td><strong>${c.customerCode}</strong></td>
                <td>${c.firstName} ${c.lastName}</td>
                <td>${c.email}</td>
                <td>${c.phone}</td>
                <td>${c.city || 'N/A'}, ${c.state || ''}</td>
                <td><span class="badge badge-${c.status.toLowerCase()}">${c.status}</span></td>
                <td>
                    <button class="btn btn-secondary btn-sm" onclick="deleteCustomer(${c.id})">${t('btn_delete', 'Delete')}</button>
                </td>
            </tr>
        `;
    });

    html += `</tbody></table></div>`;
    container.innerHTML = html;
}

async function filterCustomers() {
    const val = document.getElementById('customer-search-input').value;
    const customers = await API.getCustomers(val);
    const tbody = document.querySelector('#customers-table tbody');
    tbody.innerHTML = customers.map(c => `
        <tr>
            <td><strong>${c.customerCode}</strong></td>
            <td>${c.firstName} ${c.lastName}</td>
            <td>${c.email}</td>
            <td>${c.phone}</td>
            <td>${c.city || 'N/A'}, ${c.state || ''}</td>
            <td><span class="badge badge-${c.status.toLowerCase()}">${c.status}</span></td>
            <td><button class="btn btn-secondary btn-sm" onclick="deleteCustomer(${c.id})">${t('btn_delete', 'Delete')}</button></td>
        </tr>
    `).join('');
}

/* 3. AGENTS VIEW */
async function renderAgentsView(container) {
    const agents = await API.getAgents();
    let html = `
        <div class="page-header">
            <div>
                <h1 class="page-title">${t('agt_title', 'Agent Directory')}</h1>
                <p class="page-subtitle">${t('agt_subtitle', 'Manage insurance brokers and underwriters')}</p>
            </div>
            <button class="btn btn-primary" onclick="openAddAgentModal()">${t('btn_add_agent', '+ Add Agent')}</button>
        </div>
        <div class="table-container">
            <table class="data-table">
                <thead>
                    <tr>
                        <th>${t('tbl_code', 'Agent Code')}</th>
                        <th>${t('tbl_name', 'Name')}</th>
                        <th>${t('tbl_email', 'Email')}</th>
                        <th>${t('tbl_phone', 'Phone')}</th>
                        <th>${t('tbl_agency', 'Agency Name')}</th>
                        <th>${t('tbl_status', 'Status')}</th>
                        <th>${t('tbl_actions', 'Actions')}</th>
                    </tr>
                </thead>
                <tbody>
    `;

    agents.forEach(a => {
        html += `
            <tr>
                <td><strong>${a.agentCode}</strong></td>
                <td>${a.firstName} ${a.lastName}</td>
                <td>${a.email}</td>
                <td>${a.phone}</td>
                <td>${a.agencyName || 'Independent'}</td>
                <td><span class="badge badge-${a.status.toLowerCase()}">${a.status}</span></td>
                <td><button class="btn btn-secondary btn-sm" onclick="deleteAgent(${a.id})">${t('btn_delete', 'Delete')}</button></td>
            </tr>
        `;
    });

    html += `</tbody></table></div>`;
    container.innerHTML = html;
}

/* 4. POLICIES VIEW */
async function renderPoliciesView(container) {
    const policies = await API.getPolicies();
    let html = `
        <div class="page-header">
            <div>
                <h1 class="page-title">${t('pol_title', 'Policy Lifecycle Management')}</h1>
                <p class="page-subtitle">${t('pol_subtitle', 'Create, monitor, and update active policy contracts')}</p>
            </div>
            <button class="btn btn-primary" onclick="openCreatePolicyModal()">${t('btn_issue_policy', '+ Issue Policy')}</button>
        </div>
        <div class="table-container">
            <table class="data-table">
                <thead>
                    <tr>
                        <th>${t('tbl_policy_num', 'Policy #')}</th>
                        <th>${t('tbl_customer', 'Customer')}</th>
                        <th>${t('tbl_agent', 'Agent')}</th>
                        <th>${t('tbl_category', 'Category')}</th>
                        <th>${t('tbl_coverage', 'Coverage')}</th>
                        <th>${t('tbl_premium', 'Premium')}</th>
                        <th>${t('tbl_expiry', 'Expiry')}</th>
                        <th>${t('tbl_status', 'Status')}</th>
                        <th>${t('tbl_actions', 'Actions')}</th>
                    </tr>
                </thead>
                <tbody>
    `;

    policies.forEach(p => {
        html += `
            <tr>
                <td><strong>${p.policyNumber}</strong></td>
                <td>${p.customer ? p.customer.firstName + ' ' + p.customer.lastName : 'N/A'}</td>
                <td>${p.agent ? p.agent.firstName + ' ' + p.agent.lastName : 'Direct'}</td>
                <td>${p.policyType ? p.policyType.category : 'N/A'}</td>
                <td>₹${p.coverageAmount.toLocaleString()}</td>
                <td>₹${p.premiumAmount.toLocaleString()}</td>
                <td>${p.expiryDate}</td>
                <td><span class="badge badge-${p.status.toLowerCase()}">${p.status}</span></td>
                <td>
                    <button class="btn btn-secondary btn-sm" onclick="openRenewModal(${p.id})">${t('btn_renew', 'Renew')}</button>
                    <button class="btn btn-primary btn-sm" onclick="openRecordPaymentModal(${p.id})">${t('btn_pay', 'Pay')}</button>
                </td>
            </tr>
        `;
    });

    html += `</tbody></table></div>`;
    container.innerHTML = html;
}

/* 5. CLAIMS VIEW */
async function renderClaimsView(container) {
    const claims = await API.getClaims();
    let html = `
        <div class="page-header">
            <div>
                <h1 class="page-title">${t('clm_title', 'Claims & Assessment Management')}</h1>
                <p class="page-subtitle">${t('clm_subtitle', 'Process incoming claims, technical assessments, and settlements')}</p>
            </div>
            <button class="btn btn-primary" onclick="openSubmitClaimModal()">${t('btn_file_claim', '+ File New Claim')}</button>
        </div>
        <div class="table-container">
            <table class="data-table">
                <thead>
                    <tr>
                        <th>${t('tbl_claim_num', 'Claim #')}</th>
                        <th>${t('tbl_policy_num', 'Policy #')}</th>
                        <th>${t('tbl_customer', 'Customer')}</th>
                        <th>${t('tbl_amount_claimed', 'Amount Claimed')}</th>
                        <th>${t('tbl_incident_date', 'Incident Date')}</th>
                        <th>${t('tbl_priority', 'Priority')}</th>
                        <th>${t('tbl_status', 'Status')}</th>
                        <th>${t('tbl_actions', 'Actions')}</th>
                    </tr>
                </thead>
                <tbody>
    `;

    claims.forEach(c => {
        html += `
            <tr>
                <td><strong>${c.claimNumber}</strong></td>
                <td>${c.policy ? c.policy.policyNumber : 'N/A'}</td>
                <td>${c.customer ? c.customer.firstName + ' ' + c.customer.lastName : 'N/A'}</td>
                <td>₹${c.claimAmount.toLocaleString()}</td>
                <td>${c.incidentDate}</td>
                <td><span class="badge badge-${c.priority.toLowerCase()}">${c.priority}</span></td>
                <td><span class="badge badge-${c.status.toLowerCase()}">${c.status}</span></td>
                <td>
                    <button class="btn btn-secondary btn-sm" onclick="openAssessModal(${c.id}, ${c.claimAmount})">${t('btn_assess', 'Assess')}</button>
                    <button class="btn btn-primary btn-sm" onclick="openSettleModal(${c.id}, ${c.claimAmount})">${t('btn_settle', 'Settle')}</button>
                </td>
            </tr>
        `;
    });

    html += `</tbody></table></div>`;
    container.innerHTML = html;
}

/* 6. PAYMENTS VIEW */
async function renderPaymentsView(container) {
    const payments = await API.getPayments();
    let html = `
        <div class="page-header">
            <div>
                <h1 class="page-title">${t('pay_title', 'Premium Payment Ledger')}</h1>
                <p class="page-subtitle">${t('pay_subtitle', 'Track premium collection transactions and transaction references')}</p>
            </div>
            <button class="btn btn-primary" onclick="openRecordPaymentModal()">${t('btn_record_payment', '+ Record Payment')}</button>
        </div>
        <div class="table-container">
            <table class="data-table">
                <thead>
                    <tr>
                        <th>${t('tbl_receipt', 'Payment #')}</th>
                        <th>${t('tbl_policy_num', 'Policy #')}</th>
                        <th>${t('tbl_customer', 'Customer')}</th>
                        <th>${t('tbl_amount', 'Amount Paid')}</th>
                        <th>${t('tbl_method', 'Method')}</th>
                        <th>Txn Ref</th>
                        <th>${t('tbl_date', 'Date')}</th>
                        <th>${t('tbl_status', 'Status')}</th>
                    </tr>
                </thead>
                <tbody>
    `;

    payments.forEach(p => {
        html += `
            <tr>
                <td><strong>${p.paymentNumber}</strong></td>
                <td>${p.policy ? p.policy.policyNumber : 'N/A'}</td>
                <td>${p.policy && p.policy.customer ? p.policy.customer.firstName + ' ' + p.policy.customer.lastName : 'N/A'}</td>
                <td>₹${p.amount.toLocaleString()}</td>
                <td>${p.paymentMethod}</td>
                <td><code>${p.transactionRef || 'N/A'}</code></td>
                <td>${p.paymentDate ? p.paymentDate.split('T')[0] : 'N/A'}</td>
                <td><span class="badge badge-${p.status.toLowerCase()}">${p.status}</span></td>
            </tr>
        `;
    });

    html += `</tbody></table></div>`;
    container.innerHTML = html;
}

/* 7. RENEWALS VIEW */
async function renderRenewalsView(container) {
    const renewals = await API.getRenewals();
    let html = `
        <div class="page-header">
            <div>
                <h1 class="page-title">${t('rnw_title', 'Policy Renewal History')}</h1>
                <p class="page-subtitle">${t('rnw_subtitle', 'Log of automated and agent-processed contract term extensions')}</p>
            </div>
        </div>
        <div class="table-container">
            <table class="data-table">
                <thead>
                    <tr>
                        <th>${t('tbl_code', 'Renewal #')}</th>
                        <th>${t('tbl_policy_num', 'Policy #')}</th>
                        <th>${t('tbl_expiry', 'Previous Expiry')}</th>
                        <th>${t('tbl_expiry', 'New Expiry')}</th>
                        <th>${t('tbl_premium', 'Renewal Premium')}</th>
                        <th>${t('tbl_date', 'Processed Date')}</th>
                        <th>${t('tbl_status', 'Status')}</th>
                    </tr>
                </thead>
                <tbody>
    `;

    renewals.forEach(r => {
        html += `
            <tr>
                <td><strong>${r.renewalNumber}</strong></td>
                <td>${r.policy ? r.policy.policyNumber : 'N/A'}</td>
                <td>${r.previousExpiryDate}</td>
                <td><strong style="color: var(--primary);">${r.newExpiryDate}</strong></td>
                <td>₹${r.renewalPremium.toLocaleString()}</td>
                <td>${r.renewalDate}</td>
                <td><span class="badge badge-${r.status.toLowerCase()}">${r.status}</span></td>
            </tr>
        `;
    });

    html += `</tbody></table></div>`;
    container.innerHTML = html;
}

/* MODALS & ACTIONS */
function closeModal(id) {
    document.getElementById(id).classList.remove('show');
}

function openAddCustomerModal() {
    document.getElementById('modal-add-customer').classList.add('show');
}

async function submitAddCustomer(e) {
    e.preventDefault();
    const data = {
        firstName: document.getElementById('cust-fname').value,
        lastName: document.getElementById('cust-lname').value,
        email: document.getElementById('cust-email').value,
        phone: document.getElementById('cust-phone').value,
        city: document.getElementById('cust-city').value,
        state: document.getElementById('cust-state').value,
        address: document.getElementById('cust-address').value
    };

    try {
        await API.createCustomer(data);
        closeModal('modal-add-customer');
        showToast('Customer registered successfully!');
        await loadActiveUserDropdowns();
        renderActiveTab();
    } catch (err) {
        showToast(err.message, true);
    }
}

function openAddAgentModal() {
    document.getElementById('modal-add-agent').classList.add('show');
}

async function submitAddAgent(e) {
    e.preventDefault();
    const data = {
        firstName: document.getElementById('agt-fname').value,
        lastName: document.getElementById('agt-lname').value,
        email: document.getElementById('agt-email').value,
        phone: document.getElementById('agt-phone').value,
        agencyName: document.getElementById('agt-agency').value
    };

    try {
        await API.createAgent(data);
        closeModal('modal-add-agent');
        showToast('Agent added successfully!');
        await loadActiveUserDropdowns();
        renderActiveTab();
    } catch (err) {
        showToast(err.message, true);
    }
}

async function openCreatePolicyModal() {
    const custSelect = document.getElementById('pol-customer-select');
    const typeSelect = document.getElementById('pol-type-select');
    const agentSelect = document.getElementById('pol-agent-select');

    custSelect.innerHTML = currentCustomers.map(c => `<option value="${c.id}">${c.firstName} ${c.lastName}</option>`).join('');
    agentSelect.innerHTML = `<option value="">Direct (No Agent)</option>` + currentAgents.map(a => `<option value="${a.id}">${a.firstName} ${a.lastName}</option>`).join('');

    const types = await API.getPolicyTypes();
    typeSelect.innerHTML = types.map(t => `<option value="${t.id}">${t.name} (₹${t.basePremium}/yr)</option>`).join('');

    document.getElementById('modal-create-policy').classList.add('show');
}

async function submitCreatePolicy(e) {
    e.preventDefault();
    const data = {
        customerId: parseInt(document.getElementById('pol-customer-select').value),
        policyTypeId: parseInt(document.getElementById('pol-type-select').value),
        agentId: document.getElementById('pol-agent-select').value ? parseInt(document.getElementById('pol-agent-select').value) : null,
        coverageAmount: parseFloat(document.getElementById('pol-coverage').value),
        premiumAmount: parseFloat(document.getElementById('pol-premium').value)
    };

    try {
        await API.createPolicy(data);
        closeModal('modal-create-policy');
        showToast('Policy issued successfully!');
        renderActiveTab();
    } catch (err) {
        showToast(err.message, true);
    }
}

function openRecordPaymentModal(policyId = null) {
    const select = document.getElementById('pay-policy-select');
    select.value = policyId || '';
    document.getElementById('modal-record-payment').classList.add('show');
}

async function submitRecordPayment(e) {
    e.preventDefault();
    const data = {
        policyId: parseInt(document.getElementById('pay-policy-select').value),
        amount: parseFloat(document.getElementById('pay-amount').value),
        paymentMethod: document.getElementById('pay-method').value
    };

    try {
        await API.recordPayment(data);
        closeModal('modal-record-payment');
        showToast('Premium payment recorded! Policy activated.');
        renderActiveTab();
    } catch (err) {
        showToast(err.message, true);
    }
}

function openSubmitClaimModal(policyId = null) {
    const custId = getSelectedCustomerId();
    document.getElementById('claim-customer-id').value = custId;
    document.getElementById('modal-submit-claim').classList.add('show');
}

async function submitClaimForm(e) {
    e.preventDefault();
    const data = {
        policyId: parseInt(document.getElementById('claim-policy-id').value),
        customerId: parseInt(document.getElementById('claim-customer-id').value),
        claimAmount: parseFloat(document.getElementById('claim-amount').value),
        incidentDate: document.getElementById('claim-date').value,
        incidentDescription: document.getElementById('claim-desc').value,
        priority: document.getElementById('claim-priority').value
    };

    try {
        await API.submitClaim(data);
        closeModal('modal-submit-claim');
        showToast('Insurance claim submitted successfully!');
        renderActiveTab();
    } catch (err) {
        showToast(err.message, true);
    }
}

function openAssessModal(claimId, amount) {
    document.getElementById('assess-claim-id').value = claimId;
    document.getElementById('assess-amount').value = amount;
    document.getElementById('modal-assess-claim').classList.add('show');
}

async function submitAssessmentForm(e) {
    e.preventDefault();
    const data = {
        claimId: parseInt(document.getElementById('assess-claim-id').value),
        assessedAmount: parseFloat(document.getElementById('assess-amount').value),
        recommendation: document.getElementById('assess-recommendation').value,
        assessmentNotes: document.getElementById('assess-notes').value
    };

    try {
        await API.assessClaim(data);
        closeModal('modal-assess-claim');
        showToast('Claim assessment updated!');
        renderActiveTab();
    } catch (err) {
        showToast(err.message, true);
    }
}

function openSettleModal(claimId, amount) {
    document.getElementById('settle-claim-id').value = claimId;
    document.getElementById('settle-amount').value = amount;
    document.getElementById('modal-settle-claim').classList.add('show');
}

async function submitSettlementForm(e) {
    e.preventDefault();
    const data = {
        claimId: parseInt(document.getElementById('settle-claim-id').value),
        approvedAmount: parseFloat(document.getElementById('settle-amount').value),
        paymentMethod: document.getElementById('settle-method').value,
        notes: document.getElementById('settle-notes').value
    };

    try {
        await API.settleClaim(data);
        closeModal('modal-settle-claim');
        showToast('Claim settled and funds disbursed!');
        renderActiveTab();
    } catch (err) {
        showToast(err.message, true);
    }
}

function openRenewModal(policyId) {
    document.getElementById('renew-policy-id').value = policyId;
    document.getElementById('modal-renew-policy').classList.add('show');
}

async function submitRenewalForm(e) {
    e.preventDefault();
    const data = {
        policyId: parseInt(document.getElementById('renew-policy-id').value),
        termMonths: parseInt(document.getElementById('renew-term').value)
    };

    try {
        await API.processRenewal(data);
        closeModal('modal-renew-policy');
        showToast('Policy renewed successfully!');
        renderActiveTab();
    } catch (err) {
        showToast(err.message, true);
    }
}

async function deleteCustomer(id) {
    if (confirm('Are you sure you want to delete this customer?')) {
        try {
            await API.deleteCustomer(id);
            showToast('Customer deleted');
            renderActiveTab();
        } catch (err) {
            showToast(err.message, true);
        }
    }
}

async function deleteAgent(id) {
    if (confirm('Are you sure you want to delete this agent?')) {
        try {
            await API.deleteAgent(id);
            showToast('Agent deleted');
            renderActiveTab();
        } catch (err) {
            showToast(err.message, true);
        }
    }
}

function showToast(msg, isError = false) {
    const container = document.getElementById('toast-container');
    const toast = document.createElement('div');
    toast.className = `toast ${isError ? 'error' : ''}`;
    toast.innerText = (isError ? 'Error: ' : 'Success: ') + msg;
    container.appendChild(toast);

    setTimeout(() => {
        toast.remove();
    }, 4000);
}
