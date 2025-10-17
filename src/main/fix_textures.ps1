# PowerShell script to fix all texture references
# Run this from your project root

$modelDir = "src\main\resources\assets\charmncraft\models\block"

if (-not (Test-Path $modelDir)) {
    Write-Host "Error: Model directory not found at $modelDir" -ForegroundColor Red
    exit 1
}

Write-Host "Fixing texture references in model files..." -ForegroundColor Green
Write-Host ""

$fixedCount = 0

Get-ChildItem -Path $modelDir -Filter "*.json" -Recurse | ForEach-Object {
    $file = $_.FullName
    $content = Get-Content $file -Raw
    
    if ($content -like "*casualness_delight:block*") {
        $newContent = $content -replace 'casualness_delight:block', 'charmncraft:block'
        Set-Content -Path $file -Value $newContent
        Write-Host "Fixed: $($_.Name)" -ForegroundColor Green
        $fixedCount++
    }
}

Write-Host ""
Write-Host "Done! Fixed $fixedCount files." -ForegroundColor Green
