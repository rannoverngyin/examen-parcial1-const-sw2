param(
    [string]$BaseUrl = "http://localhost:8080",
    [string]$Profile = "dev"
)

$ErrorActionPreference = "Continue"
$root = "C:\Users\ZUZUKA\examen-parcial1-const-sw2"

Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  SESION 29 - Carga + Monitoreo Web" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan

# 1) Verificar si el backend ya esta corriendo
Write-Host "`n[1/4] Verificando backend..." -ForegroundColor Yellow
$portInUse = netstat -ano | Select-String ":8080.*LISTENING"
if ($portInUse) {
    Write-Host "  Backend ya corriendo en puerto 8080" -ForegroundColor Green
} else {
    Write-Host "  Iniciando Spring Boot..." -ForegroundColor Yellow
    $backend = Start-Process powershell -ArgumentList @(
        "-NoExit", "-Command",
        "cd '$root'; .\mvnw.cmd spring-boot:run '-Dspring-boot.run.profiles=$Profile'"
    ) -PassThru -WindowStyle Minimized
    Write-Host "  Backend iniciado (PID: $($backend.Id))" -ForegroundColor Green

    Write-Host "  Esperando a que el backend este listo..." -ForegroundColor Yellow
    $ready = $false
    for ($i = 1; $i -le 30; $i++) {
        Start-Sleep -Seconds 2
        try {
            $resp = Invoke-WebRequest -Uri "$BaseUrl/carga/productos/total" -TimeoutSec 2 -ErrorAction Stop
            if ($resp.StatusCode -eq 200) { $ready = $true; break }
        } catch {}
        Write-Host "  ...esperando ($i/30)" -ForegroundColor DarkGray
    }
    if ($ready) {
        Write-Host "  Backend listo!" -ForegroundColor Green
    } else {
        Write-Host "  Backend no respondio. Continuando de todas formas..." -ForegroundColor Red
    }
}

# 2) Iniciar Dashboard Web de monitoreo
Write-Host "`n[2/4] Iniciando Dashboard Web (puerto 9090)..." -ForegroundColor Yellow
$dashboard = Start-Process python -ArgumentList @(
    "$root\scripts\monitor-web.py"
) -PassThru -WindowStyle Minimized
Write-Host "  Dashboard PID: $($dashboard.Id)" -ForegroundColor Green
Start-Sleep -Seconds 2

# Abrir navegador
Start-Process "http://localhost:9090"
Write-Host "  Navegador abierto en http://localhost:9090" -ForegroundColor Green

# 3) Ejecutar k6
Write-Host "`n[3/4] Ejecutando k6 load test..." -ForegroundColor Yellow
Write-Host "  Script: performance/sesion29/escenarios-avanzados.js" -ForegroundColor DarkGray

$k6Output = "$root\evidencia\salida-k6.txt"
$k6Dir = "$root\performance\sesion29"

$k6 = Start-Process k6 -ArgumentList @(
    "run",
    "-e", "BASE_URL=$BaseUrl",
    "escenarios-avanzados.js"
) -PassThru -NoNewWindow -WorkingDirectory $k6Dir -RedirectStandardOutput $k6Output -RedirectStandardError "$k6Output.err"

Write-Host "  k6 PID: $($k6.Id)" -ForegroundColor Green

# 4) Esperar a que k6 termine
Write-Host "`n[4/4] k6 ejecutandose... monitoreo activo en http://localhost:9090" -ForegroundColor Cyan
Write-Host "  (presiona Ctrl+C si quieres detener manualmente)" -ForegroundColor DarkGray

$k6.WaitForExit()

Write-Host "`n============================================" -ForegroundColor Green
Write-Host "  k6 FINALIZADO" -ForegroundColor Green
Write-Host "============================================" -ForegroundColor Green
Write-Host "  Salida k6: $k6Output" -ForegroundColor DarkGray
Write-Host "  Dashboard: http://localhost:9090" -ForegroundColor DarkGray

# Leer y mostrar resumen de k6
if (Test-Path $k6Output) {
    $content = Get-Content $k6Output -Raw
    if ($content.Length -gt 2000) {
        Write-Host "`n--- ULTIMAS LINEAS k6 ---" -ForegroundColor Yellow
        Get-Content $k6Output -Tail 40
    } else {
        Write-Host "`n--- SALIDA k6 ---" -ForegroundColor Yellow
        Write-Host $content
    }
}

$errFile = "$k6Output.err"
if (Test-Path $errFile) {
    $errContent = Get-Content $errFile -Raw
    if ($errContent.Length -gt 0) {
        Write-Host "`n--- ERRORES k6 ---" -ForegroundColor Red
        Write-Host $errContent
    }
}

Write-Host "`nEl dashboard sigue corriendo. Cierra manualmente cuando termines." -ForegroundColor DarkGray
