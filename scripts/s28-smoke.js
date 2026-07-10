import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 1,
    duration: '10s',

    thresholds: {
        http_req_failed: ['rate<0.01'],
        http_req_duration: ['p(95)<500'],
        checks: ['rate>0.99'],
    },
};

export default function () {
    const respuesta = http.get(
        'http://localhost:8080/carga/productos',
        {
            tags: {
                prueba: 'smoke',
                endpoint: 'productos',
            },
        }
    );

    check(respuesta, {
        'estado HTTP 200': (r) => r.status === 200,

        'respuesta en formato JSON': (r) =>
            r.headers['Content-Type']
                ?.toLowerCase()
                .includes('application/json'),

        'contiene Laptop': (r) =>
            r.body.includes('Laptop'),
    });

    sleep(1);
}