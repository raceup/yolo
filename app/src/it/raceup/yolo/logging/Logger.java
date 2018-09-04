package it.raceup.yolo.logging;

public interface Logger {
    void log(String message);

    void log(Exception e);
}
