package it.raceup.yolo.app.gui;

import it.raceup.yolo.error.YoloException;

public class ExceptionDialog {
    private final YoloException exception;

    public ExceptionDialog(Exception e) {
        this.exception = YoloException.parse(e);

        setup();
    }

    private void setup() {
        setupLayout();
    }

    private void setupLayout() {
        // todo
    }

    public void open() {
        // todo
    }
}
