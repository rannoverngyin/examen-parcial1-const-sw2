import http from 'k6/http';
import { check, sleep } from 'k6';
import { textSummary } from 'https://jslib.k6.io/k6-summary/0.1.0/index.js';

export const options = {
  // Obliga a k6 a calcular y guardar los percentiles del Word.
  summaryTrendStats: [
    'avg',
    'min',
    'p(50)',
    'p(90)',
    'p(95)',
    'p(99)',
    'max',
  ],

  summaryTimeUnit: 'ms',

  stages: [
    { duration: '10s', target: 10 },
    { duration: '20s', target: 10 },
    { duration: '10s', target: 30 },
    { duration: '20s', target: 30 },
    { duration: '10s', target: 0 },
  ],

  thresholds: {
    http_req_failed: ['rate<0.01'],
    http_req_duration: ['p(95)<500', 'p(99)<800'],
    checks: ['rate>0.99'],
  },
};

export default function () {
  const res = http.get(
    'http://localhost:8080/carga/productos',
    {
      tags: {
        endpoint: 'productos',
      },
    }
  );

  check(res, {
    'status 200': (r) => r.status === 200,
    'contiene Laptop': (r) => r.body.includes('Laptop'),
  });

  sleep(1);
}

function obtenerValor(data, metrica, propiedad) {
  const resultado = data.metrics[metrica];

  if (!resultado || !resultado.values) {
    return null;
  }

  return resultado.values[propiedad];
}

function formatear(valor, decimales = 2) {
  if (typeof valor !== 'number') {
    return 'N/D';
  }

  return valor.toFixed(decimales);
}

function estadoThreshold(data, metrica) {
  const resultado = data.metrics[metrica];

  if (!resultado || !resultado.thresholds) {
    return 'N/D';
  }

  const nombres = Object.keys(resultado.thresholds);

  const cumple = nombres.every(
    (nombre) => resultado.thresholds[nombre].ok
  );

  return cumple ? 'CUMPLE' : 'NO CUMPLE';
}

export function handleSummary(data) {
  const ejecucion = (
    __ENV.EJECUCION || 'resultado'
  )
    .toLowerCase()
    .replace(/[^a-z0-9_-]/g, '');

  const p50 = obtenerValor(
    data,
    'http_req_duration',
    'p(50)'
  );

  const p95 = obtenerValor(
    data,
    'http_req_duration',
    'p(95)'
  );

  const p99 = obtenerValor(
    data,
    'http_req_duration',
    'p(99)'
  );

  const rps = obtenerValor(
    data,
    'http_reqs',
    'rate'
  );

  const solicitudes = obtenerValor(
    data,
    'http_reqs',
    'count'
  );

  const tasaErrores = obtenerValor(
    data,
    'http_req_failed',
    'rate'
  );

  const tasaChecks = obtenerValor(
    data,
    'checks',
    'rate'
  );

  const vusMaximos =
    obtenerValor(data, 'vus_max', 'max') ??
    obtenerValor(data, 'vus', 'max');

  const duracionSegundos =
    data.state && data.state.testRunDurationMs
      ? data.state.testRunDurationMs / 1000
      : null;

  const metricasWord = {
    ejecucion: ejecucion,
    p50_ms: p50,
    p95_ms: p95,
    p99_ms: p99,
    rps: rps,
    errores_porcentaje:
      typeof tasaErrores === 'number'
        ? tasaErrores * 100
        : null,
    checks_porcentaje:
      typeof tasaChecks === 'number'
        ? tasaChecks * 100
        : null,
    solicitudes: solicitudes,
    vus_maximos: vusMaximos,
    duracion_segundos: duracionSegundos,
    threshold_latencia: estadoThreshold(
      data,
      'http_req_duration'
    ),
    threshold_errores: estadoThreshold(
      data,
      'http_req_failed'
    ),
    threshold_checks: estadoThreshold(
      data,
      'checks'
    ),
    cpu_pico: 'Registrar con JMC',
    memoria_pico: 'Registrar con JMC',
  };

  const resumenWord = `
==================================================
MÉTRICAS PARA LA TABLA DEL WORD
==================================================
Ejecución       : ${ejecucion}
p50             : ${formatear(p50)} ms
p95             : ${formatear(p95)} ms
p99             : ${formatear(p99)} ms
RPS             : ${formatear(rps)} req/s
Errores         : ${formatear(
    typeof tasaErrores === 'number'
      ? tasaErrores * 100
      : null
  )} %
Checks          : ${formatear(
    typeof tasaChecks === 'number'
      ? tasaChecks * 100
      : null
  )} %
Peticiones      : ${formatear(solicitudes, 0)}
VUs máximos     : ${formatear(vusMaximos, 0)}
Duración        : ${formatear(duracionSegundos)} s
Latencia        : ${metricasWord.threshold_latencia}
Errores         : ${metricasWord.threshold_errores}
Checks          : ${metricasWord.threshold_checks}
CPU pico        : Registrar con JMC
Memoria pico    : Registrar con JMC
==================================================
`;

  const resumenCompleto =
    textSummary(data, {
      indent: ' ',
      enableColors: true,
    }) + resumenWord;

  const jsonCompleto = JSON.stringify(
    data,
    null,
    2
  );

  const jsonTabla = JSON.stringify(
    metricasWord,
    null,
    2
  );

  const csvTabla =
    'ejecucion;p50_ms;p95_ms;p99_ms;rps;errores_pct;checks_pct;peticiones;vus_maximos\n' +
    [
      ejecucion,
      p50,
      p95,
      p99,
      rps,
      typeof tasaErrores === 'number'
        ? tasaErrores * 100
        : '',
      typeof tasaChecks === 'number'
        ? tasaChecks * 100
        : '',
      solicitudes,
      vusMaximos,
    ].join(';') +
    '\n';

  const archivos = {
    stdout: resumenCompleto,

    // Última ejecución, como indica la guía.
    'evidencia/sesion28/resumen.json':
      jsonCompleto,
  };

  // Guarda automáticamente base u optimizado.
  archivos[
    `evidencia/sesion28/resumen-${ejecucion}.json`
  ] = jsonCompleto;

  archivos[
    `evidencia/sesion28/metricas-${ejecucion}.json`
  ] = jsonTabla;

  archivos[
    `evidencia/sesion28/metricas-${ejecucion}.txt`
  ] = resumenWord;

  archivos[
    `evidencia/sesion28/metricas-${ejecucion}.csv`
  ] = csvTabla;

  return archivos;
}