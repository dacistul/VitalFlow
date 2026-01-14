package com.vitalflow;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Main Entry Point.
 * Sets up the shared state, channels, and threads (Sensor, Controller, Pump).
 */
public class VitalFlowApp {
    public static void main(String[] args) {
        System.out.println("===============================================");
        System.out.println("   VitalFlow - Insulin Pump Java Simulation    ");
        System.out.println("===============================================");
        System.out.println("Based on verified Promela model: vitalflow.pml");
        System.out.println("Press Ctrl+C to stop.");
        System.out.println("-----------------------------------------------");

        // 1. Initialize Shared System State
        SystemState state = new SystemState();

        // 2. Initialize Communication Channels
        // Using capacity 1 to simulate tight coupling/message passing
        BlockingQueue<Integer> sensorDataChannel = new ArrayBlockingQueue<>(1);
        BlockingQueue<Boolean> pumpCmdChannel = new ArrayBlockingQueue<>(1);

        // 3. Instantiate Processes
        Thread sensorThread = new Thread(new Sensor(state, sensorDataChannel), "Sensor");
        Thread controllerThread = new Thread(new Controller(sensorDataChannel, pumpCmdChannel), "Controller");
        Thread pumpThread = new Thread(new Pump(state, pumpCmdChannel), "Pump");

        // 4. Start Simulation
        sensorThread.start();
        controllerThread.start();
        pumpThread.start();
        
        // Keep main thread alive
        try {
            sensorThread.join();
            controllerThread.join();
            pumpThread.join();
        } catch (InterruptedException e) {
            System.out.println("Simulation interrupted.");
        }
    }
}
