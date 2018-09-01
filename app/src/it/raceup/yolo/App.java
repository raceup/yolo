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
        this("192.168.1.1");
    }

    /**
     * Creates app
     *
     * @param ip Kvaser IP
     */
    public App(String ip) {
        TAG = "APP";
        setup(ip);
    }

    public static void main(String[] args) {
        if (args.length != 1) {  // todo refactor
            System.out.println("Usage: java -jar yolo.jar <IP>. Example: " +
                    "java jar yolo.jar 192.168.1.12");
            System.exit(1);
        }

        App app = new App(args[0]);
        app.start();
    }

    /**
     * Setups app and connects to kvaser
     *
     * @param ip Kvaser IP
     */
    private void setup(String ip) {
        try {
            kvaser = new RemoteKvaser(ip);  // todo user dialog
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
                        logError(e.toString());
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

                try {
                    view.update(i, type, value);  // update screen
                } catch (Exception e) {
                    logError(e.toString());
                }

                try {
                    log(i, type, value);  // add to log
                } catch (Exception e) {
                    logError(e.toString());
                }
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
