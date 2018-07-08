package it.raceup.yolo.models.car;

import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;

public class Car {
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
}