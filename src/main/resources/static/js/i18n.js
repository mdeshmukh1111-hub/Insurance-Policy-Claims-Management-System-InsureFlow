/* =====================================================================
   InsureFlow - Internationalization (i18n) & Translation System
   Supports: English (en), Hindi (hi), Marathi (mr), Telugu (te), 
             Kannada (kn), Gujarati (gu), Tamil (ta)
   ===================================================================== */

const I18N_DICTIONARY = {
    en: {
        brand: "InsureFlow",
        nav_dashboard: "Dashboard",
        nav_customers: "Customers",
        nav_agents: "Agents",
        nav_policies: "Policies",
        nav_claims: "Claims",
        nav_payments: "Payments",
        nav_renewals: "Renewals",
        
        role_label: "Active Role:",
        role_admin: "Admin",
        role_customer: "Customer",
        role_agent: "Agent",
        select_profile: "Select Simulated Profile:",
        
        // Buttons & Actions
        btn_add_customer: "+ Register Customer",
        btn_add_agent: "+ Register Agent",
        btn_issue_policy: "+ Issue Policy",
        btn_record_payment: "+ Record Payment",
        btn_file_claim: "+ File New Claim",
        btn_cancel: "Cancel",
        btn_save: "Save",
        btn_submit: "Submit",
        btn_delete: "Delete",
        btn_renew: "Renew",
        btn_assess: "Assess",
        btn_settle: "Settle",
        btn_pay: "Pay",

        // Titles & Headers
        dash_title: "Dashboard Overview",
        dash_subtitle: "Real-time overview of key metrics, policy statuses, and claims workflow",
        cust_title: "Customer Management",
        cust_subtitle: "Register and manage insured policyholders",
        agt_title: "Agent Directory",
        agt_subtitle: "Manage insurance brokers and underwriters",
        pol_title: "Policy Lifecycle Management",
        pol_subtitle: "Create, monitor, and update active policy contracts",
        clm_title: "Claims & Assessment Management",
        clm_subtitle: "Process incoming claims, technical assessments, and settlements",
        pay_title: "Premium Payment Ledger",
        pay_subtitle: "Track premium collection transactions and transaction references",
        rnw_title: "Policy Renewals History",
        rnw_subtitle: "Track policy contract extensions and renewal histories",

        // Table Columns
        tbl_code: "Code",
        tbl_name: "Full Name",
        tbl_email: "Email",
        tbl_phone: "Phone",
        tbl_city: "City / State",
        tbl_status: "Status",
        tbl_actions: "Actions",
        tbl_agency: "Agency Name",
        tbl_policy_num: "Policy #",
        tbl_customer: "Customer",
        tbl_agent: "Agent",
        tbl_type: "Policy Type",
        tbl_category: "Category",
        tbl_coverage: "Coverage",
        tbl_premium: "Premium",
        tbl_expiry: "Expiry Date",
        tbl_claim_num: "Claim #",
        tbl_amount_claimed: "Amount Claimed",
        tbl_incident_date: "Incident Date",
        tbl_priority: "Priority",
        tbl_receipt: "Receipt #",
        tbl_amount: "Amount Paid",
        tbl_method: "Payment Method",
        tbl_date: "Date",
        tbl_term: "Term",

        // KPI Labels
        kpi_total_cust: "Total Customers",
        kpi_active_agt: "Active Agents",
        kpi_active_pol: "Active Policies",
        kpi_pending_clm: "Pending Claims",
        kpi_prem_coll: "Premium Collected",
        kpi_settle_disb: "Settlement Disbursed",
        
        // Chatbot UI
        bot_title: "InsureBot AI",
        bot_subtitle: "Active Assistant",
        bot_placeholder: "Ask InsureBot a question...",
        bot_welcome: "👋 <strong>Hello! I am InsureBot</strong>, your intelligent InsureFlow assistant.<br><br>Ask me about <strong>policy plans</strong>, <strong>claim status</strong>, <strong>payments</strong>, or project <strong>DSA & DBMS specs</strong>!",
        
        // Dynamic Suggestions
        sug_policies: "What policy plans are available?",
        sug_claim_status: "Check claim #1 status",
        sug_file_claim: "How to file a claim?",
        sug_dsa: "Explain DSA algorithms used",

        footer_text: "InsureFlow © 2026 - Insurance Policy & Claims Management System | Computer Engineering Project (Java + DSA + DBMS)"
    },
    hi: {
        brand: "इंश्योरफ्लो",
        nav_dashboard: "डैशबोर्ड",
        nav_customers: "ग्राहक",
        nav_agents: "एजेंट",
        nav_policies: "पॉलिसी",
        nav_claims: "दावे (Claims)",
        nav_payments: "भुगतान",
        nav_renewals: "नवीनीकरण",
        
        role_label: "सक्रिय भूमिका:",
        role_admin: "एडमिन",
        role_customer: "ग्राहक",
        role_agent: "एजेंट",
        select_profile: "सिम्युलेटेड प्रोफ़ाइल चुनें:",
        
        btn_add_customer: "+ नया ग्राहक जोड़ें",
        btn_add_agent: "+ एजेंट पंजीकृत करें",
        btn_issue_policy: "+ पॉलिसी जारी करें",
        btn_record_payment: "+ प्रीमियम भुगतान दर्ज करें",
        btn_file_claim: "+ नया दावा दायर करें",
        btn_cancel: "रद्द करें",
        btn_save: "सहेजें",
        btn_submit: "जमा करें",
        btn_delete: "हटाएं",
        btn_renew: "नवीनीकरण करें",
        btn_assess: "आकलन करें",
        btn_settle: "निपटान करें",
        btn_pay: "भुगतान करें",

        dash_title: "डैशबोर्ड अवलोकन",
        dash_subtitle: "मुख्य मेट्रिक्स, पॉलिसी स्थिति और दावा कार्यप्रवाह का वास्तविक समय अवलोकन",
        cust_title: "ग्राहक प्रबंधन",
        cust_subtitle: "बीमित पॉलिसीधारकों को पंजीकृत और प्रबंधित करें",
        agt_title: "एजेंट निर्देशिका",
        agt_subtitle: "बीमा दलालों और अंडरराइटर्स का प्रबंधन करें",
        pol_title: "पॉलिसी जीवनचक्र प्रबंधन",
        pol_subtitle: "सक्रिय पॉलिसी अनुबंध बनाएं और ट्रैक करें",
        clm_title: "दावा और आकलन प्रबंधन",
        clm_subtitle: "आने वाले दावों, तकनीकी आकलन और निपटान को संसाधित करें",
        pay_title: "प्रीमियम भुगतान लेजर",
        pay_subtitle: "प्रीमियम संग्रह और लेनदेन ट्रैक करें",
        rnw_title: "पॉलिसी नवीनीकरण इतिहास",
        rnw_subtitle: "अनुबंध एक्सटेंशन और नवीनीकरण इतिहास ट्रैक करें",

        tbl_code: "कोड",
        tbl_name: "पूरा नाम",
        tbl_email: "ईमेल",
        tbl_phone: "फोन",
        tbl_city: "शहर / राज्य",
        tbl_status: "स्थिति",
        tbl_actions: "कार्रवाई",
        tbl_agency: "एजेंसी का नाम",
        tbl_policy_num: "पॉलिसी #",
        tbl_customer: "ग्राहक",
        tbl_agent: "एजेंट",
        tbl_type: "पॉलिसी का प्रकार",
        tbl_category: "श्रेणी",
        tbl_coverage: "कवरेज राशि",
        tbl_premium: "वार्षिक प्रीमियम",
        tbl_expiry: "समाप्ति तिथि",
        tbl_claim_num: "दावा #",
        tbl_amount_claimed: "दावा की गई राशि",
        tbl_incident_date: "घटना की तिथि",
        tbl_priority: "प्राथमिकता",
        tbl_receipt: "रसीद #",
        tbl_amount: "भुगतान राशि",
        tbl_method: "भुगतान विधि",
        tbl_date: "तिथि",
        tbl_term: "अवधि",

        kpi_total_cust: "कुल ग्राहक",
        kpi_active_agt: "सक्रिय एजेंट",
        kpi_active_pol: "सक्रिय नीतियां",
        kpi_pending_clm: "लंबित दावे",
        kpi_prem_coll: "कुल एकत्रित प्रीमियम",
        kpi_settle_disb: "निपटाया गया दावा",
        
        bot_title: "इंश्योरबॉट AI",
        bot_subtitle: "सक्रिय सहायक",
        bot_placeholder: "इंश्योरबॉट से सवाल पूछें...",
        bot_welcome: "👋 <strong>नमस्ते! मैं इंश्योरबॉट हूँ</strong>, आपका इंश्योरफ्लो AI सहायक।<br><br>मुझसे <strong>पॉलिसी योजनाओं</strong>, <strong>दावे की स्थिति</strong>, <strong>भुगतान</strong> या <strong>DSA/DBMS विवरण</strong> के बारे में पूछें!",
        
        sug_policies: "कौन सी पॉलिसी योजनाएं उपलब्ध हैं?",
        sug_claim_status: "दावा #1 की स्थिति जांचें",
        sug_file_claim: "दावा कैसे दायर करें?",
        sug_dsa: "उपयोग किए गए DSA एल्गोरिदम समझाइए",

        footer_text: "इंश्योरफ्लो © 2026 - बीमा नीति और दावा प्रबंधन प्रणाली | कंप्यूटर इंजीनियरिंग प्रोजेक्ट"
    },
    mr: {
        brand: "इन्शुरफ्लो",
        nav_dashboard: "डॅशबोर्ड",
        nav_customers: "ग्राहक",
        nav_agents: "एजंट",
        nav_policies: "पॉलिसीज",
        nav_claims: "दावे (Claims)",
        nav_payments: "पेमेंट्स",
        nav_renewals: "नूतनीकरण",
        
        role_label: "सक्रिय भूमिका:",
        role_admin: "ॲडमिन",
        role_customer: "ग्राहक",
        role_agent: "एजंट",
        select_profile: "प्रोफाइल निवडा:",
        
        btn_add_customer: "+ नवीन ग्राहक नोंदवा",
        btn_add_agent: "+ एजंट नोंदवा",
        btn_issue_policy: "+ पॉलिसी इश्यू करा",
        btn_record_payment: "+ प्रीमियम भरा",
        btn_file_claim: "+ नवीन दावा दाखल करा",
        btn_cancel: "रद्द करा",
        btn_save: "जतन करा",
        btn_submit: "सादर करा",
        btn_delete: "हटवा",
        btn_renew: "नूतनीकरण करा",
        btn_assess: "मूल्यांकन करा",
        btn_settle: "निकाल लावा",
        btn_pay: "प्रीमियम भरा",

        dash_title: "डॅशबोर्ड विहंगावलोकन",
        dash_subtitle: "महत्त्वाचे निर्देशक, पॉलिसी स्थिती आणि दावा प्रक्रियेची माहिती",
        cust_title: "ग्राहक व्यवस्थापन",
        cust_subtitle: "पॉलिसीधारक ग्राहकांची नोंदणी आणि व्यवस्थापन करा",
        agt_title: "एजंट निर्देशिका",
        agt_subtitle: "विमा एजंट आणि अंडररायटर्सचे व्यवस्थापन करा",
        pol_title: "पॉलिसी व्यवस्थापन",
        pol_subtitle: "सक्रिय पॉलिसी करारांचे व्यवस्थापन आणि निरीक्षण करा",
        clm_title: "दावा आणि मूल्यांकन व्यवस्थापन",
        clm_subtitle: "येणारे दावे, तांत्रिक मूल्यांकन आणि मंजुरी प्रक्रिया",
        pay_title: "प्रीमियम पेमेंट खाते",
        pay_subtitle: "प्रीमियम जमा आणि व्यवहार नोंदी",
        rnw_title: "पॉलिसी नूतनीकरण इतिहास",
        rnw_subtitle: "पॉलिसी मुदतवाढ आणि नूतनीकरण नोंदी",

        tbl_code: "कोड",
        tbl_name: "पूर्ण नाव",
        tbl_email: "ईमेल",
        tbl_phone: "फोन",
        tbl_city: "शहर / राज्य",
        tbl_status: "स्थिती",
        tbl_actions: "कृती",
        tbl_agency: "एजन्सीचे नाव",
        tbl_policy_num: "पॉलिसी #",
        tbl_customer: "ग्राहक",
        tbl_agent: "एजंट",
        tbl_type: "पॉलिसी प्रकार",
        tbl_category: "वर्ग",
        tbl_coverage: "कव्हरेज रक्कम",
        tbl_premium: "वार्षिक प्रीमियम",
        tbl_expiry: "मुदत संपण्याची तारीख",
        tbl_claim_num: "दावा #",
        tbl_amount_claimed: "दावा केलेली रक्कम",
        tbl_incident_date: "घटनेची तारीख",
        tbl_priority: "प्राधान्य",
        tbl_receipt: "पावती #",
        tbl_amount: "भरलेली रक्कम",
        tbl_method: "पेमेंट पद्धत",
        tbl_date: "तारीख",
        tbl_term: "मुदत",

        kpi_total_cust: "एकूण ग्राहक",
        kpi_active_agt: "सक्रिय एजंट",
        kpi_active_pol: "सक्रिय पॉलिसी",
        kpi_pending_clm: "प्रलंबित दावे",
        kpi_prem_coll: "जमा प्रीमियम",
        kpi_settle_disb: "मंजूर दावे",

        bot_title: "इन्शुरबॉट AI",
        bot_subtitle: "सक्रिय सहाय्यक",
        bot_placeholder: "इन्शुरबॉटला प्रश्न विचारा...",
        bot_welcome: "👋 <strong>नमस्कार! मी इन्शुरबॉट आहे</strong>, तुमचा इन्शुरफ्लो AI सहाय्यक।<br><br>मला <strong>पॉलिसी प्लॅन्स</strong>, <strong>दाव्याची स्थिती</strong>, <strong>पेमेंट्स</strong> किंवा <strong>DSA/DBMS</strong> बद्दल विचारा!",
        
        sug_policies: "कोणत्या पॉलिसी योजना उपलब्ध आहेत?",
        sug_claim_status: "दावा #1 ची स्थिती तपासा",
        sug_file_claim: "दावा कसा दाखल करावा?",
        sug_dsa: "वापरलेले DSA अल्गोरिदम स्पष्ट करा",

        footer_text: "इन्शुरफ्लो © 2026 - इन्शुरन्स पॉलिसी आणि क्लेम व्यवस्थापन प्रणाली | संगणक अभियांत्रिकी प्रकल्प"
    },
    te: {
        brand: "ఇన్స్యూర్-ఫ్లో",
        nav_dashboard: "డాష్‌బోర్డ్",
        nav_customers: "వినియోగదారులు",
        nav_agents: "ఏజెంట్లు",
        nav_policies: "పాలసీలు",
        nav_claims: "క్లెయిమ్‌లు",
        nav_payments: "చెల్లింపులు",
        nav_renewals: "నవీకరణలు",

        role_label: "యాక్టివ్ పాత్ర:",
        role_admin: "అడ్మిన్",
        role_customer: "కస్టమర్",
        role_agent: "ఏజెంట్",
        select_profile: "ప్రొఫైల్‌ను ఎంచుకోండి:",

        btn_add_customer: "+ కస్టమర్ నమోదు",
        btn_add_agent: "+ ఏజెంట్ నమోదు",
        btn_issue_policy: "+ పాలసీ మంజూరు",
        btn_record_payment: "+ ప్రీమియం చెల్లింపు",
        btn_file_claim: "+ క్లెయిమ్ దాఖలు",
        btn_cancel: "రద్దు",
        btn_save: "సేవ్",
        btn_submit: "సమర్పించు",
        btn_delete: "తొలగించు",
        btn_renew: "నవీకరించు",
        btn_assess: "సమీక్షించు",
        btn_settle: "పరిష్కరించు",
        btn_pay: "చెల్లించు",

        dash_title: "డాష్‌బోర్డ్ సారాంశం",
        dash_subtitle: "పాలసీల స్థితి మరియు క్లెయిమ్‌ల సారాంశం",
        cust_title: "కస్టమర్ నిర్వహణ",
        cust_subtitle: "వినియోగదారుల నమోదు మరియు నిర్వహణ",
        agt_title: "ఏజెంట్ డైరెక్టరీ",
        agt_subtitle: "ఏజెంట్ల నిర్వహణ",
        pol_title: "పాలసీ నిర్వహణ",
        pol_subtitle: "యాక్టివ్ పాలసీల ట్రాకింగ్",
        clm_title: "క్లెయిమ్‌ల నిర్వహణ",
        clm_subtitle: "క్లెయిమ్‌ల ఆమోదం మరియు పరిష్కారం",
        pay_title: "ప్రీమియం చెల్లింపులు",
        pay_subtitle: "చెల్లింపు వివరాలు",
        rnw_title: "పాలసీ నవీకరణలు",
        rnw_subtitle: "నవీకరణల వివరాలు",

        tbl_code: "కోడ్",
        tbl_name: "పూర్తి పేరు",
        tbl_email: "ఈమెయిల్",
        tbl_phone: "ఫోన్",
        tbl_city: "నగరం",
        tbl_status: "స్థితి",
        tbl_actions: "చర్యలు",
        tbl_agency: "ఏజెన్సీ పేరు",
        tbl_policy_num: "పాలసీ సంఖ్య",
        tbl_customer: "కస్టమర్",
        tbl_agent: "ఏజెంట్",
        tbl_type: "పాలసీ రకం",
        tbl_category: "వర్గం",
        tbl_coverage: "కవరేజ్ మొత్తం",
        tbl_premium: "ప్రీమియం",
        tbl_expiry: "గడువు తేదీ",
        tbl_claim_num: "క్లెయిమ్ సంఖ్య",
        tbl_amount_claimed: "కోరిన మొత్తం",
        tbl_incident_date: "సంఘటన తేదీ",
        tbl_priority: "ప్రాధాన్యత",
        tbl_receipt: "రశీదు సంఖ్య",
        tbl_amount: "చెల్లించిన మొత్తం",
        tbl_method: "పద్ధతి",
        tbl_date: "తేదీ",
        tbl_term: "కాలపరిమితి",

        kpi_total_cust: "మొత్తం కస్టమర్లు",
        kpi_active_agt: "యాక్టివ్ ఏజెంట్లు",
        kpi_active_pol: "యాక్టివ్ పాలసీలు",
        kpi_pending_clm: "పెండింగ్ క్లెయిమ్‌లు",
        kpi_prem_coll: "సేకరించిన ప్రీమియం",
        kpi_settle_disb: "పరిష్కరించిన క్లెయిమ్‌లు",

        bot_title: "ఇన్స్యూర్ బాట్ AI",
        bot_subtitle: "యాక్టివ్ అసిస్టెంట్",
        bot_placeholder: "ప్రశ్న అడగండి...",
        bot_welcome: "👋 <strong>నమస్కారం! నేను ఇన్స్యూర్ బాట్</strong>, మీ AI సహాయకుడిని.<br><br>పాలసీ ప్లాన్‌లు, క్లెయిమ్ స్థితి లేదా చెల్లింపుల గురించి అడగండి!",
        
        sug_policies: "ఏ పాలసీ ప్లాన్‌లు అందుబాటులో ఉన్నాయి?",
        sug_claim_status: "క్లెయిమ్ #1 స్థితిని తనిఖీ చేయండి",
        sug_file_claim: "క్లెయిమ్ ఎలా నమోదు చేయాలి?",
        sug_dsa: "DSA అల్గోరిథమ్‌లను వివరించండి",

        footer_text: "ఇన్స్యూర్-ఫ్లో © 2026 - ఇన్సూరెన్స్ మేనేజ్‌మెంట్ సిస్టమ్"
    },
    kn: {
        brand: "ಇನ್ಶುರ್-ಫ್ಲೋ",
        nav_dashboard: "ಡ್ಯಾಶ್‌ಬೋರ್ಡ್",
        nav_customers: "ಗ್ರಾಹಕರು",
        nav_agents: "ಏಜೆಂಟರು",
        nav_policies: "ಪಾಲಿಸಿಗಳು",
        nav_claims: "ಕ್ಲೇಮ್‌ಗಳು",
        nav_payments: "ಪಾವತಿಗಳು",
        nav_renewals: "ನವೀಕರಣಗಳು",

        role_label: "ಸಕ್ರಿಯ ಪಾತ್ರ:",
        role_admin: "ಅಡ್ಮಿನ್",
        role_customer: "ಗ್ರಾಹಕ",
        role_agent: "ಏಜೆಂಟ್",
        select_profile: "ಪ್ರೊಫೈಲ್ ಆಯ್ಕೆಮಾಡಿ:",

        btn_add_customer: "+ ಗ್ರಾಹಕ ನೋಂದಣಿ",
        btn_add_agent: "+ ಏಜೆಂಟ್ ನೋಂದಣಿ",
        btn_issue_policy: "+ ಪಾಲಿಸಿ ವಿತರಣೆ",
        btn_record_payment: "+ ಪ್ರೀಮಿಯಂ ಪಾವತಿ",
        btn_file_claim: "+ ಕ್ಲೇಮ್ ಸಲ್ಲಿಸಿ",
        btn_cancel: "ರದ್ದುಮಾಡಿ",
        btn_save: "ಉಳಿಸಿ",
        btn_submit: "ಸಲ್ಲಿಸಿ",
        btn_delete: "ಅಳಿಸಿ",
        btn_renew: "ನವೀಕರಿಸಿ",
        btn_assess: "ಪರಿಶೀಲಿಸಿ",
        btn_settle: "ಪರಿಹರಿಸಿ",
        btn_pay: "ಪಾವತಿಸಿ",

        dash_title: "ಡ್ಯಾಶ್‌ಬೋರ್ಡ್ ಅವಲೋಕನ",
        dash_subtitle: "ಪಾಲಿಸಿಗಳು ಮತ್ತು ಕ್ಲೇಮ್‌ಗಳ ಸಾರಾಂಶ",
        cust_title: "ಗ್ರಾಹಕರ ನಿರ್ವಹಣೆ",
        cust_subtitle: "ಗ್ರಾಹಕರ ನೋಂದಣಿ ಮತ್ತು ನಿರ್ವಹಣೆ",
        agt_title: "ಏಜೆಂಟ್ ಡೈರೆಕ್ಟರಿ",
        agt_subtitle: "ಏಜೆಂಟರ ನಿರ್ವಹಣೆ",
        pol_title: "ಪಾಲಿಸಿ ನಿರ್ವಹಣೆ",
        pol_subtitle: "ಸಕ್ರಿಯ ಪಾಲಿಸಿಗಳ ವಿವರ",
        clm_title: "ಕ್ಲೇಮ್ ನಿರ್ವಹಣೆ",
        clm_subtitle: "ಕ್ಲೇಮ್‌ಗಳ ಪರಿಶೀಲನೆ ಮತ್ತು ಪಾವತಿ",
        pay_title: "ಪ್ರೀಮಿಯಂ ಪಾವತಿಗಳು",
        pay_subtitle: "ಪಾವತಿ ಇತಿಹಾಸ",
        rnw_title: "ಪಾಲಿಸಿ ನವೀಕರಣಗಳು",
        rnw_subtitle: "ನವೀಕರಣ ಇತಿಹಾಸ",

        tbl_code: "ಕೋಡ್",
        tbl_name: "ಪೂರ್ಣ ಹೆಸರು",
        tbl_email: "ಇಮೇಲ್",
        tbl_phone: "ಫೋನ್",
        tbl_city: "ನಗರ",
        tbl_status: "ಸ್ಥಿತಿ",
        tbl_actions: "ಕ್ರಿಯೆಗಳು",
        tbl_agency: "ಏಜೆನ್ಸಿ ಹೆಸರು",
        tbl_policy_num: "ಪಾಲಿಸಿ ಸಂಖ್ಯೆ",
        tbl_customer: "ಗ್ರಾಹಕ",
        tbl_agent: "ಏಜೆಂಟ್",
        tbl_type: "ಪಾಲಿಸಿ ಮಾದರಿ",
        tbl_category: "ವರ್ಗ",
        tbl_coverage: "ಕವರೇಜ್",
        tbl_premium: "ಪ್ರೀಮಿಯಂ",
        tbl_expiry: "ಕೊನೆಗೊಳ್ಳುವ ದಿನಾಂಕ",
        tbl_claim_num: "ಕ್ಲೇಮ್ ಸಂಖ್ಯೆ",
        tbl_amount_claimed: "ಕ್ಲೇಮ್ ಮೊತ್ತ",
        tbl_incident_date: "ಘಟನೆ ದಿನಾಂಕ",
        tbl_priority: "ಆದ್ಯತೆ",
        tbl_receipt: "ರಶೀದಿ ಸಂಖ್ಯೆ",
        tbl_amount: "ಪಾವತಿಸಿದ ಮೊತ್ತ",
        tbl_method: "ವಿಧಾನ",
        tbl_date: "ದಿನಾಂಕ",
        tbl_term: "ಅವಧಿ",

        kpi_total_cust: "ಒಟ್ಟು ಗ್ರಾಹಕರು",
        kpi_active_agt: "ಸಕ್ರಿಯ ಏಜೆಂಟರು",
        kpi_active_pol: "ಸಕ್ರಿಯ ಪಾಲಿಸಿಗಳು",
        kpi_pending_clm: "ಬಾಕಿ ಕ್ಲೇಮ್‌ಗಳು",
        kpi_prem_coll: "ಸಂಗ್ರಹಿಸಿದ ಪ್ರೀಮಿಯಂ",
        kpi_settle_disb: "ಪರಿಹರಿಸಿದ ಕ್ಲೇಮ್‌ಗಳು",

        bot_title: "ಇನ್ಶುರ್-ಬಾಟ್ AI",
        bot_subtitle: "ಸಕ್ರಿಯ ಸಹಾಯಕ",
        bot_placeholder: "ಪ್ರಶ್ನೆ ಕೇಳಿ...",
        bot_welcome: "👋 <strong>ನಮಸ್ಕಾರ! ನಾನು ಇನ್ಶುರ್-ಬಾಟ್</strong>, ನಿಮ್ಮ AI ಸಹಾಯಕ.<br><br>ಪಾಲಿಸಿ ಪ್ಲಾನ್‌ಗಳು, ಕ್ಲೇಮ್ ಸ್ಥಿತಿ ಅಥವಾ ಪಾವತಿಗಳ ಬಗ್ಗೆ ಕೇಳಿ!",
        
        sug_policies: "ಯಾವ ಪಾಲಿಸಿ ಯೋಜನೆಗಳು ಲಭ್ಯವಿವೆ?",
        sug_claim_status: "ಕ್ಲೇಮ್ #1 ಸ್ಥಿತಿ ಪರಿಶೀಲಿಸಿ",
        sug_file_claim: "ಕ್ಲೇಮ್ ಸಲ್ಲಿಸುವುದು ಹೇಗೆ?",
        sug_dsa: "DSA ಆಲ್ಗೊರಿಥಮ್‌ಗಳನ್ನು ವಿವರಿಸಿ",

        footer_text: "ಇನ್ಶುರ್-ಫ್ಲೋ © 2026 - ವಿಮಾ ನಿರ್ವಹಣಾ ವ್ಯವಸ್ಥೆ"
    },
    gu: {
        brand: "ઈન્સ્યોરફ્લો",
        nav_dashboard: "ડેશબોર્ડ",
        nav_customers: "ગ્રાહકો",
        nav_agents: "એજન્ટો",
        nav_policies: "પોલિસીઓ",
        nav_claims: "ક્લેઈમ (દાવા)",
        nav_payments: "ચૂકવણીઓ",
        nav_renewals: "નવીનીકરણ",

        role_label: "સક્રિય ભૂમિકા:",
        role_admin: "એડમિન",
        role_customer: "ગ્રાહક",
        role_agent: "એજન્ટ",
        select_profile: "પ્રોફાઈલ પસંદ કરો:",

        btn_add_customer: "+ નવો ગ્રાહક ઉમેરો",
        btn_add_agent: "+ એજન્ટ નોંધણી કરો",
        btn_issue_policy: "+ પોલિસી આપો",
        btn_record_payment: "+ પ્રીમિયમ ચૂકવો",
        btn_file_claim: "+ ક્લેઈમ સબમિટ કરો",
        btn_cancel: "રદ કરો",
        btn_save: "સાચવો",
        btn_submit: "સબમિટ કરો",
        btn_delete: "હટાવો",
        btn_renew: "નવીનીકરણ કરો",
        btn_assess: "ચકાસો",
        btn_settle: "મંજૂર કરો",
        btn_pay: "ચૂકવો",

        dash_title: "ડેશબોર્ડ ઝાંખી",
        dash_subtitle: "પોલિસીઓ અને ક્લેઈમનું રીઅલ-ટાઈમ સ્ટેટસ",
        cust_title: "ગ્રાહક વ્યવસ્થાપન",
        cust_subtitle: "ગ્રાહકોની નોંધણી અને વ્યવસ્થાપન",
        agt_title: "એજન્ટ ડિરેક્ટરી",
        agt_subtitle: "એજન્ટોનું વ્યવસ્થાપન",
        pol_title: "પોલિસી વ્યવસ્થાપન",
        pol_subtitle: "સક્રિય પોલિસીઓની યાદી",
        clm_title: "ક્લેઈમ વ્યવસ્થાપન",
        clm_subtitle: "ક્લેઈમ ચકાસણી અને મંજૂરી",
        pay_title: "પ્રીમિયમ ચૂકવણી",
        pay_subtitle: "ચૂકવણીની હિસ્ટ્રી",
        rnw_title: "પોલિસી નવીનીકરણ",
        rnw_subtitle: "નવીનીકરણ હિસ્ટ્રી",

        tbl_code: "કોડ",
        tbl_name: "પૂરું નામ",
        tbl_email: "ઈમેઈલ",
        tbl_phone: "ફોન",
        tbl_city: "શહેર",
        tbl_status: "સ્થિતિ",
        tbl_actions: "ક્રિયાઓ",
        tbl_agency: "એજન્સીનું નામ",
        tbl_policy_num: "પોલિસી નં.",
        tbl_customer: "ગ્રાહક",
        tbl_agent: "એજન્ટ",
        tbl_type: "પોલિસીનો પ્રકાર",
        tbl_category: "કેટેગરી",
        tbl_coverage: "કવરેજ રકમ",
        tbl_premium: "પ્રીમિયમ",
        tbl_expiry: "સમાપ્તિ તારીખ",
        tbl_claim_num: "ક્લેઈમ નં.",
        tbl_amount_claimed: "દાવો કરેલી રકમ",
        tbl_incident_date: "ઘટનાની તારીખ",
        tbl_priority: "પ્રાથમિકતા",
        tbl_receipt: "રસીદ નં.",
        tbl_amount: "ચૂકવેલ રકમ",
        tbl_method: "પદ્ધતિ",
        tbl_date: "તારીખ",
        tbl_term: "મુદત",

        kpi_total_cust: "કુલ ગ્રાહકો",
        kpi_active_agt: "સક્રિય એજન્ટો",
        kpi_active_pol: "સક્રિય પોલિસીઓ",
        kpi_pending_clm: "પેન્ડિંગ ક્લેઈમ",
        kpi_prem_coll: "એકત્રિત પ્રીમિયમ",
        kpi_settle_disb: "મંજૂર રકમ",

        bot_title: "ઈન્સ્યોરબોટ AI",
        bot_subtitle: "સક્રિય સહાયક",
        bot_placeholder: "સવાલ પૂછો...",
        bot_welcome: "👋 <strong>નમસ્તે! હું ઈન્સ્યોરબોટ છું</strong>, તમારો AI સહાયક.<br><br>પોલિસી પ્લાન, ક્લેઈમ સ્ટેટસ અથવા પેમેન્ટ વિશે પૂછો!",
        
        sug_policies: "કઈ પોલિસી યોજનાઓ ઉપલબ્ધ છે?",
        sug_claim_status: "ક્લેઈમ #1 ની સ્થિતિ તપાસો",
        sug_file_claim: "ક્લેઈમ કેવી રીતે દાખલ કરવો?",
        sug_dsa: "DSA એલ્ગોરિધમ્સ સમજાવો",

        footer_text: "ઈન્સ્યોરફ્લો © 2026 - વિમા વ્યવસ્થાપન સિસ્ટમ"
    },
    ta: {
        brand: "இன்ஷூர்-ப்ளோ",
        nav_dashboard: "டாஷ்போர்டு",
        nav_customers: "வாடிக்கையாளர்கள்",
        nav_agents: "முகவர்கள்",
        nav_policies: "பாளிசிகள்",
        nav_claims: "கோரிக்கைகள் (Claims)",
        nav_payments: "செலுத்தல்கள்",
        nav_renewals: "புதுப்பித்தல்கள்",

        role_label: "செயலில் உள்ள பங்கு:",
        role_admin: "நிர்வாகி",
        role_customer: "வாடிக்கையாளர்",
        role_agent: "முகவர்",
        select_profile: "சுயவிவரத்தைத் தேர்ந்தெடுக்கவும்:",

        btn_add_customer: "+ வாடிக்கையாளர் பதிவு",
        btn_add_agent: "+ முகவர் பதிவு",
        btn_issue_policy: "+ பாலிசி வழங்குதல்",
        btn_record_payment: "+ பிரீமியம் செலுத்துதல்",
        btn_file_claim: "+ உரிமை கோரல்",
        btn_cancel: "ரத்து செய்",
        btn_save: "சேமிக்கவும்",
        btn_submit: "சமர்ப்பிக்கவும்",
        btn_delete: "நீக்கு",
        btn_renew: "புதுப்பி",
        btn_assess: "மதிப்பிடு",
        btn_settle: "தீர்த்துவை",
        btn_pay: "செலுத்து",

        dash_title: "டாஷ்போர்டு பார்வை",
        dash_subtitle: "பாலிசிகள் மற்றும் கோரிக்கைகளின் நிலைக் குறிப்புகள்",
        cust_title: "வாடிக்கையாளர் மேலாண்மை",
        cust_subtitle: "வாடிக்கையாளர் பதிவு மற்றும் மேலாண்மை",
        agt_title: "முகவர் அடைவு",
        agt_subtitle: "முகவர்கள் மேலாண்மை",
        pol_title: "பாலிசி மேலாண்மை",
        pol_subtitle: "செயலில் உள்ள பாலிசிகள்",
        clm_title: "கோரிக்கைகள் மேலாண்மை",
        clm_subtitle: "கோரிக்கைகள் பரிசீலனை மற்றும் தீர்வு",
        pay_title: "பிரீமியம் செலுத்தல்கள்",
        pay_subtitle: "செலுத்தல் விவரங்கள்",
        rnw_title: "பாலிசி புதுப்பித்தல்கள்",
        rnw_subtitle: "புதுப்பித்தல் வரலாறு",

        tbl_code: "குறியீடு",
        tbl_name: "முழு பெயர்",
        tbl_email: "மின்னஞ்சல்",
        tbl_phone: "தொலைபேசி",
        tbl_city: "நகரம்",
        tbl_status: "நிலை",
        tbl_actions: "செயல்கள்",
        tbl_agency: "நிறுவன பெயர்",
        tbl_policy_num: "பாலிசி எண்",
        tbl_customer: "வாடிக்கையாளர்",
        tbl_agent: "முகவர்",
        tbl_type: "பாலிசி வகை",
        tbl_category: "பிரிவு",
        tbl_coverage: "காப்பீட்டு தொகை",
        tbl_premium: "பிரீமியம்",
        tbl_expiry: "காலாவதி தேதி",
        tbl_claim_num: "கோரிக்கை எண்",
        tbl_amount_claimed: "கோரப்பட்ட தொகை",
        tbl_incident_date: "சம்பவ தேதி",
        tbl_priority: "முன்னுரிமை",
        tbl_receipt: "ரசீது எண்",
        tbl_amount: "செலுத்தப்பட்ட தொகை",
        tbl_method: "முறை",
        tbl_date: "தேதி",
        tbl_term: "கால அளவு",

        kpi_total_cust: "மொத்த வாடிக்கையாளர்கள்",
        kpi_active_agt: "செயலில் உள்ள முகவர்கள்",
        kpi_active_pol: "செயலில் உள்ள பாலிசிகள்",
        kpi_pending_clm: "நிலுவை கோரிக்கைகள்",
        kpi_prem_coll: "சேகரிக்கப்பட்ட பிரீமியம்",
        kpi_settle_disb: "வழங்கப்பட்ட தொகை",

        bot_title: "இன்ஷூர்-பாட் AI",
        bot_subtitle: "செயலில் உள்ள உதவியாளர்",
        bot_placeholder: "கேள்வி கேட்கவும்...",
        bot_welcome: "👋 <strong>வணக்கம்! நான் இன்ஷூர்-பாட்</strong>, உங்கள் AI உதவியாளர்.<br><br>பாலிசி திட்டங்கள், கோரிக்கை நிலை அல்லது செலுத்துதல்கள் பற்றி கேட்கவும்!",
        
        sug_policies: "என்ன பாலிசி திட்டங்கள் உள்ளன?",
        sug_claim_status: "கோரிக்கை #1 நிலையைச் சரிபார்க்கவும்",
        sug_file_claim: "கோரிக்கை தாக்கல் செய்வது எப்படி?",
        sug_dsa: "DSA வழிமுறைகளை விளக்குங்கள்",

        footer_text: "இன்ஷூர்-ப்ளோ © 2026 - காப்பீட்டு மேலாண்மை அமைப்பு"
    }
};

