package it.raceup.yolo.app;

import it.raceup.yolo.logging.ShellLogger;

import java.util.Observer;

public abstract class Updater extends ShellLogger implements Observer {
    public Updater(String tag) {
        super(tag);
    }

    public Updater() {
        this("UPDATER");
    }

}
