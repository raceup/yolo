package it.raceup.yolo.app.updater;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.updaters.FileUpdater;

import java.util.Observable;

/**
 * Updates with CAN data
 */
public class ShellBatteryUpdater extends ShellCsvUpdater {
    public static final String DEFAULT_FOLDER = FileUpdater.DEFAULT_FOLDER + "/battery/";
    private static final String[] COLUMNS = new String[]{
            // todo
    };
    private final String SEPARATOR = "|";

    public ShellBatteryUpdater(boolean logToShell, boolean logToFile) {
        super("BATTERY", COLUMNS, DEFAULT_FOLDER, logToShell, logToFile);
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            // todo
        } catch (Exception e) {
            new YoloException("cannot update battery", e, ExceptionType.KVASER)
                    .print();
        }
    }
}
