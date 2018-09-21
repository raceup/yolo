package it.raceup.yolo.app.updater;

import it.raceup.yolo.logging.updaters.FileUpdater;

import java.util.Observable;

/**
 * Updates with CAN data
 */
public class ShellImuUpdater extends ShellCsvUpdater {
    public static final String DEFAULT_FOLDER = FileUpdater.DEFAULT_FOLDER + "/imu/";
    private static final String[] COLUMNS = new String[]{
            // todo
    };
    private final String SEPARATOR = "|";

    public ShellImuUpdater(boolean logToShell, boolean logToFile) {
        super("CAN", COLUMNS, DEFAULT_FOLDER, logToShell, logToFile);
    }

    @Override
    public void update(Observable observable, Object o) {
        // todo
    }
}
