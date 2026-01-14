package com.vitalflow;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Simulates the Sensor process.
 * Non-deterministically updates the glucose level and sends readings to the Controller.
 */
public class Sensor implements Runnable {
    private final SystemState state;
    private final BlockingQueue<Integer> sensorDataChannel;
    private final Random random = new Random();

    public Sensor(SystemState state, BlockingQueue<Integer> sensorDataChannel) {
        this.state = state;
        this.sensorDataChannel = sensorDataChannel;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Read current state securely
                int currentLevel = state.getGlucoseLevel();
                
                // Promela Logic Replication:
                // :: glucose_level > 40 -> glucose_level = glucose_level - 10;
                // :: glucose_level < 300 -> glucose_level = glucose_level + 20;
                // :: true -> skip;
                
                int choice = random.nextInt(3); // 0 = drop, 1 = rise, 2 = steady
                
                if (choice == 0 && currentLevel > 40) {
                    state.updateGlucose(-10);
                } else if (choice == 1 && currentLevel < 300) {
                    state.updateGlucose(20);
                }
                // choice 2 is 'skip' (steady)

                // Get the updated level to send
                int newLevel = state.getGlucoseLevel();
                
                // Send data to controller (blocks if queue is full, though unlikely here)
                sensorDataChannel.put(newLevel);
                
                System.out.println("SENSOR: Current Glucose: " + newLevel + " mg/dL");

                // Slow down strictly for visualization purposes
                Thread.sleep(1000); 
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
