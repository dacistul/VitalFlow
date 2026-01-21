@echo off
echo Building VitalFlow Tests...

if not exist "java-impl\bin" mkdir "java-impl\bin"

REM 1. Compile Source Code
javac -d java-impl\bin java-impl\src\com\vitalflow\*.java
if %errorlevel% neq 0 exit /b %errorlevel%

REM 2. Compile Test Framework and Tests
javac -cp java-impl\bin -d java-impl\bin java-impl\test\com\vitalflow\test\*.java
if %errorlevel% neq 0 (
    echo Test Compilation Failed!
    pause
    exit /b %errorlevel%
)

echo.

echo Running Unit Tests...
echo.

REM 3. Run the Custom Test Runner
java -cp java-impl\bin com.vitalflow.test.SimpleTestRunner ^
    com.vitalflow.test.SystemStateTest ^
    com.vitalflow.test.ControllerLogicTest

if %errorlevel% neq 0 (
    echo.
    echo TESTS FAILED!
    exit /b 1
)

echo.

echo ALL TESTS PASSED.
pause
