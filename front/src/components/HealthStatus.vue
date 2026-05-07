<template>
  <div class="health-status">
    <h3>Backend Status</h3>
    <div v-if="loading">Checking status...</div>
    <div v-else>
      <p>Status: <span :class="statusClass">{{ health.status }}</span></p>
      <p>Uptime: {{ health.uptime }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { getHealthStatus } from '../services/healthService';

const health = ref({
  status: 'UNKNOWN',
  uptime: 'N/A'
});
const loading = ref(true);

const statusClass = computed(() => ({
  'status-up': health.value.status === 'UP',
  'status-down': health.value.status === 'DOWN',
  'status-unknown': health.value.status === 'UNKNOWN'
}));

onMounted(async () => {
  try {
    health.value = await getHealthStatus();
  } catch (e) {
    health.value = { status: 'DOWN', uptime: 'N/A' };
  } finally {
    loading.value = false;
  }
});
</script>

<style scoped>
.health-status {
  border: 1px solid #ddd;
  padding: 10px;
  border-radius: 8px;
  max-width: 300px;
  margin: 20px auto;
}
.status-up {
  color: green;
  font-weight: bold;
}
.status-down {
  color: red;
  font-weight: bold;
}
.status-unknown {
  color: gray;
}
</style>
