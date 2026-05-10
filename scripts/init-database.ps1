$DatabaseName = "farmtracker"
$Username = if ($env:FARMTRACKER_DB_USER) { $env:FARMTRACKER_DB_USER } else { "root" }
$Password = if (Test-Path Env:FARMTRACKER_DB_PASSWORD) { $env:FARMTRACKER_DB_PASSWORD } else { "root" }
$SchemaFile = Join-Path (Split-Path -Parent $PSScriptRoot) "database\schema.sql"

$mysql = Get-Command mysql -ErrorAction SilentlyContinue

if (-not $mysql) {
    $mysql = Get-ChildItem -Path "C:\Program Files", "C:\Program Files (x86)" `
        -Recurse -Filter mysql.exe -ErrorAction SilentlyContinue |
        Select-Object -First 1
}

if (-not $mysql) {
    Write-Host "mysql.exe was not found. Install MySQL Server, then run this task again."
    exit 1
}

Write-Host "Creating database and tables for $DatabaseName ..."
if ($Password -eq "") {
    Get-Content -Raw $SchemaFile | & $mysql.FullName -u $Username
} else {
    Get-Content -Raw $SchemaFile | & $mysql.FullName -u $Username "-p$Password"
}

if ($LASTEXITCODE -eq 0) {
    Write-Host "Database setup complete."
} else {
    Write-Host "Database setup failed. Check MySQL username/password and server status."
    Write-Host "You can override credentials with FARMTRACKER_DB_USER and FARMTRACKER_DB_PASSWORD."
    exit 1
}
