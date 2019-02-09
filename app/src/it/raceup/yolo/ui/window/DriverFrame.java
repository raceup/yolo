package it.raceup.yolo.ui.window;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.car.Driver;
import it.raceup.yolo.ui.component.driver.SteeringPanel;
import it.raceup.yolo.ui.component.driver.ThrottleBrakePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static it.raceup.yolo.utils.Os.setNativeLookAndFeelOrFail;

public class DriverFrame extends JFrame implements Observer {
    private final SteeringPanel steeringPanel = new SteeringPanel();
    private final ThrottleBrakePanel throttleBrakePanel = new ThrottleBrakePanel();
    public DriverFrame() {
        super("Driver Frame");
        setup();
    }

    private void setup() {
        setupLayout();
    }

    private void setupLayout() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(0, 10)));
        //add(steeringPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(throttleBrakePanel);
    }

    public void open() {
        try {
            pack();
            setSize(500, 900);
            setLocation(925, 350);
            setResizable(false);
            setNativeLookAndFeelOrFail();
            // disable exit button
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            setVisible(true);
        } catch (Exception e) {
            new YoloException(
                    "cannot open Driver viewer",
                    e,
                    ExceptionType.VIEW
            ).print();
        }
    }

    public void close() {
        setVisible(false);
    }


    public void setThrottle(double value) {
        throttleBrakePanel.setThrottle(value);
    }

    public void setBrake(double value) {
        throttleBrakePanel.setBrake(value);
    }

    public void setSteering(double value) {
        steeringPanel.setValue(value);
    }

    private void update(Driver driver){
        /*
        switch (driver.getDriverType()) {
            case STEERINGWHEEL: setSteering(driver.getDriverData()[0]);
            case THROTTLE: setThrottle(driver.getDriverData()[0]);
            case BRAKE: setBrake(driver.getDriverData()[1]);
        }
        */
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            update((Driver) o);
        } catch (Exception e) {
            e.printStackTrace();
            new YoloException("cannot updateWith CAR", e, ExceptionType.IMU)
                    .print();
        }
    }
}
