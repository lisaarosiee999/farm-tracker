$ProjectRoot = Split-Path -Parent $PSScriptRoot
$SourceFolder = Join-Path $ProjectRoot "WebContent"
$TomcatHome = "C:\apache-tomcat-9.0.117"
$AppName = "Framtracker"
$TargetFolder = Join-Path $TomcatHome "webapps\$AppName"

if (-not (Test-Path $SourceFolder)) {
    Write-Host "Cannot find WebContent folder: $SourceFolder"
    exit 1
}

& (Join-Path $PSScriptRoot "compile-java.ps1")
if ($LASTEXITCODE -ne 0) {
    exit 1
}

Write-Host "Deploying $AppName to Tomcat..."

if (Test-Path $TargetFolder) {
    Remove-Item -LiteralPath $TargetFolder -Recurse -Force
}

New-Item -ItemType Directory -Force -Path $TargetFolder | Out-Null
Copy-Item -Path "$SourceFolder\*" -Destination $TargetFolder -Recurse -Force

Write-Host "Deployment complete."
Write-Host "Open: http://localhost:8081/$AppName/index.jsp"
