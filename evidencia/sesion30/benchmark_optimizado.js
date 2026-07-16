import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  // Métricas que aparecerán en la consola y en el JSON.
  summaryTrendStats: [
    'avg',
    'min',
    'med',
    'max',
    'p(90)',
    'p(95)',
    'p(99)'
  ],

  // Etapas de la prueba de carga.
  stages: [
    // Aumenta progresivamente hasta 20 usuarios virtuales.
    { duration: '15s', target: 20 },

    // Mantiene los 20 usuarios durante un minuto.
    { duration: '1m', target: 20 },

    // Reduce progresivamente la carga hasta llegar a cero.
    { duration: '15s', target: 0 }
  ],

  // Condiciones que debe cumplir la prueba.
  thresholds: {
    // Debe fallar menos del 5 % de las peticiones.
    http_req_failed: ['rate<0.05'],

    // El 95 % de las peticiones debe responder en menos de 1000 ms.
    http_req_duration: ['p(95)<1000']
  }
};

export default function () {
  // Ejecuta una petición al endpoint optimizado.
  const res = http.get(
    'http://localhost:8080/benchmark/optimizado'
  );

  // Valida que la respuesta sea correcta.
  check(res, {
    'optimizado status 200':
      r => r.status === 200,

    'optimizado respuesta no vacia':
      r => r.body.length > 0
  });

  // Espera un segundo antes de realizar otra petición.
  sleep(1);
}