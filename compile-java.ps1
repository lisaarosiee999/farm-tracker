$ProjectRoot = Split-Path -Parent $PSScriptRoot
$SourceFolder = Join-Path $ProjectRoot "src"
$ClassesFolder = Join-Path $ProjectRoot "WebContent\WEB-INF\classes"
$TomcatServletJar = "C:\apache-tomcat-9.0.117\lib\servlet-api.jar"
$MysqlJar = Join-Path $ProjectRoot "WebContent\WEB-INF\lib\mysql-connector-j-8.4.0.jar"

if (-not (Test-Path $TomcatServletJar)) {
    Write-Host "Cannot find servlet-api.jar at $TomcatServletJar"
    exit 1
}

if (-not (Test-Path $MysqlJar)) {
    Write-Host "Cannot find MySQL JDBC jar at $MysqlJar"
    exit 1
}

New-Item -ItemType Directory -Force -Path $ClassesFolder | Out-Null
Get-ChildItem -Path $ClassesFolder -Recurse -Filter *.class -ErrorAction SilentlyContinue |
    Remove-Item -Force

$javaFiles = Get-ChildItem -Path $SourceFolder -Recurse -Filter *.java

if (-not $javaFiles) {
    Write-Host "No Java files found."
    exit 1
}

$classPath = "$TomcatServletJar;$MysqlJar"

Write-Host "Compiling Java files..."
javac -encoding UTF-8 -cp $classPath -d $ClassesFolder $javaFiles.FullName

if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilation successful."
} else {
    Write-Host "Compilation failed."
    exit 1
}
