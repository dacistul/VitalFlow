package com.vitalflow;

import java.util.concurrent.BlockingQueue;

public class Controller implements Runnable {
    private final BlockingQueue<Integer> sensorDataChannel;
    private final BlockingQueue<Boolean> pumpCmdChannel;

    public Controller(BlockingQueue<Integer> sensorDataChannel, BlockingQueue<Boolean> pumpCmdChannel) {
        this.sensorDataChannel = sensorDataChannel;
        this.pumpCmdChannel = pumpCmdChannel;
    }

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

                pumpCmdChannel.put(shouldPump);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
