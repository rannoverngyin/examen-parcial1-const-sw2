import http from 'k6/http';
import { check, sleep } from 'k6';
import { textSummary } from 'https://jslib.k6.io/k6-summary/0.1.0/index.js';

export const options = {
    stages: [
        { duration: '10s', target: 50 },
        { duration: '20s', target: 50 },
        { duration: '10s', target: 0 },
    ],
    thresholds: {
        http_req_failed: ['rate<0.01'],
        http_req_duration: ['p(95)<700'],
        checks: ['rate>0.99'],
    },
};

export default function () {
    const res = http.get('http://localhost:8080/carga/productos', {
        tags: { endpoint: 'productos' },
    });

    check(res, {
        'status 200': (r) => r.status === 200,
        'contiene Laptop': (r) => r.body.includes('Laptop'),
    });

    sleep(1);
}

export function handleSummary(data) {
    return {
        'stdout': textSummary(data, { indent: ' ', enableColors: true }),
        'evidencia/sesion28/resumen-50vu.json': JSON.stringify(data, null, 2),
    };
}
