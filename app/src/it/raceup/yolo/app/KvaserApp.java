package it.raceup.yolo.app;

import it.raceup.yolo.control.Hal;

public abstract class KvaserApp extends YoloApp {
    protected Hal hal;

    public KvaserApp(String tag) {
        super(tag);
    }

    @Override
    public abstract void setup();

    @Override
    public abstract void start();

    @Override
    public void close() {
        hal.close();
    }
}
