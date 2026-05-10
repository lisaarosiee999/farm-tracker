$DatabaseName = "farmtracker"
$Username = "root"
$Password = "root"
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
Get-Content -Raw $SchemaFile | & $mysql.FullName -u $Username "-p$Password"

if ($LASTEXITCODE -eq 0) {
    Write-Host "Database setup complete."
} else {
    Write-Host "Database setup failed. Check MySQL username/password and server status."
    exit 1
}
