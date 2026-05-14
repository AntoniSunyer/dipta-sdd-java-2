Explanation
Understanding the problem
This error occurs in Vue 3 when a component enters an infinite update loop. 
Specifically, a reactive dependency is being modified by an effect (like a watcher, computed property, or lifecycle hook) that is also triggered by that same dependency. 
In your case, the stack trace indicates the loop is happening within the <RocketFleet> component, likely triggered by a v-model update (onUpdate:modelValue) originating from a child component in RocketForm.vue. 
When the value updates, it triggers a change that immediately forces another update to that same value, exceeding Vue's maximum recursive limit (usually 100) to prevent the browser from crashing.
How to verifyCheck Watchers: Look for a watch in RocketFleet.vue or RocketForm.vue that modifies the same variable it is watching.
Check Emits: In RocketForm.vue, check if an update:modelValue emit is being triggered by a prop change in a way that creates a circular dependency.
Check Lifecycle Hooks: See if updated() or beforeUpdate() modifies a state variable used in the template.Trace the Input: If this happens during a text input or toggle, check if you are manually setting the reactive variable to a new value inside an @input or @change handler that v-model is already managing.
Suggested fixThe most common cause is a watcher that updates its own source. You should ensure the state only updates if the value has actually changed.Incorrect (Recursive):jswatch(rocketData, (newValue) => {  // This triggers the watcher again immediately  rocketData.value.lastUpdated = Date.now(); });
Correct (Non-recursive):
If you must update the state based on its own changes, use a conditional check or move the logic to the method that initiates the change.jswatch(() => rocketData.value.status, (newStatus, oldStatus) => {  if (newStatus !== oldStatus) {    // Only perform logic if the specific sub-property changed    processStatusChange(newStatus);  }});
Use code snippets with cautionIf the error is caused by v-model in RocketForm.vue, ensure you aren't doing this:html<!-- Avoid this: updating the prop inside the update event --><CustomInput   :modelValue="props.modelValue"   @update:modelValue="emit('update:modelValue', $event); doSomethingThatMutatesProp();" />
Use code snippets with caution
Summary
The Cause: Your code is creating a "reactive feedback loop" where an update triggers itself.The Fix: Identify the watcher or event handler in <RocketFleet> or <RocketForm> that modifies a value it depends on, and add a conditional check to break the loop or move the mutation logic out of the reactive effect.