let currentLanguage = localStorage.getItem('insureflow_lang') || 'en';

document.addEventListener('DOMContentLoaded', () => {
    initI18n();
});

function initI18n() {
    const selector = document.getElementById('language-select');
    if (selector) {
        selector.value = currentLanguage;
        selector.addEventListener('change', (e) => {
            changeLanguage(e.target.value);
        });
    }

    const botSelector = document.getElementById('bot-language-select');
    if (botSelector) {
        botSelector.value = currentLanguage;
        botSelector.addEventListener('change', (e) => {
            changeLanguage(e.target.value);
        });
    }

    applyLanguageTranslations(currentLanguage);
}

function changeLanguage(langCode) {
    if (!I18N_DICTIONARY[langCode]) return;
    currentLanguage = langCode;
    window.currentLanguage = langCode;
    localStorage.setItem('insureflow_lang', langCode);

    // Sync select dropdowns
    const selector = document.getElementById('language-select');
    if (selector) selector.value = langCode;
    const botSelector = document.getElementById('bot-language-select');
    if (botSelector) botSelector.value = langCode;

    applyLanguageTranslations(langCode);

    // Re-render active UI view to update dynamic tables, headers & buttons
    if (typeof renderActiveTab === 'function') {
        renderActiveTab();
    }
}

function applyLanguageTranslations(langCode) {
    const dict = I18N_DICTIONARY[langCode] || I18N_DICTIONARY['en'];

    // Translate DOM elements marked with data-i18n
    document.querySelectorAll('[data-i18n]').forEach(el => {
        const key = el.getAttribute('data-i18n');
        if (dict[key]) {
            if (el.tagName === 'INPUT' && el.type === 'text') {
                el.placeholder = dict[key];
            } else {
                el.innerHTML = dict[key];
            }
        }
    });

    // Update chatbot welcome message & placeholders
    const input = document.getElementById('chatbot-input');
    if (input && dict['bot_placeholder']) {
        input.placeholder = dict['bot_placeholder'];
    }
}

function t(key, fallback = '') {
    const dict = I18N_DICTIONARY[currentLanguage] || I18N_DICTIONARY['en'];
    return dict[key] || I18N_DICTIONARY['en'][key] || fallback || key;
}

window.t = t;
window.changeLanguage = changeLanguage;
