import http from 'k6/http';
import { sleep, check, group } from 'k6';

export const BASE_URL = __ENV.BASE_URL || 'http://spring:8080';

// --- Credenciales y Pool de Clientes ---
const ADMIN_USERNAME = 'admin2';
const ADMIN_PASSWORD = 'Password123!';
const NUM_CLIENTS = 400;

const clients = Array.from({ length: NUM_CLIENTS }, (_, i) => ({
  name: `client${i + 1}`,
  password: 'Password123!',
}));

/**
 * Función de setup compartida.
 * Realiza el login del admin y de un pool de clientes en paralelo.
 */
export function setup() {
  // --- Login de Administrador ---
  const adminLoginRes = http.post(`${BASE_URL}/auth/login`, JSON.stringify({
    name: ADMIN_USERNAME,
    password: ADMIN_PASSWORD,
  }), { headers: { 'Content-Type': 'application/json' } });
  check(adminLoginRes, { 'Setup - Admin login successful': (res) => res.status === 200 });
  const adminAuthHeader = adminLoginRes.headers['Authorization'];
  if (!adminAuthHeader) throw new Error('Admin login failed. Halting test.');
  const adminToken = adminAuthHeader.substring(7);

  // --- Login de Clientes en Paralelo ---
  console.log(`Logging in ${NUM_CLIENTS} clients in parallel...`);
  const loginRequests = clients.map(client => ([
    'POST',
    `${BASE_URL}/auth/login`,
    JSON.stringify(client),
    { headers: { 'Content-Type': 'application/json' } }
  ]));

  const loginResponses = http.batch(loginRequests);
  const clientTokens = loginResponses.map((res, i) => {
    if (res.status !== 200 || !res.headers['Authorization']) {
      console.error(`Login failed for ${clients[i].name}: ${res.status} ${res.body}`);
      return null;
    }
    return res.headers['Authorization'].substring(7);
  }).filter(token => token !== null);

  if (clientTokens.length !== NUM_CLIENTS) {
    throw new Error(`Could not log in all clients. Only got ${clientTokens.length} tokens. Please ensure users 'client1' through 'client${NUM_CLIENTS}' are registered.`);
  }

  console.log('All clients logged in successfully.');
  return { adminToken, clientTokens };
}

/**
 * Flujo de prueba principal compartido.
 * @param {object} data - Los datos devueltos por la función setup (tokens).
 * @param {string} testType - El nombre del tipo de prueba (e.g., 'Load', 'Stress').
 */
export function runTestFlow(data, testType) {
  const adminParams = { headers: { 'Authorization': `Bearer ${data.adminToken}` } };

  const vuId = __VU - 1;
  const clientToken = data.clientTokens[vuId];
  if (!clientToken) return;

  const clientParams = {
    headers: {
      'Authorization': `Bearer ${clientToken}`,
      'Content-Type': 'application/json',
    }
  };

  const uniqueId = `TEST-${__VU}-${__ITER}`;
  const productPayload = JSON.stringify({
    name: `Smart TV de Prueba ${uniqueId}`,
    mlaId: `MLA${uniqueId}`,
    price: 250000.50,
    imageURL: "http://http2.mlstatic.com/D_991897-MLA72029339387_102023-I.jpg",
    domainId: "MLA-TELEVISIONS",
    description: `Producto de prueba generado por k6 con ID único: ${uniqueId}`,
    qualifications: []
  });

  group(`${testType} - Admin Actions`, () => {
    // Añadimos un tag para identificar esta petición específica
    const res = http.get(`${BASE_URL}/admin/allRegisteredUsers`, { ...adminParams, tags: { name: 'Admin_GetAllUsers' } });
    check(res, { '[Admin] GET /admin/allRegisteredUsers status 200': (r) => r.status === 200 });
  });

  sleep(2);

  group(`${testType} - Client Flow`, () => {
    // Tag para 'addtocart'
    const addRes = http.post(`${BASE_URL}/client/addToCart`, productPayload, { ...clientParams, tags: { name: 'Client_AddToCart' } });
    check(addRes, { '[Client] POST /client/addToCart status 200': (r) => r.status === 200 });

    sleep(2);

    // Tag para 'purchaseProducts'
    const purchaseRes = http.post(`${BASE_URL}/client/purchaseProducts`, null, { ...clientParams, tags: { name: 'Client_Purchase' } });
    check(purchaseRes, { '[Client] POST /client/purchaseProducts status 200': (r) => r.status === 200 });
  });

  sleep(1);

  group(`${testType} - Product Search`, () => {
    const searchTerm = 'samsung';
    // Tag para 'search'. Este es probablemente el lento.
    const res = http.get(`${BASE_URL}/products/search?text=${searchTerm}`, { tags: { name: 'Public_Search' } });
    check(res, { '[Public] GET /products/search status 200': (r) => r.status === 200 });
  });

  sleep(1);
}