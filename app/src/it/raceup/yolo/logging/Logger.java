package it.raceup.yolo.logging;

public interface Logger {
    // todo docs
    void log(String message);

    void log(Exception e);
}
