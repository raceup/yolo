package it.raceup.yolo;

import it.raceup.yolo.control.Hal;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.FileLogger;
import it.raceup.yolo.models.Car;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.kvaser.Kvaser;
import it.raceup.yolo.ui.window.Main;
import it.raceup.yolo.utils.Debugger;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import static it.raceup.yolo.utils.Utils.getTimeNow;

public class App extends Debugger {
    private Kvaser kvaser;
    private Hal controller;
    private Main view;
    private static final String logFile = System.getProperty("user.dir") +
            "/logs/" + getTimeNow("YYYY-MM-dd_HH-mm-ss") + ".log";
    private final FileLogger logger = new FileLogger(logFile);

    public App() {
        setup();
    }

    private void setup() {
        try {
            kvaser = new Kvaser();
        } catch (YoloException e) {
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
            System.err.println(e.toString());
        }  // start retrieving Kvaser data

        logger.appendWithTime("hello world");
        logger.appendWithTime("hello world 1");

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Raw[] buffer = controller.getBuffer();
                    for (Raw data : buffer) {
                        try {
                            view.update(data);
                        } catch (Exception e) {
                            System.err.println("Cannot update view");
                            System.err.println("\t" + e.toString());
                        }

                        try {
                            logger.appendWithTime(data.toString());
                        } catch (Exception e) {
                            System.err.println("Cannot log");
                            System.err.println("\t" + e.toString());
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Cannot get current Kvaser buffer");
                    System.err.println("\t" + e.toString());
                }
            }
        }, 0, 100);
    }

    private void close() {
        view.close();
        logger.save();
    }
}
