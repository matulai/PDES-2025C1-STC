import { setup, runTestFlow } from './lib/common.js';

export let options = {
  vus: 50,
  duration: '1m',
  thresholds: {
    'http_req_failed': ['rate<0.01'],
    'http_req_duration': ['p(95)<800'],
    'checks': ['rate>0.99'],
  },
};
// Exportamos la función setup para que k6 la utilice.
export { setup };

export default function (data) {
  runTestFlow(data, 'Load');
}
