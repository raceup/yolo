package it.raceup.yolo.models.kvaser;

import it.raceup.yolo.logging.Logger;
import it.raceup.yolo.logging.ShellLogger;
import it.raceup.yolo.models.data.CanMessage;

import java.io.OutputStream;

public abstract class Kvaser extends RawKvaser implements Logger {
    private final ShellLogger logger;

    public Kvaser() {
        this("KVASER");
    }

    public Kvaser(String tag) {
        logger = new ShellLogger(tag);
    }

    public boolean setup(int canBitrate) {
        return false;
    }

    public CanMessage[] read() {
        return null;
    }

    public boolean write(int id, byte[] data, int flags) {
        return false;
    }

    public void close() {
    }

    public void log(String message) {
        logger.log(message);
    }

    public void logError(String message) {
        logger.logError(message);
    }

    public void log(OutputStream out, String message) {
        logger.log(out, message);
    }
}
