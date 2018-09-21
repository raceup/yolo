package it.raceup.yolo.app.updater;

import it.raceup.yolo.logging.updaters.CSVUpdater;

import static it.raceup.yolo.utils.Misc.getTimeNow;

public abstract class ShellCsvUpdater extends CSVUpdater {
    private final boolean logToFile;
    private final boolean logToShell;
    private final String folder;
    private final String fileName = getTimeNow("YYYY-MM-dd_HH-mm-ss") + ".csv";

    public ShellCsvUpdater(String tag, String[] columns, String folder, boolean logToShell, boolean logToFile) {
        super(tag, columns);

        this.folder = folder;
        this.logToShell = logToShell;
        this.logToFile = logToFile;

        setup();
    }

    public boolean isLogToFile() {
        return logToFile;
    }

    public boolean isLogToShell() {
        return logToShell;
    }

    private void setup() {
        if (this.logToFile) {
            String logFile = folder + fileName;
            setup(logFile);
        }
    }
}
