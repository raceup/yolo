package it.raceup.yolo.logging.updaters;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.loggers.CSVLogger;
import it.raceup.yolo.logging.loggers.FileLogger;

import static it.raceup.yolo.utils.Misc.getTimeNow;

/**
 * Updates file and screen with data
 */
public abstract class CSVUpdater extends Updater {
    public static final String DEFAULT_FOLDER = System.getProperty("user.dir") + "/logs/";
    private CSVLogger fileLogger;
    private String[] columns;

    public CSVUpdater(String tag, String[] columns) {
        this(DEFAULT_FOLDER, tag, columns);
    }

    public CSVUpdater(String folder, String tag, String[] columns) {
        super(tag);

        this.columns = columns;
        String logFile = folder + tag + "_" + getTimeNow("YYYY-MM-dd_HH-mm-ss") + ".csv";
        setup(logFile);
    }

    private void setup(String logFile) {
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
