# ArogyaGrid Backend

ArogyaGrid is an intelligent healthcare resource, inventory, and facility risk management backend built with Spring Boot.

## Architecture & Structure
- **config**: Security, CORS, OpenAPI, and Seed configurations
- **security**: JWT authentication, custom user details, and filter pipelines
- **entity**: JPA entities (Users, Facilities, Medicines, Inventory, Risks, Alerts, Redistributions, Audit Logs)
- **enums**: Domain enumerations (Roles, Risk Severities, Alert Statuses, etc.)
- **repository**: Spring Data JPA repositories
- **dto**: Requests and response payloads
- **service**: Business logic interfaces and implementations
- **controller**: REST API endpoints
- **exception**: Centralized exception handling
