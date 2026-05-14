import { ref } from 'vue';
import { notify } from '../services/notificationService';

export function useErrorHandler() {
  const errorMessage = ref(null);

  const handleError = (message, error) => {
    errorMessage.value = message;
    notify(message, 'error');
    if (error) {
      console.error(message, error);
    }
  };

  const clearError = () => {
    errorMessage.value = null;
  };

  return {
    error: errorMessage,
    handleError,
    clearError
  };
}
