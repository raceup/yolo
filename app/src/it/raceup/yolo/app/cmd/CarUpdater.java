package it.raceup.yolo.app.cmd;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.car.Motor;

import java.util.Observable;

/**
 * Updates with car data
 */
public class CarUpdater extends Updater {
    public CarUpdater() {
        super();
        writeLog(Motor.getLineHeader(","));
    }

    private void update(Motor motor) {
        log(motor.toString());  // to std output
        writeLog(motor.getLine(","));  // to file
    }

    private void update(Motor[] motors) {
        for (Motor motor : motors) {
            update(motor);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            this.update((Motor[]) o);
        } catch (Exception e) {
            new YoloException("cannot update car", e, ExceptionType.KVASER)
                    .print();
        }
    }
}
