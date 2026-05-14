import { apiClient } from './apiClient';

const LAUNCHES_ENDPOINT = '/launches';

export async function getAllLaunches() {
  console.info('Fetching all launches');
  const launchesData = await apiClient(LAUNCHES_ENDPOINT);
  return launchesData;
}

export async function getLaunchById(id) {
  console.info(`Fetching launch with id: ${id}`);
  const launchData = await apiClient(`${LAUNCHES_ENDPOINT}/${id}`);
  return launchData;
}

export async function createLaunch(launch) {
  console.info('Creating new launch', launch);
  const createdLaunch = await apiClient(LAUNCHES_ENDPOINT, { body: launch });
  console.info('Launch created successfully', createdLaunch);
  return createdLaunch;
}

export async function updateLaunchStatus(id, status) {
  console.info(`Updating status for launch ${id} to ${status}`);
  const updatedLaunch = await apiClient(`${LAUNCHES_ENDPOINT}/${id}/status`, {
    method: 'PATCH',
    body: status
  });
  console.info(`Launch status updated successfully for ${id}`, updatedLaunch);
  return updatedLaunch;
}
