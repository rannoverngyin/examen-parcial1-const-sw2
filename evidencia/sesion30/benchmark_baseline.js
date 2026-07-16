import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  // Define las métricas que k6 guardará en el resumen y en el JSON.
  summaryTrendStats: [
    'avg',
    'min',
    'med',
    'max',
    'p(90)',
    'p(95)',
    'p(99)'
  ],

  // Configuración de la carga.
  stages: [
    // Durante 15 segundos aumenta progresivamente hasta 20 usuarios.
    { duration: '15s', target: 20 },

    // Mantiene 20 usuarios virtuales durante 1 minuto.
    { duration: '1m', target: 20 },

    // Durante 15 segundos reduce los usuarios hasta llegar a 0.
    { duration: '15s', target: 0 }
  ],

  // Condiciones que debe cumplir la prueba.
  thresholds: {
    // Se permite menos del 5 % de peticiones fallidas.
    http_req_failed: ['rate<0.05'],

    // El 95 % de las peticiones debe responder en menos de 1000 ms.
    http_req_duration: ['p(95)<1000']
  }
};

export default function () {
  // Envía una petición al endpoint baseline.
  const res = http.get(
    'http://localhost:8080/benchmark/baseline'
  );

  // Comprueba que la respuesta sea correcta.
  check(res, {
    'baseline status 200': r => r.status === 200,

    'baseline respuesta no vacia':
      r => r.body.length > 0
  });

  // Cada usuario espera un segundo antes de repetir.
  sleep(1);
}