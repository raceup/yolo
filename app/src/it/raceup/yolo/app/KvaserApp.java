package it.raceup.yolo.app;

import it.raceup.yolo.control.Hal;

public abstract class KvaserApp extends YoloApp {
    private static final String VERSION = "";
    protected Hal hal;

    public KvaserApp(String tag) {
        super(tag);
    }

    @Override
    public void setup() {
        setupKvaser();
        setupUpdaters();
    }

    protected abstract void setupKvaser();

    protected abstract void setupUpdaters();

    @Override
    public abstract void start();

    @Override
    public void close() {
        hal.close();
    }
}
