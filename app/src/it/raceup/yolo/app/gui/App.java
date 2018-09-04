package it.raceup.yolo.app.gui;

import it.raceup.yolo.app.KvaserApp;
import it.raceup.yolo.control.Hal;
import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.car.Car;
import it.raceup.yolo.models.kvaser.FakeBlackBird;
import it.raceup.yolo.ui.window.Main;

import static it.raceup.yolo.app.gui.SettingsPanel.getSettings;

/**
 * UI interface for telemetry (yolo-gui). Opens window for settings and
 * incoming data. Listens for new data and updates screen as soon as possible.
 */
public class App extends KvaserApp {
    private final Main view;

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

    private String[] getLaunchSettings() {
        String[] settings = getSettings();
        if (settings == null) {
            new YoloException("User not input settings", ExceptionType
                    .VIEW).print();
            System.exit(1);
        }

        return settings;
    }

    @Override
    protected void setupKvaser() {
        String[] settings = getLaunchSettings();
        String ip = settings[0];
        String bitrate = settings[1];

        hal = new Hal(
                new Car(),
                new FakeBlackBird(ip)
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
        hal.addObserverToCar(view.getMotorPanels());
        hal.addObserverToKvaser(view.getCanMessagesFrame());
    }

    @Override
    public void start() {
        hal.start();
    }
}
