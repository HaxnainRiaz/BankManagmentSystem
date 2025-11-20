@echo off
echo ========================================
echo   Bank Management System - Full Setup
echo ========================================
echo.
echo This script will:
echo   1. Compile the project
echo   2. Check database setup
echo   3. Run the application
echo.
pause

echo.
echo Step 1: Compiling project...
call compile.bat
if errorlevel 1 (
    echo Compilation failed. Exiting.
    pause
    exit /b 1
)

echo.
echo Step 2: Database Check
echo.
echo IMPORTANT: Make sure you have:
echo   1. MySQL server running
echo   2. Database 'bankproject' created
echo   3. SQL file imported (sanbank/database/sanbank.sql)
echo.
echo If not set up, please:
echo   1. Start MySQL server
echo   2. Create database: CREATE DATABASE bankproject;
echo   3. Import SQL: mysql -u root -p bankproject ^< sanbank/database/sanbank.sql
echo.
pause

echo.
echo Step 3: Starting application...
call run.bat

