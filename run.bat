@echo off
setlocal

REM Check if gradlew exists
if not exist gradlew (
    echo ERROR: gradlew not found in current directory.
    exit /b 1
)

set APP_EXEC=app\build\install\app\bin\app.bat

REM Check first argument for "debug"
if /i "%~1"=="debug" (
    echo Debug mode: forcing build...
    call gradlew installDist
    if errorlevel 1 (
        echo ERROR: Gradle build failed.
        exit /b 1
    )
) else (
    REM If executable does not exist, build
    if not exist "%APP_EXEC%" (
        echo Executable not found. Building project...
        call gradlew installDist
        if errorlevel 1 (
            echo ERROR: Gradle build failed.
            exit /b 1
        )
    ) else (
        echo Executable found. Skipping build.
    )
)

REM Run the CLI application
if exist "%APP_EXEC%" (
    echo Running Minesweeper CLI...
    call "%APP_EXEC%"
) else (
    echo ERROR: Executable "%APP_EXEC%" not found.
    exit /b 1
)

endlocal
