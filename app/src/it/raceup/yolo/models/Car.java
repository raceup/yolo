package it.raceup.yolo.models;

import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;

public class Car {
    public static String[] motorTags = new String[]{
            "FL", "FR", "RL", "RR"
    };  // 4 motors in car
    public Motor[] motors = new Motor[motorTags.length];

    public Car() {
        for (int i = 0; i < motors.length; i++) {
            motors[i] = new Motor(motorTags[i]);  // initialize all motors
        }
    }

    public void update(Raw data) {
        int motor = data.getMotor();
        motors[motor].update(data);
    }

    public double get(Type type, int motor) {
        return motors[motor].get(type);
    }
}
