package it.raceup.yolo.logging;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static it.raceup.yolo.utils.Utils.getTimeNow;

public class StreamLogger implements Logger {
    protected static String TAG = "STREAM LOGGER";
    private OutputStream writer;

    public StreamLogger(OutputStream writer) {
        this.writer = writer;
    }

    public String getLogMessage(String message) {
        return "{" + TAG + "} " + message;
    }

    public String getErrorMessage(Exception e) {
        return "! Error ! " + e.toString();
    }

    public String getMessage(String message, boolean newLine, boolean withTime) {
        if (withTime) {
            message = "[" + getTimeNow("YYYY-MM-dd_HH-mm-ss") + "] " +
                    message;
        }

        if (newLine) {
            message = "\n" + message;
        }

        return message;
    }

    @Override
    public void log(String message) {
        logToStream(writer, getLogMessage(message));
    }

    @Override
    public void log(Exception e) {
        log(getMessage(getErrorMessage(e), true, false));
    }

    private void logToStream(OutputStream out, String message) {
        try {
            out.write(message.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
        }
    }
}
