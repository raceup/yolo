package it.raceup.yolo.logging;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static it.raceup.yolo.utils.Misc.getTimeNow;

public class StreamLogger implements Logger {
    public String TAG;
    private OutputStream writer;
    private static final String timeFormatter = "%-21s";
    private static final String errorFormatter = "%-80s";
    private static final String tagFormatter = "%-45s";

    public StreamLogger(String tag, OutputStream writer) {
        TAG = tag;
        this.writer = writer;
    }

    public String getLogMessage(String message) {
        return String.format(tagFormatter, "{" + TAG + "}") + " " + message;
    }

    public String getErrorMessage(Exception e) {
        return String.format(errorFormatter, e.toString()) + " ";
    }

    public String getMessage(String message, boolean newLine, boolean withTime) {
        if (withTime) {
            message = String.format(timeFormatter, "[" + getTimeNow() + "]")
                    + " " + message;
        }

        if (newLine) {
            message += "\n";
        }

        return message;
    }

    @Override
    public void log(String message) {
        logToStream(writer, getLogMessage(message));
    }

    @Override
    public void log(Exception e) {
        log(getMessage(getErrorMessage(e), true, true));
    }

    private void logToStream(OutputStream out, String message) {
        try {
            out.write(message.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
        }
    }
}
