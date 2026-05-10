$ProjectRoot = Split-Path -Parent $PSScriptRoot
$TomcatHome = "C:\apache-tomcat-9.0.117"
$AppName = "Framtracker"
$WarFile = Join-Path $ProjectRoot "target\$AppName.war"
$TargetWar = Join-Path $TomcatHome "webapps\$AppName.war"
$TargetFolder = Join-Path $TomcatHome "webapps\$AppName"

& (Join-Path $PSScriptRoot "compile-java.ps1")
if ($LASTEXITCODE -ne 0) {
    exit 1
}

Write-Host "Deploying $AppName to Tomcat..."

if (Test-Path $TargetFolder) {
    Remove-Item -LiteralPath $TargetFolder -Recurse -Force
}
if (Test-Path $TargetWar) {
    Remove-Item -LiteralPath $TargetWar -Force
}

Copy-Item -Path $WarFile -Destination $TargetWar -Force

Write-Host "Deployment complete."
Write-Host "Open: http://localhost:8081/$AppName/index.jsp"
