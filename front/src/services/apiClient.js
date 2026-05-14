import { API_BASE_URL } from '../config';

export async function apiClient(endpoint, { body, baseUrl, ...customConfig } = {}) {
  const base = baseUrl || API_BASE_URL;
  const config = {
    method: customConfig.method || (body ? 'POST' : 'GET'),
    headers: {
      ...(body ? { 'Content-Type': 'application/json' } : {}),
      ...customConfig.headers,
    },
    ...customConfig,
  };

  if (body) {
    config.body = JSON.stringify(body);
  }

  const response = await fetch(`${base}${endpoint}`, config);

  if (!response.ok) {
    const errorData = await response.json().catch(() => ({}));
    const errorMessage = errorData.message || response.statusText || 'Request failed';
    throw new Error(errorMessage);
  }

  if (response.status === 204) {
    return null;
  }

  return await response.json();
}
