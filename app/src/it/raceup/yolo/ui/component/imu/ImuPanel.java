package it.raceup.yolo.ui.component.imu;

import it.raceup.yolo.models.car.Imu;

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
    public void updateAccelerationPanel(Imu imu){
        accelerationsPanel.update(imu);
    }
    public void updateYawPanel(Imu imu){
        yawPanel.update(imu);
    }

}
