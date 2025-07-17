import { setup, runTestFlow } from './lib/common.js';

export let options = {
  setupTimeout: '10m', // tarda bastante en logear a los 300
  scenarios: {
    breakpoint: {
      executor: 'ramping-vus', 
      startVUs: 0,
      stages: [
        { duration: '10m', target: 300 }, // aumenta a 300 en un periodo de 10min
      ],
      gracefulRampDown: '30s',
    },
  },
  thresholds: {
    // Aborta la prueba si la tasa de fallos > 5% O si el p(95) > 1.5s
    'http_req_failed': [{ threshold: 'rate<0.05', abortOnFail: true, delayAbortEval: '10s' }],
    'http_req_duration': [{ threshold: 'p(95)<80000', abortOnFail: true, delayAbortEval: '10s' }],
  },
};

export { setup };

export default function (data) {
  runTestFlow(data, 'Breakpoint');
}
