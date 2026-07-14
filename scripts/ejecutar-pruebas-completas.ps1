# Automation script for k6 load test + parallel NMT resource capture
$ErrorActionPreference = "Continue"
$root = "C:\Users\ZUZUKA\examen-parcial1-const-sw2"

Write-Host "=========================================================" -ForegroundColor Cyan
Write-Host "  SESION 29 - AUTOMATIZACION DE PRUEBAS Y CAPTURA NMT  " -ForegroundColor Cyan
Write-Host "=========================================================" -ForegroundColor Cyan

# 1) Detect Spring Boot Java PID on port 8080
Write-Host "`n[1] Localizando proceso Java en puerto 8080..." -ForegroundColor Yellow
$javaPid = (Get-NetTCPConnection -LocalPort 8080 -State Listen -ErrorAction SilentlyContinue).OwningProcess
if (-not $javaPid) {
    Write-Error "No se encontro ningun proceso escuchando en el puerto 8080. Por favor, inicia el backend primero."
    exit 1
}
Write-Host "  Spring Boot Java PID encontrado: $javaPid" -ForegroundColor Green

# 2) Find jcmd binary path
$jcmdPath = Get-Command jcmd -ErrorAction SilentlyContinue | Select-Object -ExpandProperty Source
if (-not $jcmdPath) {
    $jcmdPath = Get-ChildItem "C:\Program Files\Java" -Filter "jcmd.exe" -Recurse -ErrorAction SilentlyContinue | Select-Object -ExpandProperty FullName -First 1
}
if (-not $jcmdPath) {
    $jcmdPath = "C:\Program Files\Java\jdk-26.0.1\bin\jcmd.exe"
}
Write-Host "  Uso de jcmd en: $jcmdPath" -ForegroundColor DarkGray

# Verify NMT is active
$nmtTest = & $jcmdPath $javaPid VM.native_memory summary 2>&1
if ($nmtTest -match "Native memory tracking is not enabled") {
    Write-Warning "Native Memory Tracking (NMT) NO esta habilitado en el proceso Java. Las capturas jcmd fallaran."
    Write-Warning "Asegurate de iniciar Java con: -XX:NativeMemoryTracking=summary"
} else {
    Write-Host "  Native Memory Tracking (NMT) esta ACTIVO en el proceso." -ForegroundColor Green
}

# 3) Ensure evidence directory exists
$evidenciaDir = Join-Path $root "evidencia"
if (-not (Test-Path $evidenciaDir)) {
    New-Item -ItemType Directory -Path $evidenciaDir | Out-Null
}

# 4) Capture baseline NMT snapshot
Write-Host "`n[2] Capturando Native Memory Tracking BASELINE (VUs = 0)..." -ForegroundColor Yellow
$baselineFile = Join-Path $evidenciaDir "nmt-baseline.txt"
& $jcmdPath $javaPid VM.native_memory summary > $baselineFile
Write-Host "  Guardado en: $baselineFile" -ForegroundColor Green

# 5) Run k6 in parallel
Write-Host "`n[3] Iniciando k6 load test..." -ForegroundColor Yellow
$k6Output = Join-Path $evidenciaDir "salida-k6.txt"
$k6Script = Join-Path $root "performance\sesion29\escenarios-avanzados.js"
$k6Dir = Join-Path $root "performance\sesion29"

Write-Host "  Script: $k6Script" -ForegroundColor DarkGray
Write-Host "  Salida k6: $k6Output" -ForegroundColor DarkGray

$k6Process = Start-Process k6 -ArgumentList @(
    "run",
    "-e", "BASE_URL=http://localhost:8080",
    "escenarios-avanzados.js"
) -PassThru -NoNewWindow -WorkingDirectory $k6Dir -RedirectStandardOutput $k6Output -RedirectStandardError "$k6Output.err"

Write-Host "  k6 iniciado con PID: $($k6Process.Id)" -ForegroundColor Green

# 6) Periodically log metrics and capture snapshots at stages
Write-Host "`n[4] Monitoreando recursos y capturando snapshots NMT en paralelo..." -ForegroundColor Yellow
$startTime = Get-Date

$capturedSteady10 = $false
$capturedPeak = $false
$capturedRampDown = $false

# Header for console log
Write-Host "---------------------------------------------------------------------------------" -ForegroundColor Gray
Write-Host "Tiempo (s) | Etapa Estimada | Memoria (MB) | CPU Acum (s) | Threads | NMT Captura" -ForegroundColor Gray
Write-Host "---------------------------------------------------------------------------------" -ForegroundColor Gray

