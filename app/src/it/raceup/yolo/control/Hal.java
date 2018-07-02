package it.raceup.yolo.control;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.Car;
import it.raceup.yolo.models.data.Parser;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.models.kvaser.Kvaser;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Parses inputs and updates model
 */
public class Hal {
    private Car car;
    private Kvaser kvaser;
    private Raw mostRecentValue;

    public Hal(Car car, Kvaser kvaser) {
        this.car = car;
        this.kvaser = kvaser;
    }

    public void startConnection() throws YoloException {
        if (kvaser.startConnection()) {
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    // CanData data = kvaser.getMostRecentData();
                    // Raw value = new Raw(data.getId(), data.getData());
                    mostRecentValue = new Parser(0, new byte[]{0}).buildRawData();
                    update();
                }
            }, 0, 10);
        } else {
            throw new YoloException("Cannot start kvaser connection",
                    ExceptionType.KVASER);
        }
    }

    public void close() {
        kvaser.close();
    }

    private void update() {
        car.update(mostRecentValue);
    }

    public double get(Type type, int motor) {
        return car.get(type, motor);
    }

    public Raw getMostRecentValue() {
        return mostRecentValue;
    }
}
