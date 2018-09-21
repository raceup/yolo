package it.raceup.yolo.app;

import it.raceup.yolo.control.Hal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class KvaserApp extends YoloApp {
    private static final String VERSION_FILE = "/res/strings/version.txt";
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
        String version = getVersion();
        log(version);
    }

    public String getVersion() {
        try {
            InputStream in = getClass().getResourceAsStream(VERSION_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            return reader.readLine();
        } catch (Exception e) {
            return "unknown";
        }
    }
}
