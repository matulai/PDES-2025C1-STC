import { setup, runTestFlow } from './lib/common.js';

export let options = {
  stages: [
    { duration: '5m', target: 50 },
    { duration: '5m', target: 100 },
    { duration: '5m', target: 200 },
    { duration: '5m', target: 400 },
  ],
  thresholds: {
    // El 95% de las peticiones deben ser menores a 1.2s
    'http_req_duration': ['p(95)<1500'],
    // La tasa de errores debe ser menor al 3%
    'http_req_failed': ['rate<0.03'],
  },
};

// Exportamos la función setup para que k6 la utilice.
export { setup };

export default function (data) {
  runTestFlow(data, 'Scalability');
}
