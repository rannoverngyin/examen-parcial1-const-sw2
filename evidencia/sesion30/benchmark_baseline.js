import http from 'k6/http';
import { check, sleep } from 'k6';

const BASE_URL =
    __ENV.BASE_URL || 'http://localhost:8080';

export const options = {
    stages: [
        {
            duration: '15s',
            target: 20,
        },
        {
            duration: '1m',
            target: 20,
        },
        {
            duration: '15s',
            target: 0,
        },
    ],

    thresholds: {
        http_req_failed: [
            'rate<0.05',
        ],

        http_req_duration: [
            'p(95)<1000',
        ],

        checks: [
            'rate>0.99',
        ],
    },

    summaryTrendStats: [
        'avg',
        'min',
        'med',
        'max',
        'p(90)',
        'p(95)',
        'p(99)',
    ],
};

export default function () {
    const respuesta = http.get(
        `${BASE_URL}/benchmark/baseline`,
        {
            tags: {
                version: 'baseline',
                endpoint: 'benchmark',
            },

            timeout: '5s',
        }
    );

    check(respuesta, {
        'baseline responde HTTP 200': (resultado) =>
            resultado.status === 200,

        'baseline devuelve contenido': (resultado) =>
            resultado.body !== null &&
            resultado.body.length > 0,

        'baseline devuelve JSON': (resultado) =>
            resultado.headers['Content-Type']
                ?.includes('application/json'),
    });

    sleep(1);
}