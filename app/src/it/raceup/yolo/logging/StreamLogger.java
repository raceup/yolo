package it.raceup.yolo.logging;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static it.raceup.yolo.utils.Utils.getTimeNow;

public class StreamLogger implements Logger {
    private OutputStream writer;

    public StreamLogger(OutputStream writer) {
        this.writer = writer;
    }

    public void log(String message, boolean newLine, boolean withTime) {
        if (withTime) {
            message = "[" + getTimeNow("YYYY-MM-dd_HH-mm-ss") + "] " +
                    message;
        }

        if (newLine) {
            message = "\n" + message;
        }

        log(message);
    }

    @Override
    public void log(String message) {
        log(writer, message);
    }

    @Override
    public void logError(String message) {
        log("ERROR: " + message, true, false);
    }

    @Override
    public void log(OutputStream out, String message) {
        try {
            out.write(message.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
        }
    }
}
