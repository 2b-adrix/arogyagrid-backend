# 🏥 ArogyaGrid Backend (आरोग्य ग्रिड)

> **Enterprise Spring Boot 3.3.5 Backend for Distributed Healthcare Logistics, Algorithmic Stockout Risk Mitigation & Inter-Facility Emergency Redistribution**  
> **Deployed Live URL**: `https://arogyagrid-backend-aus8.onrender.com/api`  
> **Interactive Swagger UI**: `https://arogyagrid-backend-aus8.onrender.com/api/swagger-ui/index.html`  
> **OpenAPI JSON Spec**: `https://arogyagrid-backend-aus8.onrender.com/api/v3/api-docs`  
> **Cloud Database**: AWS Neon Serverless PostgreSQL (`us-east-2`)  
> **Full Architecture Spec**: See [AROGYAGRID_FULL_STACK_DOCUMENTATION.md](file:///C:/Users/vadit/OneDrive/Desktop/arogyagrid-backend/AROGYAGRID_FULL_STACK_DOCUMENTATION.md)

---

## ⚡ Quick Start

### Prerequisites
* Java 17+ (e.g., OpenJDK 17 or Temurin)
* Maven 3.8+ (or use included `mvnw`)

### Build & Run Locally
```bash
# Clean and package without running tests
mvn clean package -DskipTests

# Run the Spring Boot application
mvn spring-boot:run
```
*Access local Swagger UI*: `http://localhost:8080/api/swagger-ui.html`

---

## 🏗️ Architectural Overview & Package Map

The backend is built with Spring Boot 3.3.5, Java 17, and Spring Security 6 with JWT (`jjwt 0.12.6`).

```text
com.arogyagrid/
├── config/             # SecurityConfig (RBAC), CorsConfig, OpenApiConfig, DataSeeder
├── security/           # JwtTokenProvider, JwtAuthenticationFilter, CustomUserDetailsService
├── entity/             # JPA Entities: User, Facility, Medicine, InventoryBalance,
│                       # InventoryTransaction, RiskAssessment, Alert,
│                       # RedistributionRecommendation, AuditLog
├── enums/              # UserRole, RiskSeverity, FacilityType, TransactionType, Criticality
├── repository/         # Spring Data JPA Repositories
├── dto/
│   ├── request/        # LoginRequest, RegisterRequest, InventoryTransactionRequest, etc.
│   └── response/       # ApiResponse<T>, FacilityResponse, RiskResponse, DashboardResponse, etc.
├── service/            # Business Logic & Mathematical Engines:
│                       # InventoryService (Haversine Distance Engine)
│                       # RiskAssessmentService (Algorithmic Stockout Index)
│                       # RedistributionService (Priority Score Engine)
│                       # DashboardService, FacilityService, AiAssistantService, AuditLogService
├── controller/         # 11 REST Controllers under /api/*
└── exception/          # Centralized GlobalExceptionHandler
```

---

## 🔐 Role-Based Access Control (RBAC)

| Role | Permissions & Authority |
| :--- | :--- |
| `ROLE_STATE_ADMIN` | Full administrative read/write access across all districts and state audit log review. |
| `ROLE_DISTRICT_OFFICER` | Requisition approval/rejection, Green Corridor transit authorization, bed management. |
| `ROLE_FACILITY_MANAGER` | Local facility bed census, staff allocation, and local alert management. |
| `ROLE_PHARMACIST_OPERATOR` | e-Aushadhi stock receipt, stock issue, and daily ledger transactions. |

---

## 📊 Core Mathematical Engines

1. **Haversine Distance Formula**: Computes exact surface distance between hospitals (e.g., SMS Hospital to PHC Amer: 10.9 km).
2. **Algorithmic Stockout Risk Index**: Calculates days of coverage from daily consumption and weights by EDL criticality (`VITAL` = 2.0, `ESSENTIAL` = 1.5, `DESIRABLE` = 1.0).
3. **Redistribution Priority Score**: Dynamically scores transfer urgency (0–100) combining deficit ratio and geospatial proximity.

---

## 📖 Complete Documentation
For end-to-end Mermaid architectural diagrams, database ERD, sequence diagrams, state machines, and Android client documentation, refer to:  
👉 **[AROGYAGRID_FULL_STACK_DOCUMENTATION.md](file:///C:/Users/vadit/OneDrive/Desktop/arogyagrid-backend/AROGYAGRID_FULL_STACK_DOCUMENTATION.md)**
