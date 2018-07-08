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

import static core.Canlib.canBITRATE_1M;

/**
 * Parses inputs and updates model
 */
public class Hal {
    private Car car;
    private Kvaser kvaser;
    private Raw[] buffer;

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
                    // todo get kvaser data
                    buffer = new Parser(0, new byte[]{0}).getParsedData();
                    update();
                }
            }, 0, 10);
        } else {
            throw new YoloException("Cannot start Kvaser connection",
                    ExceptionType.KVASER);
        }
    }

    public void close() {
        kvaser.close();
    }

    private void update() {
        for (Raw data : buffer) {
            car.update(data);
        }
    }

    public double get(Type type, int motor) {
        return car.get(type, motor);
    }

    public Raw[] getBuffer() {
        return buffer;
    }
}
