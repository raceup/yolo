package it.raceup.yolo.logging.updaters;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.loggers.CSVLogger;
import it.raceup.yolo.logging.loggers.FileLogger;

/**
 * Updates file and screen with data
 */
public abstract class CSVUpdater extends Updater {
    private CSVLogger fileLogger;
    private String[] columns;

    public CSVUpdater(String tag, String[] columns) {
        super(tag);
        this.columns = columns;
    }

    public void setup(String logFile) {
        try {
            FileLogger.create(logFile);
            fileLogger = new CSVLogger(logFile, columns);
        } catch (Exception e) {
            new YoloException("cannot create log file", e, ExceptionType
                    .UNKNOWN).print();
        }
    }

    public void writeLog(String[] columns) {
        if (fileLogger != null) {
            fileLogger.log(columns);
        }
    }
}
