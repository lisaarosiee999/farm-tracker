$TomcatHome = "C:\apache-tomcat-9.0.117"

Write-Host "Stopping Tomcat..."
& "$TomcatHome\bin\shutdown.bat"
Start-Sleep -Seconds 5
Write-Host "Stop command sent. If Tomcat is still running, check the VS Code terminal output."
