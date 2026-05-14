<template>
  <div class="catalog">
    <h3>Rocket Catalog</h3>
    <table>
      <thead>
        <tr>
          <th>Name</th>
          <th>Capacity</th>
          <th>Range</th>
          <th>Status</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="rocket in rockets" :key="rocket.id">
          <td>{{ rocket.name }}</td>
          <td>{{ rocket.capacity }}</td>
          <td>{{ rocket.range }}</td>
          <td>{{ rocket.decommissioned ? 'Decommissioned' : 'Operational' }}</td>
          <td>
            <button @click="$emit('edit', rocket)" :disabled="rocket.decommissioned">Edit</button>
            <button @click="$emit('decommission', rocket.id)" :disabled="rocket.decommissioned">Decommission</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

defineProps({
  rockets: {
    type: Array,
    required: true
  }
});

defineEmits(['edit', 'decommission']);
</script>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}
th, td {
  border: 1px solid #ddd;
  padding: 8px;
}
</style>
