package it.raceup.yolo.app.gui;

import it.raceup.yolo.app.KvaserApp;
import it.raceup.yolo.control.Hal;
import it.raceup.yolo.models.car.Car;
import it.raceup.yolo.models.kvaser.FakeBlackBird;
import it.raceup.yolo.ui.window.Main;

/**
 * UI interface for telemetry (yolo-gui). Opens window for settings and
 * incoming data. Listens for new data and updates screen as soon as possible.
 */
public class App extends KvaserApp {
    private Main view;

    public App() {
        this("GUI APP");
    }

    public App(String tag) {
        super(tag);
        view = new Main();
    }

    public static void main(String[] args) {
        App app = new App();
        app.open();
    }

    @Override
    protected void setupKvaser() {
        String ip = "192.168.1.1";
        String bitrate = "1m";

        // todo defer construction of blackbird
        hal = new Hal(
                new Car(),
                new FakeBlackBird(ip)  // todo use BlackBird in production
        );

        try {
            hal.setup(bitrate);
        } catch (Exception e) {
            log(e);
            System.exit(1);
        }
    }

    @Override
    protected void setupUpdaters() {
        hal.addObserverToCar(new CarUpdater(view));
    }

    @Override
    public void start() {
        hal.start();  // todo defer start
    }
}
