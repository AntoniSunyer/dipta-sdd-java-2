<template>
  <img alt="Vue logo" src="./assets/logo.png">
  <HealthStatus />
  <RocketFleet />
</template>

<script setup>
import HealthStatus from './components/HealthStatus.vue'
import RocketFleet from './components/RocketFleet.vue'
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
