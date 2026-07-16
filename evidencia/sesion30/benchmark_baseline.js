import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '15s', target: 20 }, // Sube a 20 usuarios en 15s
    { duration: '1m', target: 20 },  // Mantiene 20 usuarios por 1 minuto
    { duration: '15s', target: 0 }   // Baja a 0 usuarios en 15s
  ],
  thresholds: {
    http_req_failed: ['rate<0.05'],     // Menos del 5% de errores permitidos
    http_req_duration: ['p(95)<1000']   // El 95% de las peticiones deben tardar menos de 1000ms
  }
};

export default function () {
  const res = http.get('http://localhost:8082/benchmark/baseline');
  check(res, {
    'baseline status 200': r => r.status === 200,
    'baseline respuesta no vacia': r => r.body.length > 0
  });
  sleep(1);
}