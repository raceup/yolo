package it.raceup.yolo.ui.component.imu;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.car.Imu;
import it.raceup.yolo.ui.component.PolarPlane2D;
import it.raceup.yolo.ui.utils.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static it.raceup.yolo.models.data.Base.getAsString;
import static it.raceup.yolo.models.data.Type.ROLL_PITCH_YAW;

public class AccelerationsPanel extends JPanel implements Observer {
    private static final String X_LABEL = "X: ";
    private static final String Y_LABEL = "Y: ";
    private static final String TOT_LABEL = "Magnitude: ";
    private final PolarPlane2D plane;
    private final JLabel xAccelerationLabel = new JLabel(X_LABEL);
    private final JLabel yAccelerationLabel = new JLabel(Y_LABEL);
    private final JLabel totAccelerationLabel = new JLabel(TOT_LABEL);
    private final String[] rpy = {"Roll", "Pitch", "Yaw"};
    private double currentX = 0.0;
    private double currentY = 0.0;
    private ChartPanel rpychart;
    private Imu imu = new Imu();

    public AccelerationsPanel() {
        super();

        plane = new PolarPlane2D(new Dimension(250, 250));
        setup();
    }

    public static double getTotalMagnitude(double x, double y) {
        double xSquared = x * x;
        double ySquared = y * y;
        double squared = xSquared + ySquared;
        return Math.sqrt(squared);
    }

    private void setup() {
        plane.setAlignmentX(Component.CENTER_ALIGNMENT);
        rpychart = new ChartPanel(rpy, "Throttle Brake");
        setupLayout();

    }

    private void setupLayout() {
        add(Box.createRigidArea(new Dimension(0, 10)));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));  // vertically

        add(getTopPanel());
        add(plane);
        add(rpychart);
    }

    private void showrpy() {
        if (imu.getImuData(ROLL_PITCH_YAW) != null) {
            Timer updater = new Timer(100, e -> {
                rpychart.updateSeriesOrFail(0, imu.getImuData(ROLL_PITCH_YAW)[0]);
                rpychart.updateSeriesOrFail(1, imu.getImuData(ROLL_PITCH_YAW)[1]);
                rpychart.updateSeriesOrFail(2, imu.getImuData(ROLL_PITCH_YAW)[2]);
            });
            updater.start();
        }

    }

    private JPanel getTopPanel() {
        JPanel panel = new JPanel();

        // horizontally
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(xAccelerationLabel);
        panel.add(Box.createRigidArea(new Dimension(50, 0)));

        panel.add(yAccelerationLabel);
        panel.add(Box.createRigidArea(new Dimension(50, 0)));

        panel.add(totAccelerationLabel);
        panel.add(Box.createRigidArea(new Dimension(50, 0)));

        return panel;
    }

    public void setValue(double value, int axis) {
        plane.setValue(value, axis);

        if (axis == 0) {
            currentX = value;
            xAccelerationLabel.setText(X_LABEL + getAsString(value));
        } else {
            currentY = value;
            yAccelerationLabel.setText(Y_LABEL + getAsString(value));
        }

        double totAcceleration = getTotalMagnitude(currentX, currentY);
        totAccelerationLabel.setText(getAsString(totAcceleration));
    }

    public void setXYValue(double x, double y) {
        plane.setValue(x, 0);
        plane.setValue(y, 1);
    }

    public void update(Imu imu) {
        //setXYValue(imu.getImuData()[0], imu.getImuData()[1]);
    }

    private void update(Imu[] imu) {
        for (Imu value : imu) {
            update(value);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            update((Imu[]) o);
        } catch (Exception e) {
            new YoloException("cannot update imu acceleration", e, ExceptionType.VIEW)
                    .print();
        }
    }
}
