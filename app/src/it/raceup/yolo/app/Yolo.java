package it.raceup.yolo.app;

/**
 * Interface for all telemetry app using a Kvaser and YOLO framework
 */
public interface Yolo {
    void setup();

    void start();

    void close();
}
