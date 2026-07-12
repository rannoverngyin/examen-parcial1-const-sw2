param(
    [int]$DurationSec = 90
)

$javaProcs = Get-Process java -ErrorAction SilentlyContinue | Where-Object { $_.WorkingSet64 -gt 50MB }
if (-not $javaProcs) { Write-Output "No Java process found >50MB"; exit 1 }
$javaPid = $javaProcs[0].Id
Write-Output "Monitoring Java PID: $javaPid"

$logFile = "C:\Users\ZUZUKA\examen-parcial1-const-sw2\scripts\metrics-log.csv"
"Timestamp,ElapsedSec,Phase,CPU_sec,MemMB" | Out-File -FilePath $logFile -Encoding UTF8

$startTime = Get-Date
$prevCPU = 0

function Get-Phase($elapsed) {
    if ($elapsed -lt 10) { return "INICIO (0-10VUs ramp)" }
    elseif ($elapsed -lt 30) { return "ESTABLE_10VUs (10-30s)" }
    elseif ($elapsed -lt 40) { return "RAMPEO_30VUs (30-40s)" }
    elseif ($elapsed -lt 60) { return "ESTABLE_30VUs (40-60s)" }
    elseif ($elapsed -lt 70) { return "BAJADA (60-70s)" }
    else { return "FIN" }
}

$lastPhase = ""
while ($true) {
    $proc = Get-Process -Id $javaPid -ErrorAction SilentlyContinue
    if (-not $proc) { Write-Output "Java ended"; break }

    $elapsed = ((Get-Date) - $startTime).TotalSeconds
    if ($elapsed -gt $DurationSec + 15) { break }

    $memMB = [math]::Round($proc.WorkingSet64 / 1MB, 2)
    $cpuSec = [math]::Round($proc.CPU, 2)
    $cpuDelta = [math]::Round($cpuSec - $prevCPU, 2)
    $phase = Get-Phase $elapsed

    if ($phase -ne $lastPhase) {
        Write-Output "=== $phase | CPU acum: ${cpuSec}s | CPU delta/2s: ${cpuDelta}s | Mem: ${memMB}MB ==="
        $lastPhase = $phase
    }

    "$($(Get-Date).ToString('HH:mm:ss.fff')),$([math]::Round($elapsed,1)),$phase,$cpuSec,$memMB" | Out-File -FilePath $logFile -Append -Encoding UTF8

    $prevCPU = $cpuSec
    Start-Sleep -Seconds 2
}
Write-Output "Monitor done. Log: $logFile"
