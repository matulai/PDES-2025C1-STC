import { setup, runTestFlow } from './lib/common.js';

export let options = {
  setupTimeout: '10m',
  stages: [
    { duration: '10s', target: 200 }, // Súbito aumento a 200 usuarios
    { duration: '30s', target: 200 }, // Mantiene la carga
    { duration: '10s', target: 0 }, // Disminuye rápidamente
  ],
  thresholds: {
    // El 90% de las peticiones deben ser menores a 2s
    'http_req_duration': ['p(90)<2000'],
    // La tasa de errores debe ser menor al 10%
    'http_req_failed': ['rate<0.1'],
  },
};

// Exportamos la función setup para que k6 la utilice.
export { setup };

export default function (data) {
  runTestFlow(data, 'spike');
}
