package com.vitalflow;

public class SystemState {
    private int glucoseLevel = 100;
    private int insulinReservoir = 10;
    private boolean pumpActive = false;
    private boolean alarmTriggered = false;
    
    private final java.beans.PropertyChangeSupport support = new java.beans.PropertyChangeSupport(this);

    public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public synchronized int getGlucoseLevel() {
        return glucoseLevel;
    }

    public synchronized void updateGlucose(int delta) {
        int oldVal = this.glucoseLevel;
        this.glucoseLevel += delta;
        support.firePropertyChange("glucose", oldVal, this.glucoseLevel);
        // System.out.println("DEBUG: Glucose updated to " + glucoseLevel);
    }

    public synchronized int getInsulinReservoir() {
        return insulinReservoir;
    }

    public synchronized boolean isPumpActive() {
        return pumpActive;
    }

    public synchronized void setPumpActive(boolean active) {
        boolean oldVal = this.pumpActive;
        this.pumpActive = active;
        support.firePropertyChange("pumpActive", oldVal, this.pumpActive);
    }

    public synchronized boolean isAlarmTriggered() {
        return alarmTriggered;
    }

    public synchronized void setAlarmTriggered(boolean triggered) {
        boolean oldVal = this.alarmTriggered;
        this.alarmTriggered = triggered;
        support.firePropertyChange("alarmTriggered", oldVal, this.alarmTriggered);
    }

    public synchronized void executePumpCycle() {
        if (insulinReservoir > 0) {
            boolean oldPump = pumpActive;
            int oldRes = insulinReservoir;
            int oldGluc = glucoseLevel;

            pumpActive = true;
            insulinReservoir--;
            glucoseLevel -= 15;
            
            support.firePropertyChange("pumpActive", oldPump, true);
            support.firePropertyChange("reservoir", oldRes, insulinReservoir);
            support.firePropertyChange("glucose", oldGluc, glucoseLevel);

            System.out.printf("ACTION: Insulin Delivered. Remaining: %d, New Glucose: %d%n", insulinReservoir, glucoseLevel);
        } else {
            boolean oldAlarm = alarmTriggered;
            boolean oldPump = pumpActive;
            
            alarmTriggered = true;
            pumpActive = false;
            
            support.firePropertyChange("alarmTriggered", oldAlarm, true);
            support.firePropertyChange("pumpActive", oldPump, false);

            System.out.println("ALARM: Reservoir Empty!");
        }
    }
}
