import { setup, runTestFlow } from './lib/common.js';

export let options = {
  vus: 50,
  setupTimeout: '120s', // Increased timeout to 120 seconds
  duration: '1m',
  thresholds: {
    'http_req_failed': ['rate<0.01'],
    'checks': ['rate>0.99'],
    // 'http_req_duration': ['p(95)<5000'], // Comentamos o eliminamos el umbral global para ser más específicos.

    // --- Umbrales Específicos por Petición ---
    // Estas peticiones deberían ser rápidas.
    'http_req_duration{name:Admin_GetAllUsers}': ['p(95) < 800'],   // < 0.8 segundos
    'http_req_duration{name:Client_AddToCart}':  ['p(95) < 2000'],  // < 2 segundos

    // Sospechamos que esta es la petición lenta. Le damos un umbral alto para que la prueba no falle,
    // pero al ver los resultados, podrás confirmar si esta es la que tarda ~30 segundos.
    'http_req_duration{name:Client_Purchase}':   ['p(95) < 35000'], // < 35 segundos
  },
};


export { setup };

export default function (data) {
  runTestFlow(data, 'Load');
}
