package it.raceup.yolo.ui.component.battery;

import javax.swing.*;
import java.awt.*;

import static it.raceup.yolo.models.data.Base.getAsString;

public class BMSPanel extends JPanel {
    private JButton button;
    private JLabel voltageLabel = new JLabel("volt");
    private JLabel temperatureLabel = new JLabel("temp");

    public BMSPanel(int bms) {
        button = new JButton(Integer.toString(bms));

        setup();
    }

    private void setup() {
        setupLayout();
        setupButton();
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(button);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(voltageLabel);
        add(temperatureLabel);
    }

    private void setupButton() {
        // todo
    }

    public void setVoltage(double value) {
        String text = getAsString(value) + "V";
        voltageLabel.setText(text);
    }

    public void setTemperature(double value) {
        String text = getAsString(value) + "C";
        temperatureLabel.setText(text);
    }
}
