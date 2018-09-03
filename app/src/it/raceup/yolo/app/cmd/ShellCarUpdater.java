package it.raceup.yolo.app.cmd;

import it.raceup.yolo.app.FileUpdater;
import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.car.Motor;

import java.util.Observable;

/**
 * Updates with car data
 */
public class ShellCarUpdater extends FileUpdater {
    public ShellCarUpdater() {
        super("CAR UPDATER");
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
            new YoloException("cannot update car", e, ExceptionType.KVASER)
                    .print();
        }
    }
}