<template>
  <div class="rocket-fleet">
    <h2>Rockets Fleet Management</h2>
    
    <div v-if="error" class="error">{{ error }}</div>

    <RocketForm 
      v-model="form" 
      :is-editing="!!editingId" 
      @submit="handleSubmit" 
      @cancel="resetForm" 
    />

    <RocketCatalog 
      :rockets="rockets" 
      @edit="editRocket" 
      @decommission="handleDecommission" 
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getAllRockets, createRocket, updateRocket, decommissionRocket } from '../services/rocketService';
import { notify } from '../services/notificationService';
import { useErrorHandler } from '../composables/useErrorHandler';
import { INITIAL_ROCKET_FORM } from '../constants';
import RocketForm from './RocketForm.vue';
import RocketCatalog from './RocketCatalog.vue';

const rockets = ref([]);
const form = ref({ ...INITIAL_ROCKET_FORM });
const editingId = ref(null);
const { error, handleError, clearError } = useErrorHandler();

const fetchRockets = async () => {
  try {
    rockets.value = await getAllRockets();
    clearError();
  } catch (error) {
    handleError('Failed to load rockets', error);
  }
};

const resetForm = () => {
  editingId.value = null;
  form.value = { ...INITIAL_ROCKET_FORM };
  clearError();
};

const handleSubmit = async () => {
  try {
    if (editingId.value) {
      await updateRocket(editingId.value, form.value);
      notify('Rocket updated successfully!');
    } else {
      await createRocket(form.value);
      notify('Rocket registered successfully!');
    }
    await fetchRockets();
    resetForm();
  } catch (error) {
    handleError('Failed to save rocket', error);
  }
};

const handleDecommission = async (id) => {
  try {
    await decommissionRocket(id);
    notify('Rocket decommissioned successfully');
    await fetchRockets();
  } catch (error) {
    handleError('Failed to decommission rocket', error);
  }
};

const editRocket = (rocket) => {
  editingId.value = rocket.id;
  form.value = {
    name: rocket.name,
    capacity: rocket.capacity,
    range: rocket.range
  };
  clearError();
};

onMounted(fetchRockets);
</script>

<style scoped>
.rocket-fleet {
  max-width: 800px;
  margin: 0 auto;
  text-align: left;
}
.error {
  color: red;
  margin-bottom: 10px;
}
</style>
