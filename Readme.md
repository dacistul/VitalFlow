PROJECT: VitalFlow - Automated Insulin Pump Verification <br>
TEAM: Dumitrof Dan-Stefan, Stavarache Mihaela, Ion Cristina <br>
DATE: 14-JAN-2026
-------------------------------------------------------

## 1. Project Overview
This project contains both the **Formal Verification** (Promela/Spin) and the **Java Implementation** of the VitalFlow Insulin Pump.

## 2. File List
**Formal Verification (Folder: `program/`)**
*   `vitalflow.pml`: Promela source code for logic verification.
*   `TP-VF-001.md`: Master Test Plan (IEEE 829).
*   `TSR-VF-001.md`: Test Summary Report (IEEE 829).

**Java Implementation (Folder: `java-impl/`)**
*   `src/com/vitalflow/`: Source code for Sensor, Controller, Pump, and GUI.
*   `test/com/vitalflow/`: Unit tests and custom Test Runner.
*   `TP-JAVA-001.md`: Test Plan for the Java Application.
*   `TSR-JAVA-001.md`: Test Summary Report for the Java Application.
*   `SPR-JAVA-001.md`: Software Problem Report (UI Readability).
*   `SPR-JAVA-002.md`: Software Problem Report (Sensor Noise).

## 3. How to Run (Java Application)
We have provided batch scripts for easy execution on Windows.

**To Run the GUI Simulation:**
1.  Double-click `run_simulation.bat` 
    *(Or run `.\run_simulation.bat` in the terminal)*.
2.  A window will open showing the real-time status of Glucose, Reservoir, and Pump.

**To Run the Unit Tests:**
1.  Double-click `run_tests.bat`
    *(Or run `.\run_tests.bat` in the terminal)*.
2.  The console will display the pass/fail status of all 8 automated tests.

## 4. How to Run (Formal Verification)
**Prerequisites:** Spin Model Checker and GCC.
1.  Navigate to the `program/` directory.
2.  Run the simulation: `spin vitalflow.pml`
3.  Run the safety check:
    ```bash
    spin -a -N safety_check vitalflow.pml
    gcc -o pan pan.c
    ./pan -a
    ```
