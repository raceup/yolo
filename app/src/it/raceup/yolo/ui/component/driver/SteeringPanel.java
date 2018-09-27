package it.raceup.yolo.ui.component.driver;

import it.raceup.yolo.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import static it.raceup.yolo.models.data.Base.getAsString;

/**
 * Panel with info about steering wheel rotation angle
 */
public class SteeringPanel extends JPanel {
    private final Image STEERING_WHEEL_IMAGE;
    private final JLabel valueLabel;
    private final String DEGREES = " °";
    private double value = 0.0;

    public SteeringPanel() {
        STEERING_WHEEL_IMAGE = new Data().getSteeringWheelImage();
        valueLabel = new JLabel("");

        setup();
    }

    /**
     * Sets value and updates gui
     *
     * @param value new value to set
     */
    public void setRotation(double value) {
        this.value = value;
        valueLabel.setText(getAsString(value) + DEGREES);  // update labels

        repaint();  // update steering wheel image
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int startXImage = (int) (this.getWidth() * 0.37);  // compute position
        int startYImage = (int) (this.getHeight() * 0.75);
        double ratioImageSize =
                this.getWidth() * 0.75 / ((double) STEERING_WHEEL_IMAGE.getWidth(null));  // compute size ratio

        AffineTransform at = new AffineTransform();
        at.scale(ratioImageSize, ratioImageSize);  // scale
        at.rotate(
                Math.toRadians(value),
                startXImage + STEERING_WHEEL_IMAGE.getWidth(null) * 0.5,
                startYImage + STEERING_WHEEL_IMAGE.getHeight(null) * 0.5
        );  // rotate
        at.translate(startXImage, startYImage);  // move back
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(STEERING_WHEEL_IMAGE, at, null);  // draw the image
    }

    private void setup() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));  // vertical
        add(Box.createRigidArea(new Dimension(0, 250)));

        valueLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(valueLabel);

        repaint();  // force paint image
    }
}
