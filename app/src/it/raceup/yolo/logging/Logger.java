package it.raceup.yolo.logging;

import java.io.PrintStream;

import static it.raceup.yolo.utils.Utils.convertTime;

public class Logger {
    public static String TAG = "LOGGER";

    protected void logAction(String message) {
        log(System.out, message);
    }

    protected void logError(String message) {
        log(System.err, message);
    }

    protected void log(PrintStream out, String message) {
        String timing = "[" + convertTime(System.currentTimeMillis()) + "]";
        String content = ": " + message;
        out.println(timing + " " + TAG + content);
    }
}
