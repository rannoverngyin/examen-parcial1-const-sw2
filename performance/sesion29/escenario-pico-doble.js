import http from 'k6/http';
import { check, group, sleep } from 'k6';
import { Counter, Rate, Trend } from 'k6/metrics';

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';

const businessErrors = new Counter('business_errors');
const successfulWrites = new Rate('successful_writes');
const reportLatency = new Trend('report_latency', true);

export const options = {
  discardResponseBodies: true,
  scenarios: {
    consultas: {
      executor: 'ramping-vus',
      exec: 'consultarProductos',
      startVUs: 0,
      stages: [
        { duration: '20s', target: 10 },
        { duration: '60s', target: 10 },
        { duration: '30s', target: 25 },
        { duration: '30s', target: 25 },
        { duration: '20s', target: 0 },
      ],
      gracefulRampDown: '10s',
      tags: { flujo: 'consulta' },
    },
    registros: {
      executor: 'constant-arrival-rate',
      exec: 'registrarProducto',
      startTime: '20s',
      rate: 5,
      timeUnit: '1s',
      duration: '120s',
      preAllocatedVUs: 5,
      maxVUs: 30,
      tags: { flujo: 'registro' },
    },
    // SECCIÓN MODIFICADA: Duplicamos el pico de reportes (de 20 a 40 RPS)
    pico_reportes: {
      executor: 'ramping-arrival-rate',
      exec: 'consultarReporte',
      startTime: '80s',
      startRate: 2,
      timeUnit: '1s',
      preAllocatedVUs: 15, // Subimos VUs preasignadas para evitar dropped_iterations
      maxVUs: 80,          // Subimos el límite máximo de VUs para soportar la carga doble
      stages: [
        { duration: '15s', target: 40 }, // Reto: Duplicado de 20 a 40
        { duration: '15s', target: 40 }, // Reto: Duplicado de 20 a 40
        { duration: '15s', target: 0 },
      ],
      tags: { flujo: 'reporte' },
    },
  },
  thresholds: {
    http_req_failed: ['rate<0.02'],
    checks: ['rate>0.98'],
    'http_req_duration{endpoint:listado}': ['p(95)<700'],
    'http_req_duration{endpoint:total}': ['p(95)<500'],
    'http_req_duration{endpoint:registro}': ['p(95)<1000'],
    'http_req_duration{endpoint:reporte}': ['p(95)<1500'],
    successful_writes: ['rate>0.97'],
    business_errors: ['count<10'],
    report_latency: ['p(95)<1500'],
  },
};

export function setup() {
  const health = http.get(`${BASE_URL}/carga/productos/total`, {
    tags: { endpoint: 'precheck' },
  });
  if (health.status !== 200) {
    throw new Error(`API no disponible. HTTP ${health.status}`);
  }
  return { runId: Date.now() };
}

export function consultarProductos() {
  group('navegacion-productos', () => {
    const listado = http.get(`${BASE_URL}/carga/productos`, {
      tags: { endpoint: 'listado' },
    });
    check(listado, { 'listado HTTP 200': r => r.status === 200 });

    const total = http.get(`${BASE_URL}/carga/productos/total`, {
      tags: { endpoint: 'total' },
    });
    check(total, { 'total HTTP 200': r => r.status === 200 });
  });
  sleep(Math.random() * 2 + 1);
}

export function registrarProducto(data) {
  const nombre = `Prod-${data.runId}-${__VU}-${__ITER}`;
  const res = http.post(
    `${BASE_URL}/carga/productos?nombre=${encodeURIComponent(nombre)}`,
    null,
    { tags: { endpoint: 'registro' } }
  );
  const ok = check(res, { 'registro HTTP 200': r => r.status === 200 });
  successfulWrites.add(ok);
  if (!ok) businessErrors.add(1);
}

export function consultarReporte() {
  const res = http.get(`${BASE_URL}/carga/productos/reporte`, {
    tags: { endpoint: 'reporte' },
  });
  reportLatency.add(res.timings.duration);
  const ok = check(res, { 'reporte HTTP 200': r => r.status === 200 });
  if (!ok) businessErrors.add(1);
}

// Guardamos el nuevo resumen en un archivo distinto para diferenciar las corridas
export function handleSummary(data) {
  return {
    'evidencia/resumen-sesion29-reto.json': JSON.stringify(data, null, 2),
    stdout: 'Resumen del reto guardado en evidencia/resumen-sesion29-reto.json',
  };
}