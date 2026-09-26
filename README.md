# InsureFlow - Insurance Policy & Claims Management System

[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen.svg)](https://github.com/mdeshmukh1111-hub/Insurance-Policy-Claims-Management-System-InsureFlow)
[![Tech Stack](https://img.shields.io/badge/Stack-Java_17_|_Spring_Boot_3.3_|_MySQL_8.0_|_Vanilla_JS-059669.svg)](#technology-stack)
[![Single Port](https://img.shields.io/badge/Port-8080-emerald.svg)](http://localhost:8080/)
[![Course Project](https://img.shields.io/badge/Project-Computer_Engineering_(Java_+_DSA_+_DBMS)-10b981.svg)](#dsa-implementations)

**InsureFlow** is a complete, full-stack enterprise Insurance Policy & Claims Management System designed specifically as a Computer Engineering college capstone project (Java + DSA + DBMS). 

It provides an end-to-end digital lifecycle for policy issuance, premium payment processing, policy renewals, insurance claims submission, technical assessment, and financial settlement, complete with role-based dashboards for **Admin**, **Agent**, and **Customer**.

---

## Key Features & Highlights

- **Single-Port Integrated Deployment**: Spring Boot serves both backend REST APIs and the rich modern frontend from a single port (`http://localhost:8080/`).
- **Modern Non-Blue Aesthetic**: Styled with a tailored Emerald Green & Charcoal glassmorphism palette (`#059669`, `#10b981`, `#0f172a`).
- **Multi-Role Access Control (RBAC)**: Switch seamlessly between Admin, Customer, and Agent interactive views.
- **Complete Insurance Workflow**:
  `Select Policy Type → Create Policy → Pay Premium → Policy Active → Renewal Due → Renew Contract → File Claim → Under Review → Assess Claim → Approve / Reject → Disburse Settlement`
- **Real-time Analytics Dashboards**: Live KPI metrics cards, recent transaction ledgers, interactive modal forms, and status badges.
- **Interactive DSA Visualizer & Playground**: Live benchmarking console to demonstrate custom Java DSA implementations.
- **DBMS Evaluation Module**: 3NF verified schema, ER model, 25+ SQL queries, and MySQL Stored Procedures, Functions, Triggers, and Cursors.

---

## Technology Stack

| Layer | Technology |
| :--- | :--- |
| **Backend Core** | Java 17+, Spring Boot 3.3.0, Spring Data JPA, Hibernate |
| **Database** | MySQL 8.0 (Normalized to 3NF) |
| **Build & Tooling** | Maven |
| **Frontend** | Vanilla HTML5, Modern CSS3 (Emerald Palette, NO Blue), JavaScript (ES6 Fetch API) |
| **Architecture** | Layered Enterprise (Controller, Service, Repository, Entity, DTO, Exception) |

---

## Architecture & Layered Design

```
src/main/java/com/insureflow/
├── InsureFlowApplication.java
├── config/              # Spring & Data Initializer Configuration
├── controller/          # REST API Controllers (/api/customers, /api/policies, etc.)
├── dto/                 # Request & Response Data Transfer Objects
├── dsa/                 # Custom Java Data Structures & Algorithms
├── entity/              # JPA Relational Entities
├── exception/           # Global Exception Handler & Custom Exceptions
├── repository/         # Spring Data JPA Repositories
└── service/             # Business Logic & Service Implementations
```

---

## Database Schema & Normalization (3NF)

The database consists of **10 normalized relational tables**:

1. `CUSTOMERS`: Primary key `id`, unique `customer_code` and `email`.
2. `AGENTS`: Primary key `id`, unique `agent_code` and `email`.
3. `POLICY_TYPES`: Primary key `id`, unique `type_code`.
4. `POLICIES`: Primary key `id`, FK to Customer, Agent, PolicyType.
5. `PREMIUM_PAYMENTS`: Primary key `id`, FK to Policy.
6. `RENEWALS`: Primary key `id`, FK to Policy, Agent.
7. `CLAIMS`: Primary key `id`, FK to Policy, Customer.
8. `CLAIM_DOCUMENTS`: Primary key `id`, FK to Claim.
9. `CLAIM_ASSESSMENTS`: Primary key `id`, FK (Unique) to Claim.
10. `CLAIM_SETTLEMENTS`: Primary key `id`, FK (Unique) to Claim.

### DBMS Deliverables (`/docs` Directory)
- **`docs/ER_DIAGRAM.md`**: Complete ER model specification with Mermaid diagrams.
- **`docs/NORMALIZATION.md`**: Detailed 1NF, 2NF, 3NF decomposition analysis.
- **`docs/SQL_QUERIES.sql`**: 25+ complex SQL queries covering JOINs, Subqueries, Aggregations, GROUP BY, HAVING, and Views.
- **`docs/DATABASE_PROGRAMMING.sql`**: MySQL PL/SQL features:
  - `sp_process_claim_settlement` (Stored Procedure)
  - `fn_calculate_total_customer_premium` (Stored Function)
  - `trg_update_policy_status_on_renewal` (Trigger)
  - `sp_generate_agent_statistics` (Stored Procedure using Cursors)

---

## DSA Implementations & Complexity

Located in `com.insureflow.dsa` and demonstrable via the **DSA Visualizer Tab**:

1. **Hashing (`CustomHashMap`)**: Custom separate-chaining Hash Table for $O(1)$ policy number lookups.
2. **Searching (`LinearBinarySearch`)**: Linear Search $O(N)$ vs Binary Search $O(\log N)$ benchmarking on sorted arrays.
3. **Sorting (`QuickSort`)**: Lomuto partition QuickSort algorithm to sort policies/claims by premium, coverage, or amount.
4. **Singly Linked List (`CustomLinkedList`)**: Sequential audit log tracking claim processing history.
5. **Queue (`CustomQueue`)**: First-In-First-Out (FIFO) queue for pending claim submission processing.
6. **Priority Queue / Max Heap (`CustomPriorityQueue`)**: Priority Queue prioritizing claims by risk, severity, amount, and waiting days ($O(\log N)$).
7. **Binary Search Tree (`PolicyBST`)**: Policy insertion and In-Order traversal ($O(\log N)$).
8. **Graph & BFS/DFS (`InsuranceGraph`)**: Adjacency list representation of Customer $\rightarrow$ Policy $\rightarrow$ Claim networks with Breadth-First and Depth-First search.

---

## REST API Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/customers` | Fetch all customers / search by keyword |
| `POST` | `/api/customers` | Register a new customer |
| `GET` | `/api/agents` | Fetch all active insurance agents |
| `POST` | `/api/agents` | Register a new insurance agent |
| `GET` | `/api/policy-types` | Fetch policy plan categories |
| `GET` | `/api/policies` | Fetch policies / filter by status or customer |
| `POST` | `/api/policies` | Issue a new insurance policy contract |
| `POST` | `/api/payments` | Record premium payment & activate policy |
| `POST` | `/api/renewals` | Process policy contract renewal |
| `GET` | `/api/claims` | List claims / filter by priority or status |
| `POST` | `/api/claims` | Submit a new insurance claim |
| `POST` | `/api/claim-assessments` | Submit agent technical assessment |
| `POST` | `/api/claim-settlements` | Disburse claim settlement |
| `GET` | `/api/dashboard/admin` | Fetch Admin KPI metrics |
| `GET` | `/api/dsa/{concept}` | Run live DSA benchmark test |

---

## How to Run the Application Locally

### 1. Prerequisites
- **Java JDK 17** or higher (Java 26 supported)
- **MySQL Server 8.0** running on port `3306`
- **Maven** (or Maven Wrapper)

### 2. Database Configuration
Create the MySQL database:
```sql
CREATE DATABASE IF NOT EXISTS insureflow_db;
```

Update `src/main/resources/application.properties` with your local MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/insureflow_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
```
*(Or set environment variable `DB_PASSWORD=your_password`)*

### 3. Build & Run Application
Run using Maven:
```bash
mvn spring-boot:run
```
*(Or on Windows PowerShell with Maven Wrapper if installed: `.\mvnw spring-boot:run`)*

### 4. Access the Application
Open your web browser and navigate to:
**[http://localhost:8080/](http://localhost:8080/)**

The application automatically seeds realistic sample data on first boot!

---

## Screenshots

*(Dashboard, Customer Management, Policy Issue, Claims Assessment, and DSA Visualizer views)*

---

## License & Credits
Developed for **Computer Engineering (Java + DSA + DBMS) College Evaluation**.  
Author: [mdeshmukh1111-hub](https://github.com/mdeshmukh1111-hub)
