package it.raceup.yolo.logging.updaters;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.loggers.CSVLogger;
import it.raceup.yolo.logging.loggers.FileLogger;

import java.io.FileNotFoundException;

import static it.raceup.yolo.utils.Misc.getTimeNow;

/**
 * Updates file and screen with data
 */
public abstract class CSVUpdater extends FileUpdater {
    public static final String DEFAULT_FOLDER = System.getProperty("user.dir") + "/logs/";

    public CSVUpdater(String[] columns) throws FileNotFoundException {
        this("CSV UPDATER", columns);
    }

    public CSVUpdater(String tag, String[] columns) throws FileNotFoundException {
        this(DEFAULT_FOLDER, tag, columns);
    }

    public CSVUpdater(String folder, String tag, String[] columns) throws FileNotFoundException {
        super(tag);
        String logFile = folder + tag + "_" + getTimeNow("YYYY-MM-dd_HH-mm-ss") + ".csv";
        setup(logFile);
        fileLogger = new CSVLogger(columns);
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
