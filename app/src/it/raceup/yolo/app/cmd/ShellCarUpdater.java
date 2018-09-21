package it.raceup.yolo.app.cmd;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.updaters.CSVUpdater;
import it.raceup.yolo.logging.updaters.FileUpdater;
import it.raceup.yolo.models.car.Motor;

import java.util.Observable;

import static it.raceup.yolo.utils.Misc.getTimeNow;

/**
 * Updates with car data
 */
public class ShellCarUpdater extends CSVUpdater {
    public static final String DEFAULT_FOLDER = FileUpdater.DEFAULT_FOLDER + "/car/";
    private static final String[] COLUMNS = new String[]{};  // todo
    private final boolean logToFile;

    public ShellCarUpdater(boolean logToFile) {
        super("MOTORS", COLUMNS);
        this.logToFile = logToFile;

        setup();
    }

    private void setup() {
        if (this.logToFile) {
            String logFile = DEFAULT_FOLDER + getTimeNow("YYYY-MM-dd_HH-mm-ss") + ".csv";
            setup(logFile);
        }
    }

    private void update(Motor motor) {
        String message = motor.toString();
        String[] lines = message.split("\n");
        for (String line : lines) {
            log(line);  // to std output
        }

        if (this.logToFile) {
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
