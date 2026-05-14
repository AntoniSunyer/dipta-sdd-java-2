# Quality Report 

## Structural complexity

|File|Issue|Description|Severity|Recommendation|
|---|---|---|---|---|
|`back/src/main/java/academy/aicode/astrobookings/controller/GlobalExceptionHandler.java`|Fragile error handling|Matches "not found" substring in exception messages to determine HTTP 404 status.|Medium|Introduce custom exceptions (e.g., `ResourceNotFoundException`) and handle them specifically.|
|`back/src/main/java/academy/aicode/astrobookings/service/RocketService.java`, `.../LaunchService.java`|Redundant manual validation|The `validate*` methods manually check constraints that are already defined via Jakarta Validation annotations in models.|Low|Remove manual validation logic and rely on `@Valid` at the controller level.|
|`back/src/main/java/academy/aicode/astrobookings/service/RocketService.java`|Incomplete business logic|`updateRocket` allows updating a decommissioned rocket.|Low|Add a check to prevent updates on decommissioned rockets if business rules require it.|

## Naming and readability

|File|Issue|Description|Severity|Recommendation|
|---|---|---|---|---|
|`back/src/main/java/academy/aicode/astrobookings/model/LaunchStatus.java`, `.../RocketRange.java`|Non-standard enum naming|Enum constants use CamelCase (e.g., `Created`, `Earth`) instead of standard UPPER_SNAKE_CASE.|Low|Follow Java conventions and use `CREATED`, `EARTH`, etc.|
|`back/src/main/java/academy/aicode/astrobookings/controller/LaunchController.java`|Inconsistent API response pattern|Unlike `RocketController`, it doesn't use `ResponseEntity` for single resource retrieval, relying on implicit exception handling.|Low|Adopt a consistent pattern for resource retrieval across all controllers.|
|`back/src/main/java/academy/aicode/astrobookings/controller/HealthController.java`|Hardcoded time constants|The `formatUptime` method uses magic numbers (1000, 60, 24) for time conversion.|Low|Use `java.time.Duration` or define constants for better readability.|

## Redundancy
|File|Issue|Description|Severity|Recommendation|
|---|---|---|---|---|
|`back/src/main/java/academy/aicode/astrobookings/controller/*.java`|Duplicated CORS configuration|`@CrossOrigin(origins = "*")` is repeated on every controller class.|Medium|Centralize CORS configuration in a `WebMvcConfigurer` bean.|
|`back/src/main/java/academy/aicode/astrobookings/repository/*.java`|Duplicated storage logic|Both repositories implement the exact same in-memory Map logic.|Medium|Extract a generic `InMemoryRepository<T>` base class to handle common CRUD operations.|

> Report generated on 2026-05-14.
