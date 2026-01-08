PROJECT: VitalFlow - Automated Insulin Pump Verification
TEAM: Dumitrof Dan-Stefan, Ion Cristina, Stavarache Mihaela
DATE: 19-NOV-2025
-------------------------------------------------------
FILE LIST:
1. vitalflow.pml ...... Promela source code for logic verification.
2. TP-VF-001.doc ...... Master Test Plan (IEEE 829).
3. TSR-VF-001.doc ..... Test Summary Report (IEEE 829).

HOW TO RUN THE VERIFICATION (DESIGN TEST):
1. Open the 'vitalflow.pml' file in jSpin or command line Spin.
2. To verify the Safety Property (Pump never active when Sugar < 70):
   - Command: spin -run -a -N safety_check vitalflow.pml
   - Expected Output: "errors: 0"
3. To verify Liveness (Pump eventually activates if Sugar > 200):
   - Command: spin -run -a -N liveness_check vitalflow.pml
   - Expected Output: "errors: 0"

HOW TO RUN THE SOFTWARE TEST (APP):
1. (Note: The mobile UI is a prototype. Refer to Section 6 of the Test Plan for manual test cases).