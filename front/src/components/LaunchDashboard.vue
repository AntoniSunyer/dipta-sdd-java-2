<template>
  <div class="launch-dashboard">
    <h2>Launch Dashboard</h2>
    <div v-if="error" class="error">{{ error }}</div>
    
    <table>
      <thead>
        <tr>
          <th>Rocket ID</th>
          <th>Launch Date</th>
          <th>Price</th>
          <th>Min Occupancy</th>
          <th>Status</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="launch in processedLaunches" :key="launch.id">
          <td>{{ launch.rocketId }}</td>
          <td>{{ launch.launchDate }}</td>
          <td>{{ launch.price }}</td>
          <td>{{ launch.minOccupancy }}</td>
          <td>{{ launch.status }}</td>
          <td>
            <button 
              v-if="launch.canConfirm" 
              @click="handleUpdateStatus(launch.id, 'CONFIRMED')"
            >
              Confirm
            </button>
            <button 
              v-if="launch.canComplete" 
              @click="handleUpdateStatus(launch.id, 'Completed')"
            >
              Complete
            </button>
            <button 
              v-if="launch.canCancel" 
              @click="handleUpdateStatus(launch.id, 'CANCELLED')"
            >
              Cancel
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, defineExpose } from 'vue';
import { getAllLaunches, updateLaunchStatus } from '../services/launchService';
import { notify } from '../services/notificationService';
import { useErrorHandler } from '../composables/useErrorHandler';

const launches = ref([]);
const { error, handleError, clearError } = useErrorHandler();

const processedLaunches = computed(() => {
  return launches.value.map(launch => ({
    ...launch,
    canConfirm: launch.status === 'CREATED',
    canComplete: launch.status === 'CONFIRMED',
    canCancel: launch.status !== 'CANCELLED' && launch.status !== 'Completed'
  }));
});

const fetchLaunches = async () => {
  try {
    launches.value = await getAllLaunches();
    clearError();
  } catch (error) {
    handleError('Failed to load launches', error);
  }
};

const handleUpdateStatus = async (id, status) => {
  try {
    await updateLaunchStatus(id, status);
    notify(`Launch status updated to ${status}`);
    await fetchLaunches();
  } catch (error) {
    handleError(`Failed to update status to ${status}`, error);
  }
};

onMounted(fetchLaunches);

defineExpose({ fetchLaunches });
</script>

<style scoped>
.launch-dashboard {
  margin-top: 20px;
}
.error {
  color: red;
}
table {
  width: 100%;
  border-collapse: collapse;
}
th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}
button {
  margin-right: 5px;
}
</style>
