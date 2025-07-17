import http from 'k6/http';
import { check, sleep } from 'k6';
import { scenario } from 'k6/execution';

const BASE_URL = __ENV.BASE_URL || 'http://spring:8080';
const NUM_CLIENTS = parseInt(__ENV.NUM_CLIENTS) || 400;
const NUM_ADMINS = parseInt(__ENV.NUM_ADMINS) || 400;
const USER_PASSWORD = 'Password123!';

export const options = {
  scenarios: {
    // Escenario para registrar clientes en paralelo
    register_clients: {
      executor: 'shared-iterations',
      exec: 'registerClients',
      vus: 50, // Usar 50 VUs para registrar en paralelo
      iterations: NUM_CLIENTS,
      maxDuration: '10m',
    },
    // Escenario para registrar administradores en paralelo
    register_admins: {
      executor: 'shared-iterations',
      exec: 'registerAdmins',
      vus: 50, // Usar 50 VUs para registrar en paralelo
      iterations: NUM_ADMINS,
      maxDuration: '10m',
    },
  },
};

// Función que se encarga de registrar Clientes
export function registerClients() {
  // scenario.iterationInTest es un contador global para el escenario (0 a 399)
  const clientNumber = scenario.iterationInTest + 1;
  const clientName = `client${clientNumber}`;

  const registerPayload = JSON.stringify({
    name: clientName,
    password: USER_PASSWORD,
    role: 'Client',
  });

  const params = { headers: { 'Content-Type': 'application/json' } };
  const res = http.post(`${BASE_URL}/auth/register`, registerPayload, params);

  if (!check(res, { [`[Client] User '${clientName}' registered`]: (r) => r.status === 200 })) {
    console.error(`Error registrando al cliente ${clientName}: ${res.status} ${res.body}`);
  }
  sleep(0.5);
}

// Función que se encarga de registrar Administradores
export function registerAdmins() {
  const adminNumber = scenario.iterationInTest + 1;
  const adminName = `admin${adminNumber}`;

  const registerPayload = JSON.stringify({
    name: adminName,
    password: USER_PASSWORD,
    role: 'Admin',
  });

  const params = { headers: { 'Content-Type': 'application/json' } };
  const res = http.post(`${BASE_URL}/auth/register`, registerPayload, params);

  if (!check(res, { [`[Admin] User '${adminName}' registered`]: (r) => r.status === 200 })) {
    console.error(`Error registrando al admin ${adminName}: ${res.status} ${res.body}`);
  }
  sleep(0.5);
}