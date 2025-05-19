@echo off
setlocal

REM Check if gradlew exists
if not exist gradlew (
    echo ERROR: gradlew not found in current directory.
    exit /b 1
)

REM Run the build
echo Building the project with Gradle...
call gradlew installDist
if errorlevel 1 (
    echo ERROR: Gradle build failed.
    exit /b 1
)

REM Run the CLI application
set APP_EXEC=app\build\install\app\bin\app.bat
if exist "%APP_EXEC%" (
    echo Running Minesweeper CLI...
    call "%APP_EXEC%"
) else (
    echo ERROR: Executable "%APP_EXEC%" not found.
    exit /b 1
)

endlocal
