package it.raceup.yolo.logging.updaters;

import it.raceup.yolo.logging.loggers.ShellLogger;

import java.util.Observer;

public abstract class Updater extends ShellLogger implements Observer {
    public Updater(String tag) {
        super(tag);
    }

    public Updater() {
        this("UPDATER");
    }
}
