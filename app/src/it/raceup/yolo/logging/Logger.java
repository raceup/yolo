package it.raceup.yolo.logging;

import java.io.OutputStream;

public interface Logger {
    String TAG = "LOGGER";

    void log(String message);

    void logError(String message);

    void log(OutputStream out, String message);
}
