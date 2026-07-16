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
  const res = http.get('http://localhost:8080/benchmark/baseline');
  check(res, {
    'baseline status 200': r => r.status === 200,
    'baseline respuesta no vacia': r => r.body.length > 0
  });
  sleep(1);
}