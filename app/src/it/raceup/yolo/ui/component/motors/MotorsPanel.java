package it.raceup.yolo.ui.component.motors;

import it.raceup.yolo.Data;
import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.car.Driver;
import it.raceup.yolo.models.car.Motor;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.ui.component.driver.DriverPanel;
import it.raceup.yolo.ui.window.MotorFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import static it.raceup.yolo.models.car.Motors.DEFAULT_MOTORS;
import static it.raceup.yolo.models.data.Raw.isBoolean;

public class MotorsPanel extends JPanel implements Observer {
    private final Image CAR_IMAGE;
    private final MotorInfo[] motorPanels = new MotorInfo[DEFAULT_MOTORS.length];
    private final MotorFrame[] motorFrameWindows = new MotorFrame[DEFAULT_MOTORS.length];
    private final Driver driver;
    private final JButton driverButton = new JButton("Driver");
    private final DriverPanel driverPanel = new DriverPanel();

    public MotorsPanel() {
        for (int i = 0; i < DEFAULT_MOTORS.length; i++) {
            motorPanels[i] = new MotorInfo(DEFAULT_MOTORS[i]);
            motorFrameWindows[i] = new MotorFrame(DEFAULT_MOTORS[i]);
        }
        CAR_IMAGE = new Data().getCarImage();
        driver = new Driver();
        setup();
    }

    private void setup() {
        setupLayout();
        setWindowsTogglers();
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(driverButton);

        driverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                driverPanel.open();
            }
        });
        JPanel up = new JPanel();
        up.setLayout(new BoxLayout(up, BoxLayout.X_AXIS));
        up.add(motorPanels[0]);
        up.add(Box.createRigidArea(new Dimension(126, 0)));
        up.add(motorPanels[1]);

        JPanel down = new JPanel();
        down.setLayout(new BoxLayout(down, BoxLayout.X_AXIS));
        down.add(motorPanels[2]);
        down.add(Box.createRigidArea(new Dimension(126, 0)));
        down.add(motorPanels[3]);

        add(up);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(down);
    }

    private void setWindowsTogglers() {
        for (int i = 0; i < motorPanels.length; i++) {
            motorPanels[i].viewButton.addActionListener(getCheckAction(i));
        }
    }

    private ActionListener getCheckAction(int motor) {
        return actionEvent -> {
            if (motorFrameWindows[motor].isVisible()) {
                motorFrameWindows[motor].close();
            } else {
                motorFrameWindows[motor].open();
            }
        };
    }

    public void update(Raw data) {
        update(data.getMotor(), data.getType(), data.getRaw());
    }

    public void update(int motor, Type type, Double data) {

        motorPanels[motor].update(type.toString(), data, isBoolean(type));
        motorFrameWindows[motor].update(type, data);
    }

    private void updateSuspension() {
        update(0, Type.FRONT_SUSPENSION_POTENTIOMETER, driver.getDriverData(Type.FRONT_SUSPENSION_POTENTIOMETER)[0]);
        update(1, Type.FRONT_SUSPENSION_POTENTIOMETER, driver.getDriverData(Type.FRONT_SUSPENSION_POTENTIOMETER)[0]);
        update(2, Type.REAR_SUSPENSION_POTENTIOMETER, driver.getDriverData(Type.REAR_SUSPENSION_POTENTIOMETER)[0]);
        update(3, Type.REAR_SUSPENSION_POTENTIOMETER, driver.getDriverData(Type.REAR_SUSPENSION_POTENTIOMETER)[0]);
    }

    private void update(it.raceup.yolo.models.car.Motor[] motors) {
        for (int i = 0; i < motors.length; i++) {
            for (Type type : Raw.ALL) {
                double value = motors[i].get(type);  // fetch from model
                update(i, type, value);  // updateWith view
            }
        }
        updateSuspension();
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            update((Motor[]) o);
        } catch (Exception e) {
            e.printStackTrace();
            new YoloException("cannot update motors", e, ExceptionType.VIEW)
                    .print();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // paint image
        int startXImage = motorPanels[0].getX() + motorPanels[0].getWidth() +
                10;
        int startYImage = 120;
        int imageWidth = CAR_IMAGE.getWidth(this);
        int imageHeight = CAR_IMAGE.getHeight(this);

        g.drawImage(CAR_IMAGE, startXImage, startYImage, imageWidth, imageHeight, this);
    }
}
