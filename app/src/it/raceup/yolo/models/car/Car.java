package it.raceup.yolo.models.car;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.data.CanMessage;
import it.raceup.yolo.models.data.Parser;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.models.kvaser.message.FromKvaserMessage;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Car extends Observable implements Observer {
    public static final String[] DEFAULT_MOTORS = new String[]{
            "Front Left", "Front Right", "Rear Left", "Rear Right"
    };  // 4 motors in car
    private final Motor[] motors;

    public Car() {
        this(DEFAULT_MOTORS);
    }

    public Car(String[] motorLabels) {
        motors = new Motor[motorLabels.length];
        for (int i = 0; i < motors.length; i++) {
            motors[i] = new Motor(motorLabels[i]);  // initialize all motors
        }
    }

    private void update(Raw data) {
        int motor = data.getMotor();
        motors[motor].update(data);
    }

    private void update(ArrayList<CanMessage> data) {
        for (CanMessage message : data) {
            Raw[] packets = new Parser(message).getParsedData();
            for (Raw packet : packets) {
                update(packet);
            }
        }
    }

    public double get(Type type, int motor) {
        return motors[motor].get(type);
    }

    public int numberOfMotors() {
        return motors.length;
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            FromKvaserMessage message = new FromKvaserMessage(o);
            ArrayList<CanMessage> messages = message.getAsCanMessages();
            if (messages != null) {
                update(messages);
                triggerObservers();
            }
        } catch (Exception e) {
            new YoloException("cannot update car", e, ExceptionType.KVASER)
                    .print();
        }
    }

    private void triggerObservers() {
        setChanged();
        notifyObservers(motors);
    }
}
