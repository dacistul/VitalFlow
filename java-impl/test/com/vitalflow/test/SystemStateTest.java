package com.vitalflow.test;

import com.vitalflow.SystemState;
import com.vitalflow.test.SimpleTestRunner.Test;
import com.vitalflow.test.SimpleTestRunner.Before;
import static com.vitalflow.test.SimpleTestRunner.*;

public class SystemStateTest {

    private SystemState state;

    @Before
    public void setup() {
        state = new SystemState();
    }

    @Test
    public void testInitialValues() {
        assertEquals(100, state.getGlucoseLevel());
        assertEquals(10, state.getInsulinReservoir());
        assertFalse(state.isPumpActive(), "Pump should be initially inactive");
    }

    @Test
    public void testGlucoseUpdate() {
        state.updateGlucose(20);
        assertEquals(120, state.getGlucoseLevel());
        
        state.updateGlucose(-50);
        assertEquals(70, state.getGlucoseLevel());
    }

    @Test
    public void testPumpCycleReducesReservoirAndGlucose() {
        // Initial: Gluc=100, Res=10
        state.executePumpCycle();
        
        // Expected: Gluc=85 (100-15), Res=9 (10-1)
        assertEquals(85, state.getGlucoseLevel());
        assertEquals(9, state.getInsulinReservoir());
        assertTrue(state.isPumpActive(), "Pump should be active after cycle");
    }

    @Test
    public void testPumpStopsAtEmptyReservoir() {
        // Drain the reservoir manually to 0
        for(int i=0; i<10; i++) {
            state.executePumpCycle();
        }
        assertEquals(0, state.getInsulinReservoir());
        
        // Try one more pump
        state.executePumpCycle();
        
        // Should not go negative
        assertEquals(0, state.getInsulinReservoir());
        // Should trigger alarm
        assertTrue(state.isAlarmTriggered(), "Alarm should trigger when pumping on empty");
        // Pump should deactivate
        assertFalse(state.isPumpActive(), "Pump should stop when empty");
    }
}
