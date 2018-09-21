package it.raceup.yolo.logging.updaters;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.loggers.FileLogger;

import static it.raceup.yolo.utils.Misc.getTimeNow;

/**
 * Updates file and screen with data
 */
public abstract class FileUpdater extends Updater {
    public static final String DEFAULT_FOLDER = System.getProperty("user.dir") + "/logs/";
    private FileLogger fileLogger;

    public FileUpdater() {
        this("FILE UPDATER");
    }

    public FileUpdater(String tag) {
        this(DEFAULT_FOLDER, tag);
    }

    public FileUpdater(String folder, String tag) {
        super(tag);
        String logFile = folder + tag + "_" + getTimeNow("YYYY-MM-dd_HH-mm-ss") + ".log";
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
