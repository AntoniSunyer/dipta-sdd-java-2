import { BASE_URL } from '../config';
import { apiClient } from './apiClient';

export async function getHealthStatus() {
  try {
    const healthData = await apiClient('/health', { baseUrl: BASE_URL });
    return healthData;
  } catch (error) {
    console.error('There was a problem with the fetch operation:', error);
    return { status: 'DOWN', uptime: 'N/A' };
  }
}
