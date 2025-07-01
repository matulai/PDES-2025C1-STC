import { setup, runTestFlow } from './lib/common.js';


export let options = {
  vus: 50, // 50 usuarios concurrentes
  duration: '30m', // Ejecutar durante 30 minutos
  thresholds: {
    // El 99% de las peticiones deben ser menores a 1s
    'http_req_duration': ['p(99)<1000'],
    // La tasa de errores debe ser menor al 1%
    'http_req_failed': ['rate<0.01'],
  },
};

// Exportamos la función setup para que k6 la utilice.
export { setup };

export default function (data) {
  runTestFlow(data, 'soak');
}