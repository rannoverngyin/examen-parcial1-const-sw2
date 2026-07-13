import http from 'k6/http';
import { check, group, sleep } from 'k6';
import { Counter, Rate, Trend } from 'k6/metrics';

// Configuración de URL
const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';

// Métricas personalizadas
const businessErrors = new Counter('business_errors');
const successfulWrites = new Rate('successful_writes');
const reportLatency = new Trend('report_latency', true);

// Opciones de la prueba
export const options = {
  discardResponseBodies: true,
  scenarios: {
    // Escenario 1: Consultas (70% del tráfico)
    consultas: {
      executor: 'ramping-vus',
      exec: 'consultarProductos',
      startVUs: 0,
      stages: [
        { duration: '20s', target: 10 },   // Ramp-up inicial
        { duration: '60s', target: 10 },   // Mantener 10 VUs
        { duration: '30s', target: 25 },   // Subir a 25 VUs
        { duration: '30s', target: 25 },   // Mantener 25 VUs
        { duration: '20s', target: 0 },    // Ramp-down
      ],
      gracefulRampDown: '10s',
      tags: { flujo: 'consulta' },
    },

    // Escenario 2: Registros (20% del tráfico)
    registros: {
      executor: 'constant-arrival-rate',
      exec: 'registrarProducto',
      startTime: '20s',                    // Comienza después de 20s
      rate: 5,                             // 5 iteraciones por segundo
      timeUnit: '1s',
      duration: '120s',                    // Dura 120 segundos
      preAllocatedVUs: 5,
      maxVUs: 30,
      tags: { flujo: 'registro' },
    },

    // Escenario 3: Pico de reportes (10% del tráfico)
    pico_reportes: {
      executor: 'ramping-arrival-rate',
      exec: 'consultarReporte',
      startTime: '80s',                    // Comienza después de 80s
      startRate: 2,                        // Empieza con 2/s
      timeUnit: '1s',
      preAllocatedVUs: 10,
      maxVUs: 50,
      stages: [
        { duration: '15s', target: 20 },   // Sube a 20/s
        { duration: '15s', target: 20 },   // Mantiene 20/s
        { duration: '15s', target: 0 },    // Baja a 0
      ],
      tags: { flujo: 'reporte' },
    },
  },

  // Umbrales de rendimiento
  thresholds: {
    // Umbrales globales
    http_req_failed: ['rate<0.02'],        // < 2% de errores
    checks: ['rate>0.98'],                  // > 98% de checks exitosos
    
    // Umbrales por endpoint
    'http_req_duration{endpoint:listado}': ['p(95)<700'],
    'http_req_duration{endpoint:total}': ['p(95)<500'],
    'http_req_duration{endpoint:registro}': ['p(95)<1000'],
    'http_req_duration{endpoint:reporte}': ['p(95)<1500'],
    
    // Métricas personalizadas
    successful_writes: ['rate>0.97'],       // > 97% de escrituras exitosas
    business_errors: ['count<10'],          // Menos de 10 errores de negocio
    report_latency: ['p(95)<1500'],         // P95 de latencia de reporte < 1500ms
  },
};

// Función setup - Verifica que la API esté disponible
export function setup() {
  const health = http.get(`${BASE_URL}/carga/productos/total`, {
    tags: { endpoint: 'precheck' },
  });

  if (health.status !== 200) {
    throw new Error(`API no disponible. HTTP ${health.status}`);
  }

  console.log(`✅ API disponible en ${BASE_URL}`);
  return { runId: Date.now() };
}

// Escenario 1: Consultar productos
export function consultarProductos() {
  group('navegacion-productos', () => {
    // Obtener listado de productos
    const listado = http.get(`${BASE_URL}/carga/productos`, {
      tags: { endpoint: 'listado' },
    });
    check(listado, { 'listado HTTP 200': r => r.status === 200 });

    // Obtener total de productos
    const total = http.get(`${BASE_URL}/carga/productos/total`, {
      tags: { endpoint: 'total' },
    });
    check(total, { 'total HTTP 200': r => r.status === 200 });
  });

  // Simular tiempo de pensamiento
  sleep(Math.random() * 2 + 1);
}

// Escenario 2: Registrar productos con datos únicos
export function registrarProducto(data) {
  // Generar nombre único usando runId, VU e iteración
  const nombre = `Prod-${data.runId}-${__VU}-${__ITER}`;
  
  const res = http.post(
    `${BASE_URL}/carga/productos?nombre=${encodeURIComponent(nombre)}`,
    null,
    { tags: { endpoint: 'registro' } }
  );

  const ok = check(res, { 'registro HTTP 200': r => r.status === 200 });
  successfulWrites.add(ok);
  
  if (!ok) {
    businessErrors.add(1);
    console.warn(`❌ Error al registrar producto: ${nombre}`);
  }
}

// Escenario 3: Consultar reporte (pico)
export function consultarReporte() {
  const res = http.get(`${BASE_URL}/carga/productos/reporte`, {
    tags: { endpoint: 'reporte' },
  });
  
  // Registrar latencia para métrica personalizada
  reportLatency.add(res.timings.duration);
  
  const ok = check(res, { 'reporte HTTP 200': r => r.status === 200 });
  if (!ok) businessErrors.add(1);
}

// Resumen final
export function handleSummary(data) {
  return {
    'evidencia/resumen-sesion29.json': JSON.stringify(data, null, 2),
    stdout: '✅ Prueba completada. Resumen guardado en evidencia/resumen-sesion29.json',
  };
}