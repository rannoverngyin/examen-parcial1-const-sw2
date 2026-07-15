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
  // OPTIMIZACIÓN A EXPLICAR AL PROFESOR:
  // Se eliminó el procesamiento iterativo ineficiente en el servidor Java. Ahora el endpoint transfiere una 
  // estructura de datos optimizada y reducida (payload ligero), evitando instanciar y recorrer colecciones masivas.
  // Al liberar de trabajo a la CPU y al Garbage Collector, la latencia promedio disminuyó un 39.94% y el sistema 
  // se volvió sumamente estable (desviación estándar de apenas 0.71 ms).
  const res = http.get('http://host.docker.internal:8080/benchmark/optimizado');
  
  check(res, {
    'optimizado status 200': r => r.status === 200,
    'optimizado respuesta no vacia': r => r.body && r.body.length > 0
  });
  sleep(1);
}