---
sessionId: session-260514-120921-1h0w
isActive: true
---

# Requirements balance-check

### Overview & Goals
The goal is to fix the "Maximum recursive updates exceeded" error occurring in the `RocketFleet` component. This error is caused by an infinite reactive loop (feedback loop) between the parent component (`RocketFleet`) and its child component (`RocketForm`) when synchronizing form data via `v-model`.

### Scope
- **In Scope**:
  - `front/src/components/RocketForm.vue`: Fix the circular dependency in its watchers by implementing structural guards.
- **Out of Scope**:
  - Changes to the backend.
  - Changes to other frontend components (unless they exhibit the same pattern).

### Feedback Loop Analysis
Based on the error report and existing code:
1. Parent (`RocketFleet`) updates its `form` state.
2. `RocketForm` receives new `modelValue` prop.
3. `RocketForm` prop watcher triggers and updates its local `form` ref with a new object reference (`{...newValue}`).
4. `RocketForm` local `form` watcher triggers (because of the new reference/deep watch) and emits `update:modelValue`.
5. Parent receives the event and updates its `form` state with a new reference.
6. The cycle repeats indefinitely, exceeding the maximum recursion depth.


# Technical Design

### Current Implementation
The `RocketForm` component uses a two-way synchronization pattern for its `modelValue` prop:
1. A watcher on `props.modelValue` updates a local `form` ref.
2. A deep watcher on the local `form` ref emits an `update:modelValue` event.

The lack of structural comparison in these watchers causes them to trigger each other even when the actual data hasn't changed, leading to an infinite loop.

### Key Decisions
- **Structural Comparison**: Using `JSON.stringify` for comparison is a simple and effective way to perform a deep check on the flat rocket form object. This ensures the state only updates if the values actually changed.
- **Two-way Guarding**: Adding guards to both the prop-to-local and local-to-prop watchers provides robust protection against recursive updates from either side.

### Proposed Changes
Modify `front/src/components/RocketForm.vue` to introduce guards in the watchers.

#### Watcher 1: Sync from parent to child
Only update the local form if the incoming prop is structurally different.
```javascript
watch(() => props.modelValue, (newValue) => {
  if (JSON.stringify(newValue) !== JSON.stringify(form.value)) {
    form.value = { ...newValue };
  }
}, { deep: true });
```

#### Watcher 2: Sync from child to parent
Only emit an update if the local state is structurally different from the prop.
```javascript
watch(form, (newValue) => {
  if (JSON.stringify(newValue) !== JSON.stringify(props.modelValue)) {
    emit('update:modelValue', newValue);
  }
}, { deep: true });
```


# Testing

### Validation Approach
The fix will be validated by manually verifying that the recursion error no longer occurs when interacting with the Rocket Fleet management interface.

### Key Scenarios
- **Load Rocket Fleet**: Ensure the page loads without the "Maximum recursive updates exceeded" error in the console.
- **Edit Rocket**: Click "Edit" on a rocket. Verify the form is populated and no console errors appear.
- **Update Rocket**: Modify values in the form and verify the changes are reflected and saved correctly.
- **Reset Form**: Click "Cancel" or submit the form. Verify it resets correctly without triggering a loop.


# Delivery Steps

###   Step 1: Implement reactive guard in RocketForm prop watcher
Prevent the infinite loop by ensuring the local form state only updates when the incoming `modelValue` prop has changed structurally.

- Open `front/src/components/RocketForm.vue`.
- Update the watcher for `props.modelValue`.
- Add a conditional check using `JSON.stringify(newValue) !== JSON.stringify(form.value)`.
- Only update `form.value` if the data is different.

###   Step 2: Implement reactive guard in RocketForm emit watcher
Ensure that changes to the local form state only emit an update event to the parent if the data differs from the current prop value.

- In `front/src/components/RocketForm.vue`, update the deep watcher on the `form` ref.
- Add a conditional check using `JSON.stringify(newValue) !== JSON.stringify(props.modelValue)`.
- Only call `emit('update:modelValue', newValue)` if the data is different.
- This provides an extra layer of protection against recursive updates.