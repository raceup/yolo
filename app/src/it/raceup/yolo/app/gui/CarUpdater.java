package it.raceup.yolo.app.gui;

import it.raceup.yolo.app.FileUpdater;
import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.car.Motor;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.ui.window.Main;

import java.util.Observable;

/**
 * Updates screen with car data
 */
public class CarUpdater extends FileUpdater {
    private Main view;

    public CarUpdater(Main view) {
        super("CAR UPDATER (VIEW)");

        this.view = view;
        writeLog(Motor.getLineHeader(","));
    }

    private void update(Motor[] motors) {
        for (int i = 0; i < motors.length; i++) {
            for (Type type : Raw.ALL) {
                double value = motors[i].get(type);  // fetch from model
                view.update(i, type, value);  // update view
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            this.update((Motor[]) o);
        } catch (Exception e) {
            new YoloException("cannot update car", e, ExceptionType.VIEW)
                    .print();
        }
    }
}
