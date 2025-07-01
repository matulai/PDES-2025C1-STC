
import { setup, runTestFlow } from './lib/common.js';

export let options = {
  stages: [
    { duration: '2m', target: 100 }, // Aumenta hasta 100 usuarios en 2 minutos
    { duration: '3m', target: 200 }, // Aumenta hasta 200 usuarios en 3 minutos
    { duration: '2m', target: 0 }, // Disminuye a 0 usuarios
  ],
  thresholds: {
    // El 95% de las peticiones deben ser menores a 1.5s
    'http_req_duration': ['p(95)<1500'],
    // La tasa de errores debe ser menor al 5%
    'http_req_failed': ['rate<0.05'],
  },
};

// Exportamos la función setup para que k6 la utilice.
export { setup };

export default function (data) {
  runTestFlow(data, 'Stress');
}