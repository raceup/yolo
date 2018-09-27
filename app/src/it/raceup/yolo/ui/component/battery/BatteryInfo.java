package it.raceup.yolo.ui.component.battery;

import javax.swing.*;
import java.awt.*;

import static it.raceup.yolo.models.data.Base.DNF;
import static it.raceup.yolo.models.data.Base.getAsString;

public class BatteryInfo extends JPanel {
    private static String VOLTAGE = "Total voltage: " + DNF;
    private static String CURRENT = "Current: " + DNF;
    private final JLabel voltageLabel = new JLabel(VOLTAGE);
    private final JLabel currentLabel = new JLabel(CURRENT);

    public BatteryInfo() {
        setup();
    }

    private void setup() {
        setupLayout();
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(voltageLabel);
        add(Box.createRigidArea(new Dimension(100, 0)));
        add(currentLabel);
    }

    public void setVoltage(double value) {
        String text = getAsString(value) + "V";
        voltageLabel.setText(VOLTAGE + text);
    }

    public void setCurrent(double value) {
        String text = getAsString(value) + "A";
        voltageLabel.setText(CURRENT + text);
    }
}
