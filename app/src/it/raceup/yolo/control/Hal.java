package it.raceup.yolo.control;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.Car;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.models.kvaser.CanData;
import it.raceup.yolo.models.kvaser.Kvaser;

/**
 * Parses inputs and updates model
 */
public class Hal {
    private Car car;
    private Kvaser kvaser;

    public Hal(Car car) {
        this.car = car;
        setup();
    }

    private void setup() {
        try {
            kvaser = new Kvaser();
        } catch (YoloException e) {
            System.out.println(e.toString());
        }
    }

    public void startConnection() throws YoloException {
        if (kvaser.startConnection()) {
            Thread t = new Thread(() -> {
                while (kvaser.hasData()) {
                    CanData data = kvaser.getMostRecentData();
                    System.out.println(data.toString());  // todo update
                }
                System.out.println("Kvaser has no more data!");
            });
            t.start();
        } else {
            throw new YoloException("Cannot start kvaser connection",
                    ExceptionType.KVASER);
        }
    }

    public void close() {
        kvaser.close();
    }

    private void update(Raw data) {
        car.update(data);
    }

    public double get(Type type, int motor) {
        return car.get(type, motor);
    }
}
