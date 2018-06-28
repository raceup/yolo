package it.raceup.yolo.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import static it.raceup.yolo.utils.Utils.getTimeNow;

public class Debugger {
    private final static Logger LOGGER = Logger.getLogger(Logger
            .GLOBAL_LOGGER_NAME);

    public Debugger(Level level) {
        LOGGER.setLevel(level);  // just messages higher than this level
        // will be written
    }

    public void log(Level level, String tag, String message) {
        LOGGER.log(level, getCompleteMessage(tag, message));
    }

    public void log(String message) {
        LOGGER.log(Level.ALL, message);
    }

    private String getCompleteMessage(String tag, String message) {
        String timeNow = getTimeNow("HH:mm:ss");
        return "[" + timeNow + "]" + "@" + tag + ": " + message;
    }
}
