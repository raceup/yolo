package it.raceup.yolo.app.updater;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.updaters.FileUpdater;
import it.raceup.yolo.models.car.Motor;

import java.util.Observable;

/**
 * Updates with car data
 */
public class ShellMotorsUpdater extends ShellCsvUpdater {
    public static final String DEFAULT_FOLDER = FileUpdater.DEFAULT_FOLDER + "/motors/";
    private static final String[] COLUMNS = new String[]{};  // todo

    public ShellMotorsUpdater(boolean logToShell, boolean logToFile) {
        super("MOTORS", COLUMNS, DEFAULT_FOLDER, logToShell, logToFile);
    }

    private void update(Motor motor) {
        String message = motor.toString();

        if (this.isLogToShell()) {
            String[] lines = message.split("\n");
            for (String line : lines) {
                log(line);  // to std output
            }
        }

        if (this.isLogToFile()) {
            // todo writeLog();  // to file
        }
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
