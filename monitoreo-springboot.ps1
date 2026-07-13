# monitoreo-springboot.ps1
# Script para monitorear específicamente el PID 17060 (Spring Boot)

$PID_SPRING = 17060
$archivoCSV = "evidencia/metricas-springboot.csv"
$contador = 0

# Crear directorio si no existe
if (!(Test-Path "evidencia")) {
    New-Item -ItemType Directory -Path "evidencia" -Force
}

# Crear archivo CSV con encabezados
"Timestamp,CPU_Total,Memoria_MB,Memoria_Pico_MB,Threads,Handles,Productos_API,Iteraciones" | Out-File $archivoCSV

Clear-Host
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   MONITOREO SPRING BOOT - SESIÓN 29    " -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "PID: $PID_SPRING" -ForegroundColor Green
Write-Host "Archivo de métricas: $archivoCSV" -ForegroundColor Gray
Write-Host "Presiona Ctrl+C para detener" -ForegroundColor Gray
Write-Host ""

while ($true) {
    $contador++
    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    
    Clear-Host
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "   MONITOREO SPRING BOOT - #$contador    " -ForegroundColor Yellow
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "Hora: $timestamp" -ForegroundColor Gray
    Write-Host ""
    
    # Obtener información del proceso
    try {
        $proc = Get-Process -Id $PID_SPRING -ErrorAction SilentlyContinue
        
        if ($proc) {
            $cpu = [math]::Round($proc.CPU, 2)
            $memoria = [math]::Round($proc.WorkingSet / 1MB, 2)
            $memoriaPico = [math]::Round($proc.PeakWorkingSet / 1MB, 2)
            $threads = $proc.Threads.Count
            $handles = $proc.HandleCount
            
            Write-Host "📊 ESTADO DEL PROCESO:" -ForegroundColor Magenta
            Write-Host "  CPU: $cpu %" -ForegroundColor Cyan
            Write-Host "  Memoria: $memoria MB" -ForegroundColor Cyan
            Write-Host "  Memoria Pico: $memoriaPico MB" -ForegroundColor Cyan
            Write-Host "  Threads: $threads" -ForegroundColor Cyan
            Write-Host "  Handles: $handles" -ForegroundColor Cyan
            Write-Host "  Tiempo Activo: $([math]::Round($proc.TotalProcessorTime.TotalSeconds, 0)) segundos" -ForegroundColor Cyan
            
            # Gráfico de barras simple para memoria
            $barLength = [math]::Min([math]::Round($memoria / 10), 50)
            $bar = "█" * $barLength
            Write-Host "  Uso Memoria: [$bar] $memoria MB" -ForegroundColor Green
        } else {
            Write-Host "❌ PROCESO NO ENCONTRADO" -ForegroundColor Red
            Write-Host "  Verifica que la aplicación esté corriendo" -ForegroundColor Yellow
            $cpu = 0
            $memoria = 0
            $memoriaPico = 0
            $threads = 0
            $handles = 0
        }
    } catch {
        Write-Host "❌ Error al obtener el proceso" -ForegroundColor Red
        $cpu = 0
        $memoria = 0
        $memoriaPico = 0
        $threads = 0
        $handles = 0
    }
    
    Write-Host ""
    
    # Obtener métricas de la API
    Write-Host "🔗 MÉTRICAS DE LA API:" -ForegroundColor Magenta
    try {
        $response = Invoke-RestMethod -Uri "http://localhost:8080/carga/productos/total" -TimeoutSec 2 -ErrorAction SilentlyContinue
        $productos = $response.total
        Write-Host "  Total Productos: $productos" -ForegroundColor Green
        
        # También obtener el reporte si está disponible
        try {
            $reporte = Invoke-RestMethod -Uri "http://localhost:8080/carga/productos/reporte" -TimeoutSec 2 -ErrorAction SilentlyContinue
            Write-Host "  Reporte: $($reporte.resumen)" -ForegroundColor Gray
        } catch {
            # Ignorar si no está disponible
        }
    } catch {
        $productos = "Error"
        Write-Host "  ⚠️ API no disponible" -ForegroundColor Red
        Write-Host "  Verifica que la aplicación esté corriendo en http://localhost:8080" -ForegroundColor Yellow
    }
    
    Write-Host ""
    
    # Guardar en CSV
    $linea = "$timestamp,$cpu,$memoria,$memoriaPico,$threads,$handles,$productos,$contador"
    $linea | Out-File $archivoCSV -Append
    
    Write-Host "📝 Métrica #$contador guardada en CSV" -ForegroundColor Green
    
    Write-Host ""
    Write-Host "───" -ForegroundColor Gray
    Write-Host "Presiona Ctrl+C para detener el monitoreo" -ForegroundColor Gray
    Write-Host "Las métricas se guardan en: $archivoCSV" -ForegroundColor Gray
    
    Start-Sleep -Seconds 5
}