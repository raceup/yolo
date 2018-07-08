package it.raceup.yolo;

import it.raceup.yolo.control.Hal;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.FileLogger;
import it.raceup.yolo.logging.Logger;
import it.raceup.yolo.models.car.Car;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.models.kvaser.Kvaser;
import it.raceup.yolo.models.kvaser.RemoteKvaser;
import it.raceup.yolo.ui.window.Main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static it.raceup.yolo.utils.Utils.getTimeNow;

public class App extends Logger {
    private Kvaser kvaser;
    private Hal controller;
    private Main view;
    private static final String logFile = System.getProperty("user.dir") +
            "/logs/" + getTimeNow("YYYY-MM-dd_HH-mm-ss") + ".log";
    private final FileLogger logger = new FileLogger(logFile);
    private static final int updateMs = 150;

    public App() {
        TAG = "APP";
        setup();
    }

    private void setup() {
        try {
            kvaser = new RemoteKvaser("192.168.0.100");  // todo test
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        Car model = new Car();
        controller = new Hal(model, kvaser);
        view = new Main(kvaser);
        view.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }

    public void start() {
        try {
            controller.startConnection();
        } catch (YoloException e) {
            logError(e.toString());
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        updateView();
                    } catch (Exception e) {
                        logError("error while updating");
                    }
                }
            }, 0, updateMs);
        }
    }

    private void updateView() {
        for (int i = 0; i < controller.numberOfMotors(); i++) {
            HashMap<Type, Double> info = controller.getInfo(i);
            for (Type type : info.keySet()) {
                Double value = info.get(type);

                view.update(i, type, value);
                log(i, type, value);
            }
        }
    }

    private void log(int motor, Type type, Double value) {
        String log = Integer.toString(motor) + ": " + value.toString() +
                " " + type.toString();
        logger.appendWithTime(log);
    }

    private void close() {
        view.close();
        logger.save();
    }
}
