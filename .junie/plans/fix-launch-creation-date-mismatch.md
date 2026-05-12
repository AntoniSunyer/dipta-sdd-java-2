---
sessionId: session-260512-123830-410j
isActive: true
---

# Requirements

### Overview & Goals
Status: Approved - Implementation requested.
The goal is to fix a bug where creating a new launch fails due to a validation error on the `launchDate` field. The root cause is a mismatch between the property name used in the frontend (`date`) and the one expected by the backend (`launchDate`), as well as a mismatch in the date format (date-only vs date-time).

### Scope
- **In Scope**:
    - Modifying the `Launch` model in the backend to match the frontend expectations.
    - Updating the backend service and tests to reflect these changes.
- **Out of Scope**:
    - Changing the frontend code (the current frontend implementation is considered the target contract).
    - Database migrations (not applicable as storage is in-memory).


# Technical Design

### Current Implementation
- **Frontend**: `LaunchForm.vue` sends a JSON payload with a `date` property (format: "YYYY-MM-DD").
- **Backend**: `Launch.java` model defines a `launchDate` field of type `LocalDateTime` with a `@NotNull` constraint.
- **Issue**: Jackson fails to map the `date` property to `launchDate`, leaving the latter as `null`. Additionally, "YYYY-MM-DD" cannot be directly parsed into `LocalDateTime` without time information.

### Key Decisions
1. **Rename field to `date`**: Align the Java field name with the JSON property name sent by the frontend to allow automatic mapping without extra annotations.
2. **Change type to `LocalDate`**: Use `LocalDate` instead of `LocalDateTime` since the frontend only provides date information, and `LocalDate` naturally handles the "YYYY-MM-DD" format.

### Proposed Changes
- **`Launch.java`**: Rename `launchDate` to `date`, change type to `LocalDate`, and update getters/setters/constructor.
- **`LaunchService.java`**: Update validation and logging to use the new `date` property.
- **`LaunchServiceTest.java`**: Update tests to use `LocalDate`.

### File Structure
- `back/src/main/java/academy/aicode/astrobookings/model/Launch.java`
- `back/src/main/java/academy/aicode/astrobookings/service/LaunchService.java`
- `back/src/test/java/academy/aicode/astrobookings/service/LaunchServiceTest.java`


# Implementation Plan (planify)

---
plan-type: bug-fix
tier: backend
---
## bug-fix - creating-launch - backend

### Step 1: Model Update
Update the Launch model to align with the frontend property names and data types.
- Paths:
    - back/src/main/java/academy/aicode/astrobookings/model/Launch.java
- [ ] Rename `launchDate` field to `date`.
- [ ] Change `launchDate` type from `LocalDateTime` to `LocalDate`.
- [ ] Update imports to include `java.time.LocalDate` and remove `java.time.LocalDateTime`.
- [ ] Update constructor, getter (`getDate`), and setter (`setDate`).

### Step 2: Service and Test Update
Update the business logic and unit tests to reflect the model changes.
- Paths:
    - back/src/main/java/academy/aicode/astrobookings/service/LaunchService.java
    - back/src/test/java/academy/aicode/astrobookings/service/LaunchServiceTest.java
- [ ] In `LaunchService.java`, update `validateLaunch` and logging to use `getDate()`.
- [ ] In `LaunchServiceTest.java`, update test cases to use `LocalDate.now()` and `setDate()`/`getDate()`.
- [ ] Verify that all backend tests pass.


# Delivery Steps

###   Step 1: Update Launch Model to align with Frontend
Update the `Launch` model in the backend to match the frontend property names and date format.
- Open `back/src/main/java/academy/aicode/astrobookings/model/Launch.java`.
- Rename the `launchDate` field to `date`.
- Change the field type from `LocalDateTime` to `LocalDate`.
- Update the class constructor to accept `LocalDate date`.
- Update the getter to `public LocalDate getDate()` and the setter to `public void setDate(LocalDate date)`.
- Adjust imports: remove `java.time.LocalDateTime` and add `java.time.LocalDate`.

###   Step 2: Update Service and Tests
Refactor the service layer and unit tests to use the updated model properties.
- Open `back/src/main/java/academy/aicode/astrobookings/service/LaunchService.java`.
- Update the `validateLaunch` method to check `launch.getDate()`.
- Update the info log in `createLaunch` to use `savedLaunch.getDate()`.
- Open `back/src/test/java/academy/aicode/astrobookings/service/LaunchServiceTest.java`.
- Update all test instances of `Launch` creation to use `LocalDate.now()` instead of `LocalDateTime.now()`.
- Update any calls to `getLaunchDate`/`setLaunchDate` to `getDate`/`setDate`.
- Run `mvn test` in the `back` directory to verify the changes.