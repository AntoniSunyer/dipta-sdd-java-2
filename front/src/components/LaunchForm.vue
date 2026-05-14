<template>
  <div class="launch-form">
    <h3>Schedule New Launch</h3>
    <div v-if="error" class="error">{{ error }}</div>
    <form @submit.prevent="handleSubmit">
      <div>
        <label>Rocket:</label>
        <select v-model="form.rocketId" required>
          <option v-for="rocket in rockets" :key="rocket.id" :value="rocket.id">
            {{ rocket.name }} (Capacity: {{ rocket.capacity }})
          </option>
        </select>
      </div>
      <div>
        <label>Launch Date:</label>
        <input v-model="form.launchDate" type="date" required />
      </div>
      <div>
        <label>Price:</label>
        <input v-model.number="form.price" type="number" min="0" step="0.01" required />
      </div>
      <div>
        <label>Min Occupancy:</label>
        <input v-model.number="form.minOccupancy" type="number" min="1" required />
      </div>
      <button type="submit">Schedule Launch</button>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted, defineEmits } from 'vue';
import { getAllRockets } from '../services/rocketService';
import { createLaunch } from '../services/launchService';
import { notify } from '../services/notificationService';
import { useErrorHandler } from '../composables/useErrorHandler';
import { INITIAL_LAUNCH_FORM } from '../constants';

const emit = defineEmits(['launch-created']);

const rockets = ref([]);
const form = ref({ ...INITIAL_LAUNCH_FORM });
const { error, handleError, clearError } = useErrorHandler();

const fetchRockets = async () => {
  try {
    const allRockets = await getAllRockets();
    rockets.value = allRockets.filter(r => !r.decommissioned);
    if (rockets.value.length > 0 && !form.value.rocketId) {
      form.value.rocketId = rockets.value[0].id;
    }
    clearError();
  } catch (error) {
    handleError('Failed to load rockets', error);
  }
};

const handleSubmit = async () => {
  try {
    await createLaunch(form.value);
    notify('Launch scheduled successfully!');
    emit('launch-created');
    resetForm();
  } catch (error) {
    handleError('Failed to schedule launch', error);
  }
};

const resetForm = () => {
  form.value = {
    ...INITIAL_LAUNCH_FORM,
    rocketId: rockets.value.length > 0 ? rockets.value[0].id : ''
  };
  clearError();
};

onMounted(fetchRockets);
</script>

<style scoped>
.launch-form {
  border: 1px solid #ccc;
  padding: 15px;
  margin-bottom: 20px;
}
.error {
  color: red;
}
form div {
  margin-bottom: 10px;
}
label {
  display: inline-block;
  width: 120px;
}
</style>
