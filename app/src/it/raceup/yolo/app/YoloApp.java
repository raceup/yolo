package it.raceup.yolo.app;

/**
 * Interface for all telemetry app using a Kvaser and YOLO framework
 */
public interface YoloApp {
    void setup();

    void start();

    void close();
}
