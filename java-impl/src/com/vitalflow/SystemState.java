package com.vitalflow;

/**
 * Represents the shared state of the system, mirroring the global variables in vitalflow.pml.
 * Access to these variables is synchronized to prevent race conditions, ensuring thread safety.
 */
public class SystemState {
    private int glucoseLevel = 100;
    private int insulinReservoir = 10;
    private boolean pumpActive = false;
    private boolean alarmTriggered = false;
    
    private final java.beans.PropertyChangeSupport support = new java.beans.PropertyChangeSupport(this);

    public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    // --- Glucose Level Access ---
    public synchronized int getGlucoseLevel() {
        return glucoseLevel;
    }

    public synchronized void updateGlucose(int delta) {
        int oldVal = this.glucoseLevel;
        this.glucoseLevel += delta;
        support.firePropertyChange("glucose", oldVal, this.glucoseLevel);
        // System.out.println("DEBUG: Glucose updated to " + glucoseLevel);
    }

    // --- Insulin Reservoir Access ---
    public synchronized int getInsulinReservoir() {
        return insulinReservoir;
    }

    // --- Pump Status Access ---
    public synchronized boolean isPumpActive() {
        return pumpActive;
    }

    public synchronized void setPumpActive(boolean active) {
        boolean oldVal = this.pumpActive;
        this.pumpActive = active;
        support.firePropertyChange("pumpActive", oldVal, this.pumpActive);
    }

    // --- Alarm Status Access ---
    public synchronized boolean isAlarmTriggered() {
        return alarmTriggered;
    }

    public synchronized void setAlarmTriggered(boolean triggered) {
        boolean oldVal = this.alarmTriggered;
        this.alarmTriggered = triggered;
        support.firePropertyChange("alarmTriggered", oldVal, this.alarmTriggered);
    }

    /**
     * Atomically executes the pumping action:
     * 1. Checks reservoir.
     * 2. Decrements reservoir.
     * 3. Lowers glucose.
     * 4. Sets pump active status.
     * 
     * Mirrors the 'atomic' block in the Promela Pump process.
     */
    public synchronized void executePumpCycle() {
        if (insulinReservoir > 0) {
            boolean oldPump = pumpActive;
            int oldRes = insulinReservoir;
            int oldGluc = glucoseLevel;

            pumpActive = true;
            insulinReservoir--;
            glucoseLevel -= 15; // Insulin lowers sugar
            
            support.firePropertyChange("pumpActive", oldPump, true);
            support.firePropertyChange("reservoir", oldRes, insulinReservoir);
            support.firePropertyChange("glucose", oldGluc, glucoseLevel);

            System.out.printf("ACTION: Insulin Delivered. Remaining: %d, New Glucose: %d%n", insulinReservoir, glucoseLevel);
        } else {
            boolean oldAlarm = alarmTriggered;
            boolean oldPump = pumpActive;
            
            alarmTriggered = true;
            pumpActive = false; // Cannot pump if empty
            
            support.firePropertyChange("alarmTriggered", oldAlarm, true);
            support.firePropertyChange("pumpActive", oldPump, false);

            System.out.println("ALARM: Reservoir Empty!");
        }
    }
}
