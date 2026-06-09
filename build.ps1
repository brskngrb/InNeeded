param([Parameter(ValueFromRemainingArguments=$true)][string[]]$GradleArgs)
$ErrorActionPreference = "Stop"
$GradleVersion = "8.10.2"
$ProjectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$LocalDir = Join-Path $ProjectRoot ".gradle-local"
$GradleHome = Join-Path $LocalDir "gradle-$GradleVersion"
$GradleZip = Join-Path $LocalDir "gradle-$GradleVersion-bin.zip"
$GradleExe = Join-Path $GradleHome "bin\gradle.bat"
$DistributionUrl = "https://services.gradle.org/distributions/gradle-$GradleVersion-bin.zip"
if (-not $GradleArgs -or $GradleArgs.Count -eq 0) { $GradleArgs = @("build") }
if (-not (Test-Path $GradleExe)) {
    New-Item -ItemType Directory -Force -Path $LocalDir | Out-Null
    if (-not (Test-Path $GradleZip)) {
        [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
        Invoke-WebRequest -Uri $DistributionUrl -OutFile $GradleZip
    }
    Expand-Archive -Path $GradleZip -DestinationPath $LocalDir -Force
}
& $GradleExe @GradleArgs
exit $LASTEXITCODE
