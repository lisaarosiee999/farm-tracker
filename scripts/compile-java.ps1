$ProjectRoot = Split-Path -Parent $PSScriptRoot

Write-Host "Building WAR with Maven..."
Push-Location $ProjectRoot
mvn package
$exitCode = $LASTEXITCODE
Pop-Location

if ($exitCode -eq 0) {
    Write-Host "Build successful: target\Framtracker.war"
} else {
    Write-Host "Build failed."
    exit $exitCode
}
