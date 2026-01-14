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

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Blocks until sensor data is available
                // Promela: sensor_data ? current_level
                int currentLevel = sensorDataChannel.take();

                boolean shouldPump = false;

                // Promela Logic:
                // :: (current_level < 70) -> pump_cmd ! false;
                // :: (current_level >= 200) -> pump_cmd ! true;
                // :: else -> pump_cmd ! false;
                
                if (currentLevel < 70) {
                    shouldPump = false;
                    System.out.println("CONTROLLER: [SAFETY] Low Sugar (" + currentLevel + "). Command: STOP");
                } else if (currentLevel >= 200) {
                    shouldPump = true;
                    System.out.println("CONTROLLER: High Sugar (" + currentLevel + "). Command: PUMP");
                } else {
                    shouldPump = false;
                    System.out.println("CONTROLLER: Normal Range (" + currentLevel + "). Command: IDLE");
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
