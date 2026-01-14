@echo off
echo Building VitalFlow Java Simulation...

if not exist "java-impl\bin" mkdir "java-impl\bin"

javac -d java-impl\bin java-impl\src\com\vitalflow\*.java

if %errorlevel% neq 0 (
    echo Compilation Failed!
    pause
    exit /b %errorlevel%
)

echo Compilation Successful. Starting GUI Simulation...
echo.

start javaw -cp java-impl\bin com.vitalflow.VitalFlowGUI

echo GUI Launched in separate window.
pause
