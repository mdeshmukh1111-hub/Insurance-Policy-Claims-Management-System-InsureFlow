package com.insureflow.config;

import com.insureflow.entity.*;
import com.insureflow.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final AgentRepository agentRepository;
    private final PolicyTypeRepository policyTypeRepository;
    private final PolicyRepository policyRepository;
    private final PremiumPaymentRepository paymentRepository;
    private final RenewalRepository renewalRepository;
    private final ClaimRepository claimRepository;
    private final ClaimAssessmentRepository assessmentRepository;
    private final ClaimSettlementRepository settlementRepository;

    public DataInitializer(CustomerRepository customerRepository, AgentRepository agentRepository, PolicyTypeRepository policyTypeRepository, PolicyRepository policyRepository, PremiumPaymentRepository paymentRepository, RenewalRepository renewalRepository, ClaimRepository claimRepository, ClaimAssessmentRepository assessmentRepository, ClaimSettlementRepository settlementRepository) {
        this.customerRepository = customerRepository;
        this.agentRepository = agentRepository;
        this.policyTypeRepository = policyTypeRepository;
        this.policyRepository = policyRepository;
        this.paymentRepository = paymentRepository;
        this.renewalRepository = renewalRepository;
        this.claimRepository = claimRepository;
        this.assessmentRepository = assessmentRepository;
        this.settlementRepository = settlementRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (customerRepository.count() > 0) {
            System.out.println("-> Database already initialized with data.");
            return;
        }

        System.out.println("-> Seeding initial sample data into InsureFlow database...");

        // 1. Seed Customers
        Customer c1 = customerRepository.save(new Customer("CUST-1001", "Aarav", "Sharma", "aarav.sharma@example.com", "+91-9876543210", "42 MG Road", "Mumbai", "Maharashtra", "400001"));
        Customer c2 = customerRepository.save(new Customer("CUST-1002", "Priya", "Patel", "priya.patel@example.com", "+91-9823456789", "15 SG Highway", "Ahmedabad", "Gujarat", "380015"));
        Customer c3 = customerRepository.save(new Customer("CUST-1003", "Rohan", "Verma", "rohan.verma@example.com", "+91-9711223344", "88 Park Street", "Kolkata", "West Bengal", "700016"));
        Customer c4 = customerRepository.save(new Customer("CUST-1004", "Ananya", "Deshmukh", "ananya.d@example.com", "+91-9988776655", "104 FC Road", "Pune", "Maharashtra", "411004"));

        // 2. Seed Agents
        Agent a1 = agentRepository.save(new Agent("AGT-2001", "Vikram", "Mehta", "vikram.agent@insureflow.com", "+91-9811122233", "Apex Financial Advisory"));
        Agent a2 = agentRepository.save(new Agent("AGT-2002", "Sneha", "Kulkarni", "sneha.agent@insureflow.com", "+91-9844455566", "TrustCare Assurance"));

        // 3. Seed Policy Types
        PolicyType pt1 = policyTypeRepository.save(new PolicyType("HLTH-GOLD", "Comprehensive Health Care", "Full medical hospitalization coverage including cashless ICU and OPD care.", "HEALTH", 1200.0, 50000.0, 1000000.0, 12));
        PolicyType pt2 = policyTypeRepository.save(new PolicyType("AUTO-COMP", "Auto Shield Bumper-to-Bumper", "Comprehensive motor vehicle insurance covering third party damages and collision.", "AUTO", 850.0, 20000.0, 500000.0, 12));
        PolicyType pt3 = policyTypeRepository.save(new PolicyType("LIFE-TERM", "Secure Life Protection", "Pure term life insurance giving 100x income lump sum cover.", "LIFE", 1500.0, 100000.0, 5000000.0, 24));
        PolicyType pt4 = policyTypeRepository.save(new PolicyType("PROP-SAFE", "Home & Property Assurance", "Protection against natural disasters, fire, and burglary for residential properties.", "PROPERTY", 950.0, 50000.0, 2000000.0, 12));

        // 4. Seed Policies
        Policy p1 = policyRepository.save(new Policy("POL-10001", c1, a1, pt1, 500000.0, 1200.0, "ANNUALLY", LocalDate.now().minusMonths(6), LocalDate.now().plusMonths(6), "ACTIVE"));
        Policy p2 = policyRepository.save(new Policy("POL-10002", c2, a1, pt2, 350000.0, 850.0, "ANNUALLY", LocalDate.now().minusMonths(11), LocalDate.now().plusDays(15), "RENEWAL_DUE"));
        Policy p3 = policyRepository.save(new Policy("POL-10003", c3, a2, pt3, 2000000.0, 1500.0, "ANNUALLY", LocalDate.now().minusMonths(2), LocalDate.now().plusMonths(22), "ACTIVE"));
        Policy p4 = policyRepository.save(new Policy("POL-10004", c4, a2, pt4, 800000.0, 950.0, "ANNUALLY", LocalDate.now().minusMonths(14), LocalDate.now().minusMonths(2), "EXPIRED"));

        // 5. Seed Premium Payments
        paymentRepository.save(new PremiumPayment("PAY-9001", p1, 1200.0, LocalDateTime.now().minusMonths(6), "CREDIT_CARD", "SUCCESS", "TXN-881920", "Annual premium paid via Credit Card"));
        paymentRepository.save(new PremiumPayment("PAY-9002", p2, 850.0, LocalDateTime.now().minusMonths(11), "UPI", "SUCCESS", "TXN-771829", "Initial premium payment via Google Pay"));
        paymentRepository.save(new PremiumPayment("PAY-9003", p3, 1500.0, LocalDateTime.now().minusMonths(2), "BANK_TRANSFER", "SUCCESS", "TXN-661543", "Net Banking transfer processed"));

        // 6. Seed Renewals
        renewalRepository.save(new Renewal("RNW-3001", p1, LocalDate.now().minusMonths(6), LocalDate.now().minusMonths(6), LocalDate.now().plusMonths(6), 1200.0, "COMPLETED", a1));

        // 7. Seed Claims
        Claim cl1 = claimRepository.save(new Claim("CLM-7001", p1, c1, 45000.0, LocalDate.now().minusDays(20), "Hospitalization due to sudden appendectomy surgery at City Hospital.", "APPROVED", "HIGH", "MEDIUM"));
        Claim cl2 = claimRepository.save(new Claim("CLM-7002", p2, c2, 18500.0, LocalDate.now().minusDays(5), "Vehicle collision resulting in broken front bumper and headlight damage.", "SUBMITTED", "MEDIUM", "LOW"));
        Claim cl3 = claimRepository.save(new Claim("CLM-7003", p3, c3, 120000.0, LocalDate.now().minusDays(35), "Emergency cardiac treatment & ICU stay.", "SETTLED", "URGENT", "HIGH"));

        // 8. Seed Claim Assessments
        assessmentRepository.save(new ClaimAssessment(cl1, a1, 42000.0, "Hospital bill verified. Covered under cashless surgical clause.", "APPROVE", "COMPLETED"));
        assessmentRepository.save(new ClaimAssessment(cl3, a2, 120000.0, "Full medical audit completed. Critical illness benefit approved.", "APPROVE", "COMPLETED"));

        // 9. Seed Claim Settlements
        settlementRepository.save(new ClaimSettlement(cl3, 120000.0, "SETTL-994812", "COMPLETED", "BANK_TRANSFER", "Direct NEFT transfer to customer bank account completed."));

        System.out.println("-> InsureFlow sample data successfully seeded into MySQL!");
    }
}