while (-not $k6Process.HasExited) {
    Start-Sleep -Seconds 2
    $elapsed = [math]::Round(((Get-Date) - $startTime).TotalSeconds, 1)
    
    # Get JVM Process details
    $proc = Get-Process -Id $javaPid -ErrorAction SilentlyContinue
    if (-not $proc) {
        Write-Host "  El proceso Java se detuvo inesperadamente!" -ForegroundColor Red
        break
    }
    
    $memMB = [math]::Round($proc.WorkingSet64 / 1MB, 2)
    $cpuSec = [math]::Round($proc.CPU, 2)
    $threads = $proc.Threads.Count
    
    # Estimate k6 stages based on script timeline
    $stage = "Ramp-Up 0->10"
    $nmtStatus = "-"
    
    if ($elapsed -ge 20 -and $elapsed -lt 80) {
        $stage = "Estable 10 VUs + Reg"
        if ($elapsed -ge 50 -and -not $capturedSteady10) {
            $steadyFile = Join-Path $evidenciaDir "nmt-steady-10vus.txt"
            & $jcmdPath $javaPid VM.native_memory summary > $steadyFile
            $capturedSteady10 = $true
            $nmtStatus = "Capturado Steady-10"
        }
    }
    elseif ($elapsed -ge 80 -and $elapsed -lt 110) {
        $stage = "Peak Load (Ramp 25 + Reportes)"
        if ($elapsed -ge 98 -and -not $capturedPeak) {
            $peakFile = Join-Path $evidenciaDir "nmt-peak-load.txt"
            & $jcmdPath $javaPid VM.native_memory summary > $peakFile
            $capturedPeak = $true
            $nmtStatus = "Capturado Peak-Load"
        }
    }
    elseif ($elapsed -ge 110 -and $elapsed -lt 140) {
        $stage = "Estable 25 VUs"
    }
    elseif ($elapsed -ge 140 -and $elapsed -lt 160) {
        $stage = "Ramp-Down 25->0"
        if ($elapsed -ge 148 -and -not $capturedRampDown) {
            $rampDownFile = Join-Path $evidenciaDir "nmt-ramp-down.txt"
            & $jcmdPath $javaPid VM.native_memory summary > $rampDownFile
            $capturedRampDown = $true
            $nmtStatus = "Capturado Ramp-Down"
        }
    }
    elseif ($elapsed -ge 160) {
        $stage = "Post-Test (GC Pendiente)"
    }
    
    # Output metrics row
    $elapsedStr = $elapsed.ToString().PadRight(10)
    $stageStr = $stage.PadRight(25)
    $memStr = "${memMB} MB".PadRight(15)
    $cpuStr = "${cpuSec} s".PadRight(15)
    $threadStr = $threads.ToString().PadRight(9)
    
    if ($nmtStatus -ne "-") {
        Write-Host "$elapsedStr | $stageStr | $memStr | $cpuStr | $threadStr | $nmtStatus" -ForegroundColor Green
    } else {
        Write-Host "$elapsedStr | $stageStr | $memStr | $cpuStr | $threadStr | -" -ForegroundColor Gray
    }
}

Write-Host "---------------------------------------------------------------------------------" -ForegroundColor Gray
Write-Host "k6 ha terminado de ejecutar el test de carga." -ForegroundColor Green

# 7) Post-Test cleanup and final NMT capture after GC
Write-Host "`n[5] Esperando 10 segundos para estabilizacion..." -ForegroundColor Yellow
Start-Sleep -Seconds 10

Write-Host "Ejecutando Garbage Collection manual en el proceso..." -ForegroundColor Yellow
$gcResult = & $jcmdPath $javaPid GC.run 2>&1
Start-Sleep -Seconds 2

Write-Host "Capturando Native Memory Tracking POST-TEST (VUs = 0)..." -ForegroundColor Yellow
$postFile = Join-Path $evidenciaDir "nmt-post-test.txt"
& $jcmdPath $javaPid VM.native_memory summary > $postFile
Write-Host "  Guardado en: $postFile" -ForegroundColor Green

# 8) Summarize results
Write-Host "`n=========================================================" -ForegroundColor Green
Write-Host "  PROCESAMIENTO FINALIZADO CON EXITO  " -ForegroundColor Green
Write-Host "=========================================================" -ForegroundColor Green
Write-Host "  Evidencia generada en: $evidenciaDir" -ForegroundColor DarkGray
Write-Host "  - nmt-baseline.txt (Estado inicial)" -ForegroundColor DarkGray
Write-Host "  - nmt-steady-10vus.txt (Carga media)" -ForegroundColor DarkGray
Write-Host "  - nmt-peak-load.txt (Carga maxima)" -ForegroundColor DarkGray
Write-Host "  - nmt-ramp-down.txt (Fase de bajada)" -ForegroundColor DarkGray
Write-Host "  - nmt-post-test.txt (Post-GC final)" -ForegroundColor DarkGray
Write-Host "  - salida-k6.txt (Log completo de k6)" -ForegroundColor DarkGray

# Display tail of k6 output
if (Test-Path $k6Output) {
    Write-Host "`n--- RESUMEN K6 ---" -ForegroundColor Yellow
    Get-Content $k6Output -Tail 35
}
