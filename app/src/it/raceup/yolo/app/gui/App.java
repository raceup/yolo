package it.raceup.yolo.app.gui;

import it.raceup.yolo.app.KvaserApp;
import it.raceup.yolo.app.updater.ShellBatteryUpdater;
import it.raceup.yolo.app.updater.ShellCanUpdater;
import it.raceup.yolo.app.updater.ShellImuUpdater;
import it.raceup.yolo.app.updater.ShellMotorsUpdater;
import it.raceup.yolo.control.Hal;
import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.car.Imu;
import it.raceup.yolo.models.car.Motors;
import it.raceup.yolo.models.data.Type;
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

            //start test code
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }
            //end test code
            App app = new App();
            app.open();
        } catch (Exception e) {
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
        boolean logCan = Boolean.parseBoolean(settings[3]);
        boolean logBattery = Boolean.parseBoolean(settings[4]);
        //boolean logIMU = Boolean.parseBoolean(settings[5]);
        //test code
        boolean logIMU = true;
        double[] x = {0,0,0};
        hal = new Hal(
                new Motors(),
                new FakeBlackBird(ip),
                new Imu(Type.ACCELERATION, x)
        );

        try {
            hal.setup(bitrate);
        } catch (Exception e) {
            log(e);
            showMessage(e);
        }

        setupLogUpdaters(logMotors, logCan, logBattery, logIMU);  // add file loggers
    }

    @Override
    protected void setupUpdaters() {
        hal.addObserverToMotors(view.getMotorPanels());

        //testcode
        hal.addObserverToImu(view.getDynamicsFrame());

        // todo hal.addObserverToKvaser(view.getCanMessagesFrame());
        // todo hal.addObserverToKvaser(view.getBatteryFrame());
        // todo hal.addObserverToKvaser(view.getDynamicsFrame());
    }

    private void setupLogUpdaters(boolean logMotors, boolean logCan, boolean logBattery, boolean logIMU) {
        if (logMotors) {
            hal.addObserverToMotors(new ShellMotorsUpdater(false, true));
        }

        if (logCan) {
            hal.addObserverToKvaser(new ShellCanUpdater(false, true));
        }

        if (logBattery) {
            hal.addObserverToKvaser(new ShellBatteryUpdater(false, true));
        }

        if (logIMU) {
            hal.addObserverToKvaser(new ShellImuUpdater(false, true));
        }
    }

    @Override
    public void start() {
        hal.start();
    }
}
