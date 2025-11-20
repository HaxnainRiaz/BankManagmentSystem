@echo off
echo ========================================
echo   Bank Management System - Compiler
echo ========================================
echo.

cd sanbank

echo Checking Java installation...
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java is not installed or not in PATH
    pause
    exit /b 1
)

echo Java found!
echo.

echo Checking MySQL connector JAR...
set MYSQL_JAR=..\mysql-connector-java-5.1.49\mysql-connector-java-5.1.49\mysql-connector-java-5.1.49-bin.jar
if not exist "%MYSQL_JAR%" (
    echo ERROR: MySQL connector JAR not found at: %MYSQL_JAR%
    pause
    exit /b 1
)

echo MySQL connector found!
echo.

echo Creating build directory...
if not exist "build\classes" mkdir "build\classes"

echo.
echo Compiling Java files...
javac -cp "%MYSQL_JAR%" -d "build\classes" src\bank\*.java

if errorlevel 1 (
    echo.
    echo ERROR: Compilation failed!
    pause
    exit /b 1
)

echo.
echo ========================================
echo   Compilation Successful!
echo ========================================
echo.
echo You can now run the application using: run.bat
echo.
pause

