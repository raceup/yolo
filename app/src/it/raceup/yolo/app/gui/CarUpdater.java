package it.raceup.yolo.app.gui;

import it.raceup.yolo.app.FileUpdater;
import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.car.Motor;

import java.util.Observable;

/**
 * Updates screen with car data
 */
public class CarUpdater extends FileUpdater {
    public CarUpdater() {
        super("CAR UPDATER (VIEW)");
        writeLog(Motor.getLineHeader(","));
    }

    private void update(Motor motor) {
        String message = motor.toString();
        String[] lines = message.split("\n");
        for (String line : lines) {
            log(line);  // to std output
        }
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
            new YoloException("cannot update car", e, ExceptionType.VIEW)
                    .print();
        }
    }
}
