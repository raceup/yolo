package it.raceup.yolo.control;

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

    public void startConnection() {
        Thread t = new Thread(() -> {
            while (kvaser.hasData()) {
                CanData data = kvaser.getMostRecentData();
                System.out.println(data.toString());  // todo update
            }
        });
        t.start();
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
