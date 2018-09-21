package it.raceup.yolo.logging.loggers;

public interface Logger {
    void log(String message);

    void log(Exception e);
}
