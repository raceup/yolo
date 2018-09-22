package it.raceup.yolo.app;

import it.raceup.yolo.control.Hal;

import static it.raceup.yolo.Data.getAppVersion;

public abstract class KvaserApp extends YoloApp {
    protected Hal hal;

    public KvaserApp(String tag) {
        super(tag);

        logVersion();
        addCloseListener();
    }

    private void addCloseListener() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Thread.sleep(10);
                log("shutting down");
                close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));  // listen for SIGINT
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

    private void logVersion() {
        String version = getAppVersion();
        log(version);
    }
}
