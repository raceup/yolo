package it.raceup.yolo;

import it.raceup.yolo.control.Hal;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.Car;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.kvaser.Kvaser;
import it.raceup.yolo.ui.window.Main;
import it.raceup.yolo.utils.Debugger;

import java.util.Timer;
import java.util.TimerTask;

public class App extends Debugger {
    private Kvaser kvaser;
    private Car model;
    private Hal controller;
    private Main view;

    public App() {
        setup();
    }

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }

    private void setup() {
        try {
            kvaser = new Kvaser();
        } catch (YoloException e) {
            System.out.println(e.toString());
        }

        model = new Car();
        controller = new Hal(model, kvaser);
        view = new Main(kvaser);
    }

    public void start() {
        try {
            controller.startConnection();
        } catch (YoloException e) {
            System.err.println(e.toString());
        }  // start logging Kvaser data

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Raw[] buffer = controller.getBuffer();
                for (Raw data : buffer) {
                    view.update(data);
                }
            }
        }, 0, 100);

    }
}
