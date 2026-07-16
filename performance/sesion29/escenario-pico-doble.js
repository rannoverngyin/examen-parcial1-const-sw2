import http from 'k6/http';
import { check, group, sleep } from 'k6';
import { Counter, Rate, Trend } from 'k6/metrics';

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';

const businessErrors = new Counter('business_errors');
const reportLatency = new Trend('report_latency', true);

export const options = {
  discardResponseBodies: true,
  scenarios: {
    pico_doble_reportes: {
      executor: 'ramping-arrival-rate',
      exec: 'consultarReporte',
      startTime: '0s',
      startRate: 2,
      timeUnit: '1s',
      preAllocatedVUs: 10,
      maxVUs: 50,
      stages: [
        { duration: '20s', target: 25 },
        { duration: '20s', target: 25 },
        { duration: '20s', target: 2 },
        { duration: '20s', target: 2 },
        { duration: '20s', target: 25 },
        { duration: '20s', target: 25 },
        { duration: '20s', target: 0 },
      ],
      tags: { flujo: 'pico-doble' },
    },
  },

  thresholds: {
    http_req_failed: ['rate<0.02'],
    checks: ['rate>0.98'],
    'http_req_duration{endpoint:reporte}': ['p(95)<1500'],
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

export function consultarReporte() {
  const res = http.get(`${BASE_URL}/carga/productos/reporte`, {
    tags: { endpoint: 'reporte' },
  });
  reportLatency.add(res.timings.duration);

  const ok = check(res, { 'reporte HTTP 200': r => r.status === 200 });
  if (!ok) businessErrors.add(1);
}

export function handleSummary(data) {
  return {
    'evidencia/resumen-sesion29-pico-doble.json': JSON.stringify(data, null, 2),
    stdout: 'Resumen de pico doble guardado en evidencia/resumen-sesion29-pico-doble.json',
  };
}
