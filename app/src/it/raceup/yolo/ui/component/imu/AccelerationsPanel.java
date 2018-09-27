package it.raceup.yolo.ui.component.imu;

import javax.swing.*;
import java.awt.*;

public class AccelerationsPanel extends JPanel {
    private final PolarPlane2D accelerationPlane;

    AccelerationPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));  // add items vertically

        accelerationPlane = new PolarPlane2D(new Dimension(250, 245));
        accelerationPlane.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(accelerationPlane);

    }

    /**
     * Sets new value of acceleration to given axis
     *
     * @param value acceleration
     * @param axis  acceleration axis
     */
    public void setValue(double value, int axis) {
        accelerationPlane.setValue(value, axis);
    }
}
