package it.raceup.yolo.models.car;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;

import java.util.Observable;
import java.util.Observer;

public class Car implements Observer {
    public static final String[] MOTOR_TAGS = new String[]{
            "Front Left", "Front Right", "Rear Left", "Rear Right"
    };  // 4 motors in car
    public Motor[] motors = new Motor[MOTOR_TAGS.length];

    public Car() {
        for (int i = 0; i < motors.length; i++) {
            motors[i] = new Motor(MOTOR_TAGS[i]);  // initialize all motors
        }
    }

    public void update(Raw data) {
        int motor = data.getMotor();
        motors[motor].update(data);
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
            this.update((Raw) o);
        } catch (Exception e) {
            new YoloException("cannot update car", e, ExceptionType.KVASER)
                    .print();
        }
    }
}
