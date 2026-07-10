import http from 'k6/http';
import { check, sleep } from 'k6';
import { textSummary } from
    'https://jslib.k6.io/k6-summary/0.1.0/index.js';

export const options = {
    summaryTrendStats: [
        'avg',
        'min',
        'med',
        'max',
        'p(90)',
        'p(95)',
        'p(99)',
    ],

    summaryTimeUnit: 'ms',

    stages: [
        {
            duration: '10s',
            target: 10,
        },
        {
            duration: '20s',
            target: 10,
        },
        {
            duration: '10s',
            target: 30,
        },
        {
            duration: '20s',
            target: 30,
        },
        {
            duration: '10s',
            target: 0,
        },
    ],

    thresholds: {
        http_req_failed: [
            'rate<0.01',
        ],

        http_req_duration: [
            'p(95)<500',
            'p(99)<800',
        ],

        checks: [
            'rate>0.99',
        ],
    },
};

export default function () {
    const respuesta = http.get(
        'http://localhost:8080/carga/productos',
        {
            tags: {
                endpoint: 'productos',
            },
        }
    );

    check(respuesta, {
        'estado HTTP 200': (r) =>
            r.status === 200,

        'respuesta contiene Laptop': (r) =>
            r.body.includes('Laptop'),

        'respuesta contiene Mouse': (r) =>
            r.body.includes('Mouse'),
    });

    sleep(1);
}

export function handleSummary(data) {
    return {
        stdout: textSummary(
            data,
            {
                indent: ' ',
                enableColors: true,
            }
        ),

        'evidencia/sesion28/resumen.json':
            JSON.stringify(data, null, 2),
    };
}