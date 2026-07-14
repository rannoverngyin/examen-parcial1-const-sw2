$javaPid = (Get-NetTCPConnection -LocalPort 8080 -State Listen -ErrorAction SilentlyContinue).OwningProcess
if (-not $javaPid) {
    $javaPid = (Get-Process java | Where-Object { $_.WorkingSet64 -gt 50MB }).Id | Select-Object -First 1
}
Write-Output "Monitoreando Java PID: $javaPid"

$logFile = "C:\Users\ZUZUKA\examen-parcial1-const-sw2\scripts\metrics-log.csv"
"Timestamp,ElapsedSec,VUs,CPU_sec,MemMB,CPU_Pct" | Out-File -FilePath $logFile -Encoding UTF8

$startTime = Get-Date
$stage = "inicio"

while ($true) {
    $proc = Get-Process -Id $javaPid -ErrorAction SilentlyContinue
    if (-not $proc) { Write-Output "Java process ended"; break }

    $elapsed = ((Get-Date) - $startTime).TotalSeconds
    $memMB = [math]::Round($proc.WorkingSet64 / 1MB, 2)
    $cpuSec = [math]::Round($proc.CPU, 2)

    # Estimate VU stage based on elapsed time (matching k6 load.js stages)
    if ($elapsed -lt 10) { $vus = "0-10"; $stage = "ramp-up-10" }
    elseif ($elapsed -lt 30) { $vus = "10"; $stage = "steady-10" }
    elseif ($elapsed -lt 45) { $vus = "10-30"; $stage = "ramp-up-30" }
    elseif ($elapsed -lt 65) { $vus = "30"; $stage = "steady-30" }
    elseif ($elapsed -lt 80) { $vus = "30-0"; $stage = "ramp-down" }
    else { $vus = "0"; $stage = "done" }

    $line = "$(Get-Date -Format 'HH:mm:ss.fff'),$([math]::Round($elapsed,1)),$vus,$cpuSec,$memMB,"
    Write-Output "$stage | Elapsed: $([math]::Round($elapsed,1))s | VUs: $vus | CPU: ${cpuSec}s | Mem: ${memMB}MB"
    "$line" | Out-File -FilePath $logFile -Append -Encoding UTF8

    Start-Sleep -Milliseconds 500
}
Write-Output "Monitoreo finalizado. Log: $logFile"
