package it.raceup.yolo.app.cmd;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.FileLogger;
import it.raceup.yolo.logging.ShellLogger;

import java.util.Observer;

import static it.raceup.yolo.utils.Utils.getTimeNow;

/**
 * Updates file and screen with data
 */
public abstract class Updater extends ShellLogger implements Observer {
    private static final String logFile = System.getProperty("user.dir") +
            "/logs/" + getTimeNow("YYYY-MM-dd_HH-mm-ss") + ".log";
    protected FileLogger logger;

    public Updater() {
        this(logFile);
    }

    public Updater(String logFile) {
        super("UPDATER");
        setup(logFile);
    }

    private void setup(String logFile) {
        try {
            FileLogger.create(logFile);
            logger = new FileLogger(logFile);
        } catch (Exception e) {
            new YoloException("cannot create log file", ExceptionType
                    .UNKNOWN).print();
        }
    }
}
