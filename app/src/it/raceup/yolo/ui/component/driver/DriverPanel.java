package it.raceup.yolo.ui.component.driver;

import javax.swing.*;
import java.awt.*;

public class DriverPanel extends JPanel {
    private final SteeringPanel steeringPanel = new SteeringPanel();
    private final ThrottleBrakePanel throttleBrakePanel = new
            ThrottleBrakePanel();

    public DriverPanel() {
        setup();
    }

    private void setup() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(steeringPanel);

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(throttleBrakePanel);
    }

    public void setThrottle(double value) {
        throttleBrakePanel.setThrottle(value);
    }

    public void setBrake(double value) {
        throttleBrakePanel.setBrake(value);
    }

    public void setSteering(double value) {
        steeringPanel.setValue(value);
    }
}
