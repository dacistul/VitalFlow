package com.vitalflow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class VitalFlowGUI extends JFrame implements PropertyChangeListener {

    private JLabel lblGlucose;
    private JLabel lblReservoir;
    private JLabel lblPumpStatus;
    private JLabel lblAlarmStatus;
    private JTextArea logArea;
    private SystemState state;

    private Thread sensorThread;
    private Thread controllerThread;
    private Thread pumpThread;

    public VitalFlowGUI() {
        setTitle("VitalFlow - Insulin Pump Monitor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        initComponents();
        redirectSystemStreams();
        startSimulation();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);

        JPanel dashboard = new JPanel(new GridLayout(1, 4, 10, 0));
        dashboard.setPreferredSize(new Dimension(800, 100));

        lblGlucose = createStatusCard(dashboard, "Glucose Level", "100 mg/dL", new Color(230, 240, 255));
        lblReservoir = createStatusCard(dashboard, "Reservoir", "10 Units", new Color(230, 255, 230));
        lblPumpStatus = createStatusCard(dashboard, "Pump Status", "IDLE", new Color(255, 250, 230));
        lblAlarmStatus = createStatusCard(dashboard, "System Alarm", "OK", new Color(255, 230, 230));

        mainPanel.add(dashboard, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        logArea.setBackground(new Color(30, 30, 30));
        logArea.setForeground(new Color(200, 200, 200));
        
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("System Event Log"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnExit = new JButton("Exit Simulation");
        btnExit.addActionListener(e -> System.exit(0));
        controls.add(btnExit);
        mainPanel.add(controls, BorderLayout.SOUTH);
    }

    private JLabel createStatusCard(JPanel parent, String title, String initialValue, Color bg) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        card.setBackground(bg);
        
        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTitle.setBorder(new EmptyBorder(5, 0, 5, 0));
        
        JLabel lblValue = new JLabel(initialValue, SwingConstants.CENTER);
        lblValue.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        
        parent.add(card);
        return lblValue;
    }

    private void startSimulation() {
        System.out.println("Initializing VitalFlow System Components...");
        
        state = new SystemState();
        state.addPropertyChangeListener(this);

        BlockingQueue<Integer> sensorDataChannel = new ArrayBlockingQueue<>(1);
        BlockingQueue<Boolean> pumpCmdChannel = new ArrayBlockingQueue<>(1);

        sensorThread = new Thread(new Sensor(state, sensorDataChannel), "Sensor");
        controllerThread = new Thread(new Controller(sensorDataChannel, pumpCmdChannel), "Controller");
        pumpThread = new Thread(new Pump(state, pumpCmdChannel), "Pump");

        sensorThread.start();
        controllerThread.start();
        pumpThread.start();
        
        System.out.println("System Running. Monitoring threads started.");
    }

    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) {
                updateLog(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) {
                updateLog(new String(b, off, len));
            }
        };
        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }

    private void updateLog(String text) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(text);
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SwingUtilities.invokeLater(() -> {
            switch (evt.getPropertyName()) {
                case "glucose":
                    int gVal = (int) evt.getNewValue();
                    lblGlucose.setText(gVal + " mg/dL");
                    if (gVal < 70) lblGlucose.setForeground(Color.RED);
                    else if (gVal > 200) lblGlucose.setForeground(Color.ORANGE);
                    else lblGlucose.setForeground(Color.BLACK);
                    break;
                case "reservoir":
                    lblReservoir.setText(evt.getNewValue() + " Units");
                    break;
                case "pumpActive":
                    boolean active = (boolean) evt.getNewValue();
                    lblPumpStatus.setText(active ? "PUMPING" : "IDLE");
                    lblPumpStatus.setForeground(active ? new Color(0, 150, 0) : Color.BLACK);
                    break;
                case "alarmTriggered":
                    boolean alarm = (boolean) evt.getNewValue();
                    lblAlarmStatus.setText(alarm ? "CRITICAL!" : "OK");
                    lblAlarmStatus.setForeground(alarm ? Color.RED : new Color(0, 100, 0));
                    break;
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VitalFlowGUI().setVisible(true);
        });
    }
}
