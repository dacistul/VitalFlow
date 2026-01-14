package com.vitalflow;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

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
                int currentLevel = state.getGlucoseLevel();
                
                int choice = random.nextInt(3); // 0 = drop, 1 = rise, 2 = steady
                
                if (choice == 0 && currentLevel > 40) {
                    state.updateGlucose(-10);
                } else if (choice == 1 && currentLevel < 300) {
                    state.updateGlucose(20);
                }

                int newLevel = state.getGlucoseLevel();
                
                sensorDataChannel.put(newLevel);
                
                System.out.println("SENSOR: Current Glucose: " + newLevel + " mg/dL");

                Thread.sleep(1000); 
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
