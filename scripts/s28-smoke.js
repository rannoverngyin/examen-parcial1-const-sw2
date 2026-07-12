import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 5,
    duration: '30s',
    thresholds: {
        http_req_failed: ['rate<0.01'],
        http_req_duration: ['p(95)<500'],
        checks: ['rate>0.99'],
    },
};

export default function () {
    const res = http.get('http://localhost:8080/carga/productos');
    check(res, {
        'status 200': (r) => r.status === 200,
        'respuesta JSON': (r) => r.headers['Content-Type']?.includes('json'),
    });
    sleep(1);
}
