package it.raceup.yolo.ui.component.imu;

import it.raceup.yolo.ui.window.DriverFrame;

import javax.swing.*;
import java.awt.*;

public class ImuPanel extends JPanel {
    private final AccelerationsPanel accelerationsPanel = new
            AccelerationsPanel();
    //private final YawPanel yawPanel = new YawPanel();
    private DriverFrame driverFrame = new DriverFrame();

    public ImuPanel() {
        setup();
    }

    private void setup() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(accelerationsPanel);
        add(Box.createRigidArea(new Dimension(10, 0)));
    }

    private void openDriverPanel() {
        if (driverFrame.isVisible()) {
            driverFrame.close();
        } else {
            driverFrame.open();
        }
    }
}
