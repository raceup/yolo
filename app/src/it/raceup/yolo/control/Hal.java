package it.raceup.yolo.control;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.car.Car;
import it.raceup.yolo.models.data.CanMessage;
import it.raceup.yolo.models.data.Parser;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.models.kvaser.Kvaser;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static core.Canlib.canBITRATE_1M;

/**
 * Handles business logic of telemetry.
 * Opens connection with Kvaser and remains listening for incoming data.
 * When there is new data, updates car data.
 */
public class Hal {
    private Car car;
    private Kvaser kvaser;

    public Hal(Car car, Kvaser kvaser) {
        this.car = car;
        this.kvaser = kvaser;
    }

    public void startConnection() throws YoloException {
        if (kvaser.setup(canBITRATE_1M)) {
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    CanMessage[] messages = kvaser.read();
                    update(messages);
                }
            }, 0, 100);
        } else {
            throw new YoloException("Cannot start Kvaser connection",
                    ExceptionType.KVASER);
        }
    }

    // todo use
    public void close() {
        kvaser.close();
    }

    private void update(Raw data) {
        car.update(data);
    }

    private void update(Raw[] data) {
        for (Raw raw : data) {
            update(raw);
        }
    }

    private void update(CanMessage[] messages) {
        for (CanMessage message : messages) {
            System.out.println("Parsing");
            System.out.println(message.toString());
            Raw[] data = new Parser(message).getParsedData();
            update(data);
        }
    }

    public double get(Type type, int motor) {
        return car.get(type, motor);
    }

    public HashMap<Type, Double> getInfo(int motor) {
        HashMap<Type, Double> info = new HashMap<>();
        for (Type type : Type.values()) {
            Double value = null;
            try {
                value = get(type, motor);
            } catch (Exception e) {
            }
            info.put(type, value);
        }
        return info;
    }

    public int numberOfMotors() {
        return car.numberOfMotors();
    }
}
