import http from 'k6/http';
import { sleep, check, group } from 'k6';

// --- Configuración Centralizada ---

// La URL base del entorno de pruebas. Se puede sobrescribir con una variable de entorno.
export const BASE_URL = __ENV.BASE_URL || 'http://spring:8080';

// Credenciales del usuario administrador para tareas de setup.
const ADMIN_USERNAME = 'admin2';
const ADMIN_PASSWORD = 'Password123!';

// El número de clientes a loguear se toma de una variable de entorno.
// Si no se especifica, se usarán 50 por defecto.
// k6 run -e NUM_CLIENTS=100 load-test.js
const NUM_CLIENTS = parseInt(__ENV.NUM_CLIENTS) || 50;

/**
 * =================================================================================
 * FUNCIÓN SETUP
 * Se ejecuta una única vez al inicio de toda la prueba.
 * Su objetivo es preparar los datos y el estado necesarios para los usuarios virtuales (VUs).
 * =================================================================================
 */
export function setup() {
  console.log(`Iniciando setup para ${NUM_CLIENTS} clientes...`);

  // 1. Iniciar sesión como Administrador para obtener un token
  // Este token se puede usar para tareas administrativas o para crear datos de prueba.
  const adminLoginRes = http.post(
    `${BASE_URL}/auth/login`,
    JSON.stringify({ name: ADMIN_USERNAME, password: 'Password123!' }),
    { headers: { 'Content-Type': 'application/json' } }
  );

  if (!check(adminLoginRes, { 'Setup - Admin login successful': (r) => r.status === 200 })) {
    throw new Error('El login del Admin falló. Deteniendo la prueba.');
  }
  const adminToken = adminLoginRes.json('token');

  // 2. Generar el pool de clientes que intentarán iniciar sesión.
  const clients = Array.from({ length: NUM_CLIENTS }, (_, i) => ({
    name: `client${i + 1}`,
    password: 'Password123!',
  }));

  // 3. Iniciar sesión con todos los clientes en lotes para no sobrecargar el servidor.
  console.log(`Logging in ${NUM_CLIENTS} clients in batches...`);
  const batchSize = 50; // Iniciar sesión en lotes de 50
  const clientTokens = [];

  for (let i = 0; i < NUM_CLIENTS; i += batchSize) {
    const batch = clients.slice(i, i + batchSize);
    const loginRequests = batch.map(client => ([
      'POST',
      `${BASE_URL}/auth/login`,
      JSON.stringify(client),
      { headers: { 'Content-Type': 'application/json' } }
    ]));

    const loginResponses = http.batch(loginRequests);

    loginResponses.forEach((res, j) => {
      const clientIndex = i + j;
      if (res.status !== 200 || !res.headers['Authorization']) {
        console.error(`Login failed for ${clients[clientIndex].name}: ${res.status} ${res.body}`);
      } else {
        clientTokens.push(res.json('token'));
      }
    });
    sleep(1); // Pequeña pausa entre lotes.
  }

  if (clientTokens.length !== NUM_CLIENTS) {
    console.error(`Se esperaba loguear ${NUM_CLIENTS} clientes, pero solo se obtuvieron ${clientTokens.length} tokens.`);
    // Considera lanzar un error si es crítico que todos se logueen.
    // throw new Error('No todos los clientes pudieron iniciar sesión.');
  }

  console.log(`Setup finalizado. Se obtuvieron ${clientTokens.length} tokens de cliente.`);

  // 4. Devolver todos los datos necesarios para que los VUs los usen.
  return {
    adminToken: adminToken,
    clientTokens: clientTokens,
  };
}

/**
 * =================================================================================
 * FUNCIÓN DE FLUJO DE PRUEBA
 * Contiene la lógica que cada VU ejecutará en un bucle.
 * @param {object} data - El objeto devuelto por la función setup().
 * @param {string} testType - Un nombre para etiquetar los grupos de esta prueba (ej. 'Load', 'Stress').
 * =================================================================================
 */
export function runTestFlow(data, testType) {
  // Asegurarse de que el VU tenga un token de cliente para trabajar.
  // __VU es el ID del usuario virtual (empieza en 1). Lo ajustamos para que sea un índice de array (empieza en 0).
  const vuId = __VU - 1;
  const clientToken = data.clientTokens[vuId];

  // Si no hay token para este VU (porque hubo más VUs que logins exitosos), no hacer nada.
  if (!clientToken) {
    return;
  }

  // Preparar los headers de autenticación para las peticiones.
  const clientParams = {
    headers: {
      'Authorization': `Bearer ${clientToken}`,
      'Content-Type': 'application/json',
    }
  };

  // --- INICIO DEL FLUJO DE USUARIO ---

  group(`${testType} - Combined Flow`, () => {
    // 1. Obtener la lista de usuarios registrados (Admin)
    let adminRes = http.get(`${BASE_URL}/admin/allRegisteredUsers`, {
      headers: {
        'Authorization': `Bearer ${data.adminToken}`,
      },
    });
    check(adminRes, { '[Admin] GET /admin/allRegisteredUsers status 200': (r) => r.status === 200 });

    sleep(1);

    // 2. Añadir un producto al carrito (Client)
    const productPayload = JSON.stringify({
      name: `Smart TV de Prueba - VU ${__VU}`, // Producto con nombre único por VU
      mlaId: `MLA${Math.floor(Math.random() * 10000)}`, // ID aleatorio
      price: 250000.50,
      imageURL: "http://http2.mlstatic.com/D_991897-MLA72029339387_102023-I.jpg",
      domainId: "MLA-TELEVISIONS",
      description: `Producto de prueba generado por k6 - VU ${__VU}`,
    });
    let addRes = http.post(`${BASE_URL}/client/addToCart`, productPayload, { ...clientParams, tags: { name: 'Client_AddToCart' } });
    check(addRes, { '[Client] POST /client/addToCart status 200': (r) => r.status === 200 });

    sleep(2);

    // 3. Realizar la compra (Client)
    let purchaseRes = http.post(`${BASE_URL}/client/purchaseProducts`, null, { ...clientParams, tags: { name: 'Client_Purchase' } });
    check(purchaseRes, { '[Client] POST /client/purchaseProducts status 200': (r) => r.status === 200 });
  });

  sleep(1);
}
