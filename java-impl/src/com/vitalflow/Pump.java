package com.vitalflow;

import java.util.concurrent.BlockingQueue;

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
                boolean trigger = pumpCmdChannel.take();

                if (trigger) {
                    state.executePumpCycle();
                } else {
                    state.setPumpActive(false);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
