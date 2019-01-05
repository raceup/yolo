package it.raceup.yolo.control;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.loggers.ShellLogger;
import it.raceup.yolo.models.car.Motors;
import it.raceup.yolo.models.car.Imu;
import it.raceup.yolo.models.data.CanMessage;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.models.kvaser.Kvaser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;

/**
 * Handles business logic of telemetry. Opens connection with Kvaser and
 * remains listening for incoming data. When there is new data, updates motors data.
 */
public class Hal extends ShellLogger {
    private final Motors motors;
    private final Kvaser kvaser;
    private final Imu imu;

    public Hal(Motors motors, Kvaser kvaser, Imu imu) {
        super("HAL");

        this.motors = motors;
        this.kvaser = kvaser;
        this.imu = imu;
        addObserverToKvaser(motors);

        //test code
        addObserverToKvaser(imu);
    }

    public void addObserverToKvaser(Observer observer) {
        kvaser.addObserver(observer);
    }

    public void addObserverToMotors(Observer observer) {
        motors.addObserver(observer);
    }

    public void addObserverToImu(Observer observer) {imu.addObserver(observer);}

    public void setup(String canBitrate) throws YoloException {
        if (!kvaser.setup(canBitrate)) {
            throw new YoloException("cannot start Kvaser connection",
                    ExceptionType.KVASER);
        }
    }

    public void start() {
        Thread thread = new Thread(kvaser);
        thread.start();
    }

    public void close() {
        log("closing");
        kvaser.close();
    }

    public double get(Type type, int motor) {
        return motors.get(type, motor);
    }

    public ArrayList<CanMessage> getRaw() {
        return kvaser.getData();
    }

    public HashMap<Type, Double> getInfo(int motor) {
        HashMap<Type, Double> info = new HashMap<>();
        for (Type type : Type.values()) {
            Double value = null;
            try {
                value = get(type, motor);
            } catch (Exception e) {
                log(e);
            }
            info.put(type, value);
        }
        return info;
    }

    public int numberOfMotors() {
        return motors.numberOfMotors();
    }
}
