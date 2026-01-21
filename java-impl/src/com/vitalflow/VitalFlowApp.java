package com.vitalflow;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class VitalFlowApp {
    public static void main(String[] args) {
        System.out.println("===============================================");
        System.out.println("   VitalFlow - Insulin Pump Java Simulation    ");
        System.out.println("===============================================");
        System.out.println("Press Ctrl+C to stop.");
        System.out.println("-----------------------------------------------");

        SystemState state = new SystemState();

        BlockingQueue<Integer> sensorDataChannel = new ArrayBlockingQueue<>(1);
        BlockingQueue<Boolean> pumpCmdChannel = new ArrayBlockingQueue<>(1);

        Thread sensorThread = new Thread(new Sensor(state, sensorDataChannel), "Sensor");
        Thread controllerThread = new Thread(new Controller(sensorDataChannel, pumpCmdChannel), "Controller");
        Thread pumpThread = new Thread(new Pump(state, pumpCmdChannel), "Pump");

        sensorThread.start();
        controllerThread.start();
        pumpThread.start();
        
        try {
            sensorThread.join();
            controllerThread.join();
            pumpThread.join();
        } catch (InterruptedException e) {
            System.out.println("Simulation interrupted.");
        }
    }
}
