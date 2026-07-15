import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '15s', target: 20 },
    { duration: '1m', target: 20 },
    { duration: '15s', target: 0 }
  ],
  thresholds: {
    http_req_failed: ['rate<0.05'],
    http_req_duration: ['p(95)<1000']
  }
};

export default function () {
  // INEFICIENCIA A EXPLICAR AL PROFESOR:
  // Este endpoint procesa un filtrado manual e iterativo sobre colecciones pesadas en la memoria (JVM) de Spring Boot.
  // Al no delegar el filtro a la base de datos ni usar paginación, el servidor procesa miles de objetos repetidamente 
  // por cada petición de los 20 usuarios concurrentes, saturando el procesador y el recolector de basura (Garbage Collector).
  const res = http.get('http://host.docker.internal:8080/benchmark/baseline');
  
  check(res, {
    'baseline status 200': r => r.status === 200,
    'baseline respuesta no vacia': r => r.body && r.body.length > 0
  });
  sleep(1);
}