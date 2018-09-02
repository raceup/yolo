package it.raceup.yolo.app;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.FileLogger;

import static it.raceup.yolo.utils.Utils.getTimeNow;

/**
 * Updates file and screen with data
 */
public abstract class FileUpdater extends Updater {
    private static final String logFile = System.getProperty("user.dir") +
            "/logs/" + getTimeNow("YYYY-MM-dd_HH-mm-ss") + ".log";
    protected FileLogger fileLogger;

    public FileUpdater() {
        this("FILE UPDATER", logFile);
    }

    public FileUpdater(String tag) {
        this(tag, logFile);
    }

    public FileUpdater(String tag, String logFile) {
        super(tag);
        setup(logFile);
    }

    private void setup(String logFile) {
        try {
            FileLogger.create(logFile);
            fileLogger = new FileLogger(logFile);
        } catch (Exception e) {
            new YoloException("cannot create log file", ExceptionType
                    .UNKNOWN).print();
        }
    }

    public void writeLog(String message) {
        if (fileLogger != null) {
            fileLogger.log(fileLogger.getMessage(message, true, true));
        }
    }
}
