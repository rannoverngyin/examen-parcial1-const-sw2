import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: 50,
  duration: '30s',

  summaryTrendStats: [
    'avg',
    'min',
    'p(50)',
    'p(90)',
    'p(95)',
    'p(99)',
    'max',
  ],

  thresholds: {
    http_req_failed: ['rate<0.01'],
    http_req_duration: ['p(95)<700'],
    checks: ['rate>0.99'],
  },
};

export default function () {
  const res = http.get(
    'http://localhost:8080/carga/productos'
  );

  check(res, {
    'status 200': (r) => r.status === 200,
    'contiene Laptop': (r) =>
      r.body.includes('Laptop'),
  });

  sleep(1);
}