package com.vitalflow.test;

import com.vitalflow.Controller;
import com.vitalflow.test.SimpleTestRunner.Test;
import static com.vitalflow.test.SimpleTestRunner.*;

public class ControllerLogicTest {

    @Test
    public void testHypoglycemiaProtection() {
        // Test boundary 69 (Just below 70)
        assertFalse(Controller.decidePumpAction(69), "Pump must be OFF for 69");
        // Test deep low
        assertFalse(Controller.decidePumpAction(40), "Pump must be OFF for 40");
    }

    @Test
    public void testNormalRangeIdle() {
        // Test boundary 70 (Just safe)
        assertFalse(Controller.decidePumpAction(70), "Pump must be IDLE for 70");
        // Test middle
        assertFalse(Controller.decidePumpAction(150), "Pump must be IDLE for 150");
        // Test boundary 199 (Just below high)
        assertFalse(Controller.decidePumpAction(199), "Pump must be IDLE for 199");
    }

    @Test
    public void testHyperglycemiaResponse() {
        // Test boundary 200 (Trigger point)
        assertTrue(Controller.decidePumpAction(200), "Pump must be ACTIVE for 200");
        // Test high
        assertTrue(Controller.decidePumpAction(300), "Pump must be ACTIVE for 300");
    }
}
