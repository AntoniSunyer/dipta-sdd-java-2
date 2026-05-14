# Quality Report 

## Structural complexity

|File|Issue|Description|Severity|Recommendation|
|---|---|---|---|---|
|`front/src/components/RocketFleet.vue`|Mixed responsibilities|The component manages both the rocket registration form and the fleet catalog table.|Medium|Split into two separate components: `RocketForm.vue` and `RocketCatalog.vue`.|
|`front/src/components/LaunchDashboard.vue`|Logic in template|Complex conditional rendering for status actions is handled directly in the template.|Low|Extract action logic to computed properties or methods to improve readability.|
|`front/src/services/`|Inconsistent error handling|`healthService.js` uses try-catch, while others check `response.ok` and throw.|Medium|Centralize API call logic in a shared utility to ensure consistent error handling and logging.|

## Naming and readability

|File|Issue|Description|Severity|Recommendation|
|---|---|---|---|---|
|`front/src/services/healthService.js`|Generic variable names|Uses `data` and `error` which are non-descriptive.|Low|Rename to `healthData` or `fetchError` to clarify intent.|
|`front/src/components/*.vue`|Abbreviated catch variable|Common use of `e` in catch blocks.|Low|Use the full word `error` for better readability.|
|`front/src/services/*.js`|Hardcoded API URLs|`http://localhost:8080` is repeated across multiple files.|High|Extract to a single configuration file or environment variable.|

## Redundancy
|File|Issue|Description|Severity|Recommendation|
|---|---|---|---|---|
|`front/src/components/LaunchForm.vue`, `RocketFleet.vue`|Duplicated initial state|`resetForm` methods manually re-declare the initial state object.|Low|Extract the initial state to a constant and reuse it in both the declaration and the reset method.|
|`front/src/components/*.vue`|Duplicated error reporting|Both `error.value` and `notify()` are called together in every catch block.|Medium|Create a shared `useErrorHandler` composable or a helper function to centralize error reporting.|

> Report generated on 2026-05-14.
