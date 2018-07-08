package it.raceup.yolo.utils;

import static it.raceup.yolo.utils.Utils.convertTime;

public class Logger {
    public static String TAG = "LOGGER";

    protected void logAction(String message) {
        String timing = "[" + convertTime(System.currentTimeMillis()) + "]";
        String content = ": " + message;
        System.out.println(timing + " " + TAG + content);
    }
}
