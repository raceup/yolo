package it.raceup.yolo.ui.component.motors;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.ui.window.MotorFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import static it.raceup.yolo.models.car.Motors.DEFAULT_MOTORS;

public class MotorsPanel extends JPanel implements Observer {
    private final MotorInfo[] motorPanels = new MotorInfo[DEFAULT_MOTORS.length];
    private final MotorFrame[] motorFrameWindows = new MotorFrame[DEFAULT_MOTORS.length];

    public MotorsPanel() {
        for (int i = 0; i < DEFAULT_MOTORS.length; i++) {
            motorPanels[i] = new MotorInfo(DEFAULT_MOTORS[i]);
            motorFrameWindows[i] = new MotorFrame(DEFAULT_MOTORS[i]);
        }

        setup();
    }

    private void setup() {
        setupLayout();
        setWindowsTogglers();
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel up = new JPanel();
        up.setLayout(new BoxLayout(up, BoxLayout.X_AXIS));
        up.add(motorPanels[0]);
        up.add(Box.createRigidArea(new Dimension(10, 0)));
        up.add(motorPanels[1]);

        JPanel down = new JPanel();
        down.setLayout(new BoxLayout(down, BoxLayout.X_AXIS));
        down.add(motorPanels[2]);
        down.add(Box.createRigidArea(new Dimension(10, 0)));
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

    public void update(int motor, it.raceup.yolo.models.data.Type type,
                       Double data) {
        try {
            motorPanels[motor].update(type, data);
        } catch (Exception e) {
            System.err.println("updateWith(Raw data): CANNOT UPDATE MOTOR PANEL");
        }

        try {
            motorFrameWindows[motor].update(type, data);
        } catch (Exception e) {
            System.err.println("updateWith(Raw data): CANNOT UPDATE MOTOR WINDOW");
        }
    }

    private void update(it.raceup.yolo.models.car.Motor[] motors) {
        for (int i = 0; i < motors.length; i++) {
            for (Type type : Raw.ALL) {
                double value = motors[i].get(type);  // fetch from model
                update(i, type, value);  // updateWith view
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            this.update((it.raceup.yolo.models.car.Motor[]) o);
        } catch (Exception e) {
            new YoloException("cannot updateWith car", e, ExceptionType.VIEW)
                    .print();
        }
    }
}