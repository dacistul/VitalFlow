package com.vitalflow;

import java.util.concurrent.BlockingQueue;

/**
 * Simulates the Controller process.
 * Receives sensor data and decides whether to activate the pump based on safety thresholds.
 */
public class Controller implements Runnable {
    private final BlockingQueue<Integer> sensorDataChannel;
    private final BlockingQueue<Boolean> pumpCmdChannel;

    public Controller(BlockingQueue<Integer> sensorDataChannel, BlockingQueue<Boolean> pumpCmdChannel) {
        this.sensorDataChannel = sensorDataChannel;
        this.pumpCmdChannel = pumpCmdChannel;
    }

    /**
     * Pure function determining pump action based on glucose level.
     * Returns: true (PUMP), false (STOP/IDLE)
     */
    public static boolean decidePumpAction(int currentLevel) {
        if (currentLevel < 70) {
            // [SAFETY] Low Sugar
            return false;
        } else if (currentLevel >= 200) {
            // High Sugar
            return true;
        } else {
            // Normal Range
            return false;
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Blocks until sensor data is available
                // Promela: sensor_data ? current_level
                int currentLevel = sensorDataChannel.take();

                boolean shouldPump = decidePumpAction(currentLevel);

                if (shouldPump) {
                     System.out.println("CONTROLLER: High Sugar (" + currentLevel + "). Command: PUMP");
                } else {
                    if (currentLevel < 70) {
                        System.out.println("CONTROLLER: [SAFETY] Low Sugar (" + currentLevel + "). Command: STOP");
                    } else {
                        System.out.println("CONTROLLER: Normal Range (" + currentLevel + "). Command: IDLE");
                    }
                }

                // Send command to Pump process
                // Promela: pump_cmd ! true/false
                pumpCmdChannel.put(shouldPump);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
