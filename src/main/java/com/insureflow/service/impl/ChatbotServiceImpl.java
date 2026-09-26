package com.insureflow.service.impl;

import com.insureflow.dto.ChatRequestDTO;
import com.insureflow.dto.ChatResponseDTO;
import com.insureflow.entity.*;
import com.insureflow.repository.*;
import com.insureflow.service.ChatbotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
public class ChatbotServiceImpl implements ChatbotService {

    private final ClaimRepository claimRepository;
    private final ClaimAssessmentRepository claimAssessmentRepository;
    private final PolicyRepository policyRepository;
    private final PolicyTypeRepository policyTypeRepository;
    private final CustomerRepository customerRepository;
    private final AgentRepository agentRepository;
    private final PremiumPaymentRepository paymentRepository;

    public ChatbotServiceImpl(ClaimRepository claimRepository,
                              ClaimAssessmentRepository claimAssessmentRepository,
                              PolicyRepository policyRepository,
                              PolicyTypeRepository policyTypeRepository,
                              CustomerRepository customerRepository,
                              AgentRepository agentRepository,
                              PremiumPaymentRepository paymentRepository) {
        this.claimRepository = claimRepository;
        this.claimAssessmentRepository = claimAssessmentRepository;
        this.policyRepository = policyRepository;
        this.policyTypeRepository = policyTypeRepository;
        this.customerRepository = customerRepository;
        this.agentRepository = agentRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public ChatResponseDTO processQuery(ChatRequestDTO request) {
        String input = request.getMessage() != null ? request.getMessage().trim() : "";
        String lower = input.toLowerCase();
        String lang = request.getLanguage() != null ? request.getLanguage().toLowerCase() : "en";

        // 1. Specific Claim ID lookup
        Matcher claimMatcher = Pattern.compile("(?:claim|clm|दावा|दावा|క్లెయిమ్|ಕ್ಲೇಮ್)[\\s#-_]*(\\d+)", Pattern.CASE_INSENSITIVE).matcher(input);
        if (claimMatcher.find()) {
            Long claimId = Long.parseLong(claimMatcher.group(1));
            return handleClaimLookup(claimId, lang);
        }

        // 2. Specific Policy ID lookup
        Matcher policyMatcher = Pattern.compile("(?:policy|pol|पॉलिसी|పాలిసీ|ಪಾಲಿಸಿ)[\\s#-_]*(\\d+)", Pattern.CASE_INSENSITIVE).matcher(input);
        if (policyMatcher.find() && !lower.contains("type") && !lower.contains("plan")) {
            Long policyId = Long.parseLong(policyMatcher.group(1));
            return handlePolicyLookup(policyId, lang);
        }

        // 3. Claims Inquiry & Filing
        if (lower.contains("claim") || lower.contains("दावा") || lower.contains("क्लेम") || lower.contains("క్లెయిమ్") || lower.contains("ಕ್ಲೇಮ್") || lower.contains("દાવા") || lower.contains("கோரிக்கை")) {
            if (lower.contains("file") || lower.contains("submit") || lower.contains("how") || lower.contains("कैसे") || lower.contains("कसा")) {
                return getFilingClaimInfo(lang);
            }
            return handleMyClaimsSummary(request.getUserId(), lang);
        }

        // 4. Policy Plans Inquiry
        if (lower.contains("policy") || lower.contains("policies") || lower.contains("plan") || lower.contains("पॉलिसी") || lower.contains("पॉलिसीज") || lower.contains("పాలిసీ") || lower.contains("ಪಾಲಿಸಿ") || lower.contains("પોલિસી") || lower.contains("பாலிசி")) {
            return handleAvailablePolicyTypes(lang);
        }

        // 5. Payments & Premiums
        if (lower.contains("pay") || lower.contains("payment") || lower.contains("premium") || lower.contains("भुगतान") || lower.contains("प्रीमियम") || lower.contains("చెల్లింపు")) {
            return getPaymentInfo(lang);
        }

        // 6. Renewals
        if (lower.contains("renew") || lower.contains("renewal") || lower.contains("नवीनीकरण") || lower.contains("नूतनीकरण")) {
            return getRenewalInfo(lang);
        }

        // 7. Academic DSA Algorithms
        if (lower.contains("dsa") || lower.contains("algorithm") || lower.contains("quicksort") || lower.contains("hashmap") || lower.contains("heap") || lower.contains("graph")) {
            return getDsaInfo(lower, lang);
        }

        // 8. DBMS Architecture
        if (lower.contains("db") || lower.contains("database") || lower.contains("dbms") || lower.contains("sql") || lower.contains("3nf")) {
            return getDbmsInfo(lang);
        }

        // 9. Greeting / Hello
        if (lower.contains("hi") || lower.contains("hello") || lower.contains("hey") || lower.contains("नमस्ते") || lower.contains("नमस्कार") || lower.contains("నమస్కారం") || lower.contains("வணக்கம்")) {
            return getGreetingInfo(lang);
        }

        // Fallback
        return getFallbackInfo(lang);
    }

    private ChatResponseDTO handleClaimLookup(Long claimId, String lang) {
        Optional<Claim> opt = claimRepository.findById(claimId);
        if (opt.isEmpty()) {
            String notFoundMsg = switch (lang) {
                case "hi" -> "❌ **दावा #" + claimId + " नहीं मिला**\n\nसिस्टम में ID #" + claimId + " का कोई रिकॉर्ड मौजूद नहीं है।";
                case "mr" -> "❌ **दावा #" + claimId + " सापडला नाही**\n\nसिस्टममध्ये ID #" + claimId + " ची कोणतीही नोंद नाही.";
                case "te" -> "❌ **క్లెయిమ్ #" + claimId + " కనుగొనబడలేదు**";
                case "kn" -> "❌ **ಕ್ಲೇಮ್ #" + claimId + " ಕಂಡುಬಂದಿಲ್ಲ**";
                case "gu" -> "❌ **ક્લેઈમ #" + claimId + " મળ્યો નથી**";
                case "ta" -> "❌ **கோரிக்கை #" + claimId + " காணப்படவில்லை**";
                default -> "❌ **Claim #" + claimId + " Not Found**\n\nNo claim record exists with ID #" + claimId + ".";
            };
            return new ChatResponseDTO(notFoundMsg, "CLAIM_NOT_FOUND", getSuggestions(lang));
        }

        Claim c = opt.get();
        Optional<ClaimAssessment> assessmentOpt = claimAssessmentRepository.findByClaimId(claimId);

        StringBuilder sb = new StringBuilder();
        if (lang.equals("hi")) {
            sb.append("📋 **दावा विवरण #").append(c.getId()).append(" (").append(c.getClaimNumber()).append(")**\n\n")
              .append("• **स्थिति**: `").append(c.getStatus()).append("`\n")
              .append("• **प्राथमिकता**: `").append(c.getPriority()).append("`\n")
              .append("• **ग्राहक**: ").append(c.getCustomer().getFirstName()).append(" ").append(c.getCustomer().getLastName()).append("\n")
              .append("• **दावा की गई राशि**: ₹").append(String.format("%,.2f", c.getClaimAmount())).append("\n");
        } else if (lang.equals("mr")) {
            sb.append("📋 **दावा तपशील #").append(c.getId()).append(" (").append(c.getClaimNumber()).append(")**\n\n")
              .append("• **स्थिती**: `").append(c.getStatus()).append("`\n")
              .append("• **प्राधान्य**: `").append(c.getPriority()).append("`\n")
              .append("• **ग्राहक**: ").append(c.getCustomer().getFirstName()).append(" ").append(c.getCustomer().getLastName()).append("\n")
              .append("• **दावा केलेली रक्कम**: ₹").append(String.format("%,.2f", c.getClaimAmount())).append("\n");
        } else {
            sb.append("📋 **Claim Details for #").append(c.getId()).append(" (").append(c.getClaimNumber()).append(")**\n\n")
              .append("• **Status**: `").append(c.getStatus()).append("`\n")
              .append("• **Priority**: `").append(c.getPriority()).append("` | **Risk Level**: `").append(c.getRiskLevel()).append("`\n")
              .append("• **Customer**: ").append(c.getCustomer().getFirstName()).append(" ").append(c.getCustomer().getLastName()).append("\n")
              .append("• **Claimed Amount**: ₹").append(String.format("%,.2f", c.getClaimAmount())).append("\n");
        }

        if (assessmentOpt.isPresent()) {
            sb.append("• **Approved Assessed Amount**: ₹").append(String.format("%,.2f", assessmentOpt.get().getAssessedAmount())).append("\n");
        }

        return new ChatResponseDTO(sb.toString(), "CLAIM_DETAILS", getSuggestions(lang));
    }

    private ChatResponseDTO handlePolicyLookup(Long policyId, String lang) {
        Optional<Policy> opt = policyRepository.findById(policyId);
        if (opt.isEmpty()) {
            return new ChatResponseDTO("❌ Policy #" + policyId + " not found.", "POLICY_NOT_FOUND", getSuggestions(lang));
        }
        Policy p = opt.get();
        StringBuilder sb = new StringBuilder();
        sb.append("📜 **Policy Contract #").append(p.getId()).append(" (").append(p.getPolicyNumber()).append(")**\n\n")
          .append("• **Plan**: ").append(p.getPolicyType().getName()).append("\n")
          .append("• **Status**: `").append(p.getStatus()).append("`\n")
          .append("• **Coverage Amount**: ₹").append(String.format("%,.2f", p.getCoverageAmount())).append("\n")
          .append("• **Annual Premium**: ₹").append(String.format("%,.2f", p.getPremiumAmount())).append("\n");
        return new ChatResponseDTO(sb.toString(), "POLICY_DETAILS", getSuggestions(lang));
    }

    private ChatResponseDTO handleMyClaimsSummary(Long userId, String lang) {
        List<Claim> claims = claimRepository.findAll();
        StringBuilder sb = new StringBuilder("📊 **Claims Summary (Total: ").append(claims.size()).append("):**\n\n");
        for (Claim c : claims) {
            sb.append("• **Claim #").append(c.getId()).append("**: `").append(c.getStatus()).append("` - ₹").append(String.format("%,.2f", c.getClaimAmount())).append("\n");
        }
        return new ChatResponseDTO(sb.toString(), "CLAIMS_SUMMARY", getSuggestions(lang));
    }

    private ChatResponseDTO handleAvailablePolicyTypes(String lang) {
        List<PolicyType> types = policyTypeRepository.findAll();
        StringBuilder sb = new StringBuilder();
        if (lang.equals("hi")) {
            sb.append("🛡️ **उपलब्ध बीमा योजनाएं:**\n\n");
        } else if (lang.equals("mr")) {
            sb.append("🛡️ **उपलब्ध इन्शुरन्स प्लॅन्स:**\n\n");
        } else {
            sb.append("🛡️ **Available Insurance Plans on InsureFlow:**\n\n");
        }

        for (PolicyType pt : types) {
            sb.append("• **").append(pt.getName()).append("** (`").append(pt.getTypeCode()).append("`)\n")
              .append("  - Category: *").append(pt.getCategory()).append("*\n")
              .append("  - Coverage: ₹").append(String.format("%,.2f", pt.getMaxCoverage())).append(" | Premium: ₹").append(String.format("%,.2f", pt.getBasePremium())).append("/yr\n\n");
        }
        return new ChatResponseDTO(sb.toString(), "POLICY_PLANS", getSuggestions(lang));
    }

    private ChatResponseDTO getFilingClaimInfo(String lang) {
        String msg = switch (lang) {
            case "hi" -> "📋 **दावा कैसे दायर करें:**\n\n1. **Claims टैब** पर जाएं।\n2. **File New Claim** बटन पर क्लिक करें।\n3. पॉलिसी ID और घटना विवरण दर्ज करें।";
            case "mr" -> "📋 **दावा कसा दाखल करावा:**\n\n1. **Claims टॅब** वर जा.\n2. **File New Claim** बटणावर क्लिक करा.\n3. पॉलिसी ID आणि तपशील भरा.";
            default -> "📋 **How to File a Claim:**\n\n1. Navigate to **Claims Tab**.\n2. Click **File New Claim** button.\n3. Enter Policy ID, Amount, Incident Date, and Description.";
        };
        return new ChatResponseDTO(msg, "CLAIM_HOWTO", getSuggestions(lang));
    }

    private ChatResponseDTO getPaymentInfo(String lang) {
        String msg = switch (lang) {
            case "hi" -> "💳 **प्रीमियम भुगतान विकल्प:** UPI / Google Pay, क्रेडिट/डेबिट कार्ड, नेट बैंकिंग और चेक।";
            case "mr" -> "💳 **प्रीमियम पेमेंट पर्याय:** UPI, क्रेडिट कार्ड, नेट बँकिंग आणि चेक.";
            default -> "💳 **Premium Payments**: Supported via UPI, Credit/Debit Card, Net Banking (NEFT/RTGS), and Cheque.";
        };
        return new ChatResponseDTO(msg, "PAYMENT_INFO", getSuggestions(lang));
    }

    private ChatResponseDTO getRenewalInfo(String lang) {
        String msg = switch (lang) {
            case "hi" -> "🔄 **पॉलिसी नवीनीकरण:** 12 महीने (1 वर्ष), 24 महीने (2 वर्ष), या 36 महीने (3 वर्ष) चुनें।";
            case "mr" -> "🔄 **पॉलिसी नूतनीकरण:** 12 महिने (1 वर्ष), 24 महिने (2 वर्षे), किंवा 36 महिने (3 वर्षे) निवडा.";
            default -> "🔄 **Policy Renewal**: Flexible renewal terms of 12, 24, or 36 months available.";
        };
        return new ChatResponseDTO(msg, "RENEWAL_INFO", getSuggestions(lang));
    }

    private ChatResponseDTO getDsaInfo(String lower, String lang) {
        String msg = "⚡ **Custom DSA Algorithms in InsureFlow:**\n\n• **QuickSort**: Lomuto partition for policy/claim sorting.\n• **CustomHashMap**: O(1) policy lookups.\n• **Max Heap Priority Queue**: Risk & claim severity ranking.\n• **Graph BFS/DFS**: Network fraud analysis.";
        return new ChatResponseDTO(msg, "DSA_INFO", getSuggestions(lang));
    }

    private ChatResponseDTO getDbmsInfo(String lang) {
        String msg = "🗄️ **DBMS Architecture (3NF Verified):**\n\n• 10 Normalized Relational Tables.\n• Stored Procedure `sp_process_claim_settlement`.\n• Stored Function `fn_calculate_total_customer_premium`.\n• Trigger `trg_update_policy_status_on_renewal`.";
        return new ChatResponseDTO(msg, "DBMS_INFO", getSuggestions(lang));
    }

    private ChatResponseDTO getGreetingInfo(String lang) {
        String msg = switch (lang) {
            case "hi" -> "👋 **नमस्ते! मैं इंश्योरबॉट हूँ**, आपका इंश्योरफ्लो AI सहायक। पॉलिसी, दावे या भुगतान के बारे में पूछें!";
            case "mr" -> "👋 **नमस्कार! मी इन्शुरबॉट आहे**, तुमचा इन्शुरफ्लो AI सहाय्यक। पॉलिसी, दावे किंवा पेमेंट्स बद्दल विचारा!";
            case "te" -> "👋 **నమస్కారం! నేను ఇన్స్యూర్ బాట్**, మీ AI సహాయకుడిని.";
            case "kn" -> "👋 **ನಮಸ್ಕಾರ! ನಾನು ಇನ್ಶುರ್-ಬಾಟ್**, ನಿಮ್ಮ AI ಸಹಾಯಕ.";
            case "gu" -> "👋 **નમસ્તે! હું ઈન્સ્યોરબોટ છું**, તમારો AI સહાયક.";
            case "ta" -> "👋 **வணக்கம்! நான் இன்ஷூர்-பாட்**, உங்கள் AI உதவியாளர்.";
            default -> "👋 **Hello! Welcome to InsureBot**, your intelligent InsureFlow AI Assistant. How can I help you today?";
        };
        return new ChatResponseDTO(msg, "GREETING", getSuggestions(lang));
    }

    private ChatResponseDTO getFallbackInfo(String lang) {
        return new ChatResponseDTO("🤖 InsureBot Assistant: Select one of the options below or specify a Claim ID (e.g. 'claim #1').", "FALLBACK", getSuggestions(lang));
    }

    private List<String> getSuggestions(String lang) {
        return switch (lang) {
            case "hi" -> Arrays.asList("उपलब्ध पॉलिसी योजनाएं", "दावा #1 की स्थिति जांचें", "दावा कैसे दायर करें?");
            case "mr" -> Arrays.asList("उपलब्ध पॉलिसी योजना", "दावा #1 ची स्थिती तपासा", "दावा कसा दाखल करावा?");
            case "te" -> Arrays.asList("పాలసీ ప్లాన్‌లు", "క్లెయిమ్ #1 స్థితి", "క్లెయిమ్ దాఖలు");
            case "kn" -> Arrays.asList("ಪಾಲಿಸಿ ಯೋಜನೆಗಳು", "ಕ್ಲೇಮ್ #1 ಸ್ಥಿತಿ", "ಕ್ಲೇಮ್ ಸಲ್ಲಿಸುವುದು");
            case "gu" -> Arrays.asList("પોલિસી યોજનાઓ", "ક્લેઈમ #1 ની સ્થિતિ", "ક્લેઈમ કેવી રીતે કરવો?");
            case "ta" -> Arrays.asList("பாலிசி திட்டங்கள்", "கோரிக்கை #1 நிலை", "கோரிக்கை தாக்கல்");
            default -> Arrays.asList("What policy plans are available?", "Check claim #1 status", "How to file a claim?", "Explain DSA algorithms");
        };
    }
}
