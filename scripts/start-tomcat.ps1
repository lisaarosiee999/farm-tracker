$TomcatHome = "C:\apache-tomcat-9.0.117"
$Port = 8081

# If Tomcat is already running, do not start another copy.
$connection = Get-NetTCPConnection -LocalPort $Port -ErrorAction SilentlyContinue
if ($connection) {
    Write-Host "Tomcat is already running on port $Port."
    exit 0
}

Write-Host "Starting Tomcat from $TomcatHome ..."
Start-Process -FilePath "$TomcatHome\bin\catalina.bat" `
    -ArgumentList "run" `
    -WorkingDirectory "$TomcatHome\bin" `
    -WindowStyle Hidden

Start-Sleep -Seconds 8

$connection = Get-NetTCPConnection -LocalPort $Port -ErrorAction SilentlyContinue
if ($connection) {
    Write-Host "Tomcat started successfully on http://localhost:$Port"
} else {
    Write-Host "Tomcat did not start. Check: $TomcatHome\logs"
    exit 1
}
