package com.vitalflow;

import java.util.concurrent.BlockingQueue;

/**
 * Simulates the Pump process.
 * Receives commands and executes the physical insulin delivery.
 */
public class Pump implements Runnable {
    private final SystemState state;
    private final BlockingQueue<Boolean> pumpCmdChannel;

    public Pump(SystemState state, BlockingQueue<Boolean> pumpCmdChannel) {
        this.state = state;
        this.pumpCmdChannel = pumpCmdChannel;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Blocks until command is received
                // Promela: pump_cmd ? trigger
                boolean trigger = pumpCmdChannel.take();

                if (trigger) {
                    // Logic delegated to SystemState to ensure atomicity of 
                    // reservoir check, decrement, and glucose update.
                    state.executePumpCycle();
                } else {
                    state.setPumpActive(false);
                    // Quietly go idle
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
