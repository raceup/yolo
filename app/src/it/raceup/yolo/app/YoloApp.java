package it.raceup.yolo.app;

import it.raceup.yolo.logging.ShellLogger;

public abstract class YoloApp extends ShellLogger implements Yolo {
    public YoloApp(String tag) {
        super(tag);
    }

    public YoloApp() {
        this("YOLO APP");
    }

    protected final void build() {
        try {
            setup();
            start();
        } catch (Exception e) {
            log(e);
        } finally {
            close();
        }
    }
}
