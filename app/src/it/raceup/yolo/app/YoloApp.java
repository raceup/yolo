package it.raceup.yolo.app;

import it.raceup.yolo.logging.loggers.ShellLogger;

public abstract class YoloApp extends ShellLogger implements Yolo {
    public YoloApp(String tag) {
        super(tag);
    }

    public YoloApp() {
        this("YOLO APP");
    }

    public final void open() {
        try {
            setup();
            start();
        } catch (Exception e) {
            e.printStackTrace();
            log(e);
            close();
        }
    }
}
