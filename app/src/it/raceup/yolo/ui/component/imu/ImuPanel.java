package it.raceup.yolo.ui.component.imu;

import javax.swing.*;
import java.awt.*;

public class ImuPanel extends JPanel {
    private final AccelerationsPanel accelerationsPanel = new
            AccelerationsPanel();
    private final YawPanel yawPanel = new YawPanel();

    public ImuPanel() {
        setup();
    }

    private void setup() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(Box.createRigidArea(new Dimension(10, 0)));
        add(accelerationsPanel);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(yawPanel);
    }

}
