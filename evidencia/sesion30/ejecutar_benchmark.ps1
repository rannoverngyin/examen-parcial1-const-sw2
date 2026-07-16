# ejecutar_benchmark_simple.ps1
# Versión más simple y robusta

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   EJECUTANDO BENCHMARK - SESIÓN 30     " -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Crear directorios
New-Item -ItemType Directory -Force -Path "evidencia/sesion30/resultados_baseline" | Out-Null
New-Item -ItemType Directory -Force -Path "evidencia/sesion30/resultados_optimizado" | Out-Null

# Baseline - 3 ejecuciones
Write-Host "🔵 EJECUTANDO BASELINE (3 repeticiones)" -ForegroundColor Cyan
Write-Host ""

Write-Host "  [1/3] Ejecutando Baseline Run 1..." -ForegroundColor Yellow
k6 run --summary-export evidencia/sesion30/resultados_baseline/run1.json evidencia/sesion30/benchmark_baseline.js > evidencia/sesion30/resultados_baseline/run1.txt 2>&1
Write-Host "  ✅ Baseline Run 1 completado" -ForegroundColor Green

Write-Host "  [2/3] Ejecutando Baseline Run 2..." -ForegroundColor Yellow
k6 run --summary-export evidencia/sesion30/resultados_baseline/run2.json evidencia/sesion30/benchmark_baseline.js > evidencia/sesion30/resultados_baseline/run2.txt 2>&1
Write-Host "  ✅ Baseline Run 2 completado" -ForegroundColor Green

Write-Host "  [3/3] Ejecutando Baseline Run 3..." -ForegroundColor Yellow
k6 run --summary-export evidencia/sesion30/resultados_baseline/run3.json evidencia/sesion30/benchmark_baseline.js > evidencia/sesion30/resultados_baseline/run3.txt 2>&1
Write-Host "  ✅ Baseline Run 3 completado" -ForegroundColor Green

Write-Host ""
Write-Host "🟢 EJECUTANDO OPTIMIZADO (3 repeticiones)" -ForegroundColor Cyan
Write-Host ""

Write-Host "  [1/3] Ejecutando Optimizado Run 1..." -ForegroundColor Yellow
k6 run --summary-export evidencia/sesion30/resultados_optimizado/run1.json evidencia/sesion30/benchmark_optimizado.js > evidencia/sesion30/resultados_optimizado/run1.txt 2>&1
Write-Host "  ✅ Optimizado Run 1 completado" -ForegroundColor Green

Write-Host "  [2/3] Ejecutando Optimizado Run 2..." -ForegroundColor Yellow
k6 run --summary-export evidencia/sesion30/resultados_optimizado/run2.json evidencia/sesion30/benchmark_optimizado.js > evidencia/sesion30/resultados_optimizado/run2.txt 2>&1
Write-Host "  ✅ Optimizado Run 2 completado" -ForegroundColor Green

Write-Host "  [3/3] Ejecutando Optimizado Run 3..." -ForegroundColor Yellow
k6 run --summary-export evidencia/sesion30/resultados_optimizado/run3.json evidencia/sesion30/benchmark_optimizado.js > evidencia/sesion30/resultados_optimizado/run3.txt 2>&1
Write-Host "  ✅ Optimizado Run 3 completado" -ForegroundColor Green

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   BENCHMARK COMPLETADO EXITOSAMENTE    " -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan