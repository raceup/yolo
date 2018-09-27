package it.raceup.yolo.ui.component.battery;

import javax.swing.*;

import static it.raceup.yolo.models.data.Base.getAsString;

public class BatteryInfo extends JPanel {
    private static String VOLTAGE = "Total voltage: ";
    private static String CURRENT = "Current: ";
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
