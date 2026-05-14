---
plan-type: report
tier: back
---
# report - back-quality - back

## Specification

Implementation plan to address the defects identified in the backend quality report, focusing on architectural consistency, naming conventions, and redundancy.

**Context**: [.product/reports/back.quality.report.md](../reports/back.quality.report.md)

## Implementation Steps

### Step 1: Standardize Enums and Constants
Follow Java naming conventions and improve readability of time-related logic.
- Paths:
    - `back/src/main/java/academy/aicode/astrobookings/model/LaunchStatus.java`
    - `back/src/main/java/academy/aicode/astrobookings/model/RocketRange.java`
    - `back/src/main/java/academy/aicode/astrobookings/controller/HealthController.java`
- [ ] Rename `LaunchStatus` constants to `UPPER_SNAKE_CASE` (e.g., `CREATED`, `CONFIRMED`).
- [ ] Rename `RocketRange` constants to `UPPER_SNAKE_CASE` (e.g., `EARTH`, `MARS`).
- [ ] Refactor `HealthController.formatUptime` to use `java.time.Duration` or defined constants instead of magic numbers.

### Step 2: Centralize Infrastructure and Storage
Eliminate duplication in CORS configuration and repository implementation.
- Paths:
    - `back/src/main/java/academy/aicode/astrobookings/config/WebConfig.java`
    - `back/src/main/java/academy/aicode/astrobookings/controller/`
    - `back/src/main/java/academy/aicode/astrobookings/repository/InMemoryRepository.java`
    - `back/src/main/java/academy/aicode/astrobookings/repository/RocketRepository.java`
    - `back/src/main/java/academy/aicode/astrobookings/repository/LaunchRepository.java`
- [ ] Create `WebConfig` class with a `WebMvcConfigurer` bean to centralize CORS settings.
- [ ] Remove `@CrossOrigin` annotations from all controller classes.
- [ ] Implement `InMemoryRepository<T>` base class to handle generic Map-based CRUD operations.
- [ ] Refactor `RocketRepository` and `LaunchRepository` to extend `InMemoryRepository`.

### Step 3: Refactor Services and Validation
Simplify services by leveraging Jakarta Validation and strengthening business rules.
- Paths:
    - `back/src/main/java/academy/aicode/astrobookings/service/RocketService.java`
    - `back/src/main/java/academy/aicode/astrobookings/service/LaunchService.java`
- [ ] Remove manual `validate*` methods in services and rely on `@Valid` at the controller level.
- [ ] Add a check in `RocketService.updateRocket` to prevent modifications to decommissioned rockets.

### Step 4: Standardize API Responses and Error Handling
Improve error handling robustness and API consistency.
- Paths:
    - `back/src/main/java/academy/aicode/astrobookings/exception/ResourceNotFoundException.java`
    - `back/src/main/java/academy/aicode/astrobookings/controller/GlobalExceptionHandler.java`
    - `back/src/main/java/academy/aicode/astrobookings/controller/LaunchController.java`
- [ ] Create `ResourceNotFoundException`.
- [ ] Refactor `GlobalExceptionHandler` to handle `ResourceNotFoundException` directly instead of matching strings.
- [ ] Update `LaunchController` to use `ResponseEntity` for all endpoints, matching the pattern in `RocketController`.
