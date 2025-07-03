import { setup, runTestFlow } from './lib/common.js';

export let options = {
  scenarios: {
    breakpoint: {
      executor: 'ramping-vus',
      startVUs: 0,
      stages: [
        { duration: '10m', target: 400 },
      ],
      gracefulRampDown: '30s',
    },
  },
  thresholds: {
    // Aborta la prueba si la tasa de fallos > 5% O si el p(95) > 1.5s
    'http_req_failed': [{ threshold: 'rate<0.05', abortOnFail: true, delayAbortEval: '10s' }],
    'http_req_duration': [{ threshold: 'p(95)<1500', abortOnFail: true, delayAbortEval: '10s' }],
  },
};

export { setup };

export default function (data) {
  runTestFlow(data, 'Breakpoint');
}
