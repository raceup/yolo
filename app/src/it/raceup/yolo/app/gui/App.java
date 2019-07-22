package it.raceup.yolo.app.gui;

import it.raceup.yolo.app.KvaserApp;
import it.raceup.yolo.app.updater.ShellCanUpdater;
import it.raceup.yolo.app.updater.ShellDriverUpdater;
import it.raceup.yolo.app.updater.ShellImuUpdater;
import it.raceup.yolo.app.updater.ShellMotorsUpdater;
import it.raceup.yolo.control.Hal;
import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.car.Driver;
import it.raceup.yolo.models.car.Imu;
import it.raceup.yolo.models.car.Motors;
import it.raceup.yolo.models.kvaser.BlackBird;
import it.raceup.yolo.models.kvaser.FakeBlackBird;
import it.raceup.yolo.ui.window.MainFrame;

import javax.swing.*;

import static it.raceup.yolo.ui.dialog.ExceptionPanel.showMessage;
import static it.raceup.yolo.ui.dialog.SettingsPanel.getSettings;

/**
 * UI interface for telemetry (yolo-gui). Opens window for settings and
 * incoming data. Listens for new data and updates screen as soon as possible.
 */
public class App extends KvaserApp {
    private final MainFrame view;

    public App() {
        this("GUI APP");
    }

    public App(String tag) {
        super(tag);
        view = new MainFrame();
    }

    public static void main(String[] args) {
        try {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }
            App app = new App();
            app.open();
        } catch (Exception e) {
            e.printStackTrace();
            showMessage(e);
        }
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

        boolean logMotors = Boolean.parseBoolean(settings[2]);
        boolean logIMU = Boolean.parseBoolean(settings[3]);
        boolean logDriver = Boolean.parseBoolean(settings[4]);

        hal = new Hal(
                new Motors(),
                new FakeBlackBird(ip),
                //new BlackBird(ip),
                new Imu(),
                new Driver(),
                new boolean[]{logMotors, logIMU, logDriver}
        );

        try {
            hal.setup(bitrate);
        } catch (Exception e) {
            log(e);
            showMessage(e);
        }
    }


    @Override
    protected void setupUpdaters() {
        hal.addObserverToMotors(view.getMotorPanels());
        hal.addObserverToImu(view.getDynamicsFrame());
        hal.addObserverToDriver(view.getDriverFrame());
        hal.addObserverToKvaser(new ShellCanUpdater(false, true));

        // todo hal.addObserverToKvaser(view.getCanMessagesFrame());
        // todo hal.addObserverToKvaser(view.getBatteryFrame());
        // todo hal.addObserverToKvaser(view.getDynamicsFrame());
    }

    private void setupLogUpdaters(boolean logMotors, boolean logCan, boolean logBattery, boolean logIMU, boolean logDriver) {
        if (logMotors) {
            hal.addObserverToMotors(new ShellMotorsUpdater(false, true));
        }
        if (logCan) {
            hal.addObserverToKvaser(new ShellCanUpdater(false, true));
        }
        if (logIMU) {
            hal.addObserverToKvaser(new ShellImuUpdater(false, true));
        }
        if (logDriver) {
            hal.addObserverToKvaser(new ShellDriverUpdater(false, true));
        }
    }

    @Override
    public void start() {
        hal.start();
    }
}
