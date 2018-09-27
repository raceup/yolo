package it.raceup.yolo.ui.component.imu;

import javax.swing.*;

import static it.raceup.yolo.models.data.Base.DEGREES;
import static it.raceup.yolo.models.data.Base.getAsString;

public class YawPanel extends JPanel {
    private final JLabel valueLabel = new JLabel(DEGREES);  // setup label

    public YawPanel() {
        super();

        setup();
    }

    /**
     * Setups gui and panel
     */
    private void setup() {
        valueLabel.setAlignmentX(CENTER_ALIGNMENT);

        setupLayout();
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));  // vertically

        JLabel label = new JLabel("Yaw rate");
        label.setAlignmentX(CENTER_ALIGNMENT);

        add(label);
        add(valueLabel);

        // todo setPreferredSize(new Dimension(200, 80));
    }

    /**
     * Sets new value of yaw rate
     *
     * @param value yaw rate
     */
    public void setValue(double value) {
        valueLabel.setText(getAsString(value));
    }

}
