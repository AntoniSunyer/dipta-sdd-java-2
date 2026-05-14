import { apiClient } from './apiClient';

const ROCKETS_ENDPOINT = '/rockets';

export async function getAllRockets() {
  console.info('Fetching all rockets');
  const rocketsData = await apiClient(ROCKETS_ENDPOINT);
  return rocketsData;
}

export async function createRocket(rocket) {
  console.info('Creating new rocket', rocket);
  const createdRocket = await apiClient(ROCKETS_ENDPOINT, { body: rocket });
  console.info('Rocket created successfully', createdRocket);
  return createdRocket;
}

export async function updateRocket(id, rocket) {
  console.info(`Updating rocket ${id}`, rocket);
  const updatedRocket = await apiClient(`${ROCKETS_ENDPOINT}/${id}`, {
    method: 'PUT',
    body: rocket
  });
  console.info(`Rocket ${id} updated successfully`, updatedRocket);
  return updatedRocket;
}

export async function decommissionRocket(id) {
  console.info(`Decommissioning rocket ${id}`);
  await apiClient(`${ROCKETS_ENDPOINT}/${id}/decommission`, {
    method: 'PATCH'
  });
  console.info(`Rocket ${id} decommissioned successfully`);
}
