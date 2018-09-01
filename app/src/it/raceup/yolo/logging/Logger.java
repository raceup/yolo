package it.raceup.yolo.logging;

import java.io.OutputStream;

public interface Logger {
    // todo docs
    void log(String message);
    void logError(String message);
    void log(OutputStream out, String message);
}
