@echo off
echo ========================================
echo   Bank Management System - Launcher
echo ========================================
echo.

cd sanbank

echo Checking if project is compiled...
if not exist "build\classes\bank\login.class" (
    echo ERROR: Project not compiled. Please run compile.bat first
    pause
    exit /b 1
)

echo Project is compiled!
echo.

echo Checking MySQL connector JAR...
set MYSQL_JAR=..\mysql-connector-java-5.1.49\mysql-connector-java-5.1.49\mysql-connector-java-5.1.49-bin.jar
if not exist "%MYSQL_JAR%" (
    echo ERROR: MySQL connector JAR not found
    pause
    exit /b 1
)

echo.
echo Starting application...
echo.
echo Login Credentials:
echo   Username: admin
echo   Password: 12345678
echo.
echo ========================================
echo.

java -cp "build\classes;%MYSQL_JAR%" bank.login

if errorlevel 1 (
    echo.
    echo ERROR: Application failed to start!
    echo.
    echo Common issues:
    echo   1. MySQL server not running
    echo   2. Database 'bankproject' not created
    echo   3. Database connection error
    echo.
    pause
    exit /b 1
)

pause

