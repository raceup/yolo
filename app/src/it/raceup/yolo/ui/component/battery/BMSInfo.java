package it.raceup.yolo.ui.component.battery;

import javax.swing.*;
import java.awt.*;

public class BMSInfo extends JPanel {
    private final int BMS_PER_SEGMENT = 3;
    private final int N_SEGMENTS = 8;
    private final BMSPanel[] bms = new BMSPanel[BMS_PER_SEGMENT * N_SEGMENTS];

    public BMSInfo() {
        setup();
    }

    private void setup() {
        for (int i = 0; i < bms.length; i++) {  // create BMS
            bms[i] = new BMSPanel(i + 1);
        }

        setupLayout();
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));  // horizontal

        add(Box.createRigidArea(new Dimension(10, 0)));

        for (int i = 0; i < N_SEGMENTS; i++) {
            JPanel segmentPanel = getSegmentPanel(i);
            add(segmentPanel);
            add(Box.createRigidArea(new Dimension(10, 0)));
        }
    }

    private JPanel getSegmentPanel(int segment) {
        JPanel segmentPanel = new JPanel();
        segmentPanel.setLayout(new BoxLayout(segmentPanel, BoxLayout.Y_AXIS));

        for (int i = segment * BMS_PER_SEGMENT; i < (segment + 1) * BMS_PER_SEGMENT;
             i++) {
            segmentPanel.add(bms[i]);
            add(Box.createRigidArea(new Dimension(0, 10)));
        }

        return segmentPanel;
    }

    public void setVoltage(int i, double value) {
        bms[i].setVoltage(value);
    }

    public void setTemperature(int i, double value) {
        bms[i].setTemperature(value);
    }
}
