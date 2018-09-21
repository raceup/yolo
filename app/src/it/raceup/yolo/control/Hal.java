package it.raceup.yolo.control;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.loggers.ShellLogger;
import it.raceup.yolo.models.car.Car;
import it.raceup.yolo.models.data.CanMessage;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.models.kvaser.Kvaser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;

/**
 * Handles business logic of telemetry. Opens connection with Kvaser and
 * remains listening for incoming data. When there is new data, updates car data.
 */
public class Hal extends ShellLogger {
    private final Car car;
    private final Kvaser kvaser;

    public Hal(Car car, Kvaser kvaser) {
        super("HAL");

        this.car = car;
        this.kvaser = kvaser;
        addObserverToKvaser(car);
    }

    public void addObserverToKvaser(Observer observer) {
        kvaser.addObserver(observer);
    }

    public void addObserverToCar(Observer observer) {
        car.addObserver(observer);
    }

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
        return car.get(type, motor);
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
        return car.numberOfMotors();
    }
}
