package it.raceup.yolo.ui.component.imu;

import it.raceup.yolo.ui.component.PolarPlane2D;

import javax.swing.*;
import java.awt.*;

public class AccelerationsPanel extends JPanel {
    private final PolarPlane2D accelerationPlane;

    public AccelerationsPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));  // vertically

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
