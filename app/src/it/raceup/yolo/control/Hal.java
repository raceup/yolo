package it.raceup.yolo.control;

import it.raceup.yolo.app.updater.ShellDriverUpdater;
import it.raceup.yolo.app.updater.ShellImuUpdater;
import it.raceup.yolo.app.updater.ShellMotorsUpdater;
import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.loggers.ShellLogger;
import it.raceup.yolo.models.car.Driver;
import it.raceup.yolo.models.car.Imu;
import it.raceup.yolo.models.car.Motors;
import it.raceup.yolo.models.data.CanMessage;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.models.kvaser.Kvaser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;

/**
 * Handles business logic of telemetry. Opens connection with Kvaser and
 * remains listening for incoming data. When there is new data, updates motors data.
 */
public class Hal extends ShellLogger {
    private final Motors motors;
    private final Kvaser kvaser;
    private final Imu imu;
    private final Driver driver;
    private boolean[] loggerPreferences;
    private Runnable runner;
    private final long LOG_RATIO = 20; //Milliseconds


    public Hal(Motors motors, Kvaser kvaser, Imu imu, Driver driver, boolean[] loggerPreferences) {
        super("HAL");

        this.motors = motors;
        this.kvaser = kvaser;
        this.imu = imu;
        this.driver = driver;
        this.loggerPreferences = loggerPreferences;

        addObserverToKvaser(motors);
        addObserverToKvaser(imu);
        addObserverToKvaser(driver);

        runner = new Runnable() {
            @Override
            public void run() {
                ShellImuUpdater imuLogger = null;
                ShellMotorsUpdater motorLogger = null;
                ShellDriverUpdater driverLogger = null;

                if (loggerPreferences[0]) {
                    motorLogger = new ShellMotorsUpdater(false, true);
                }
                if (loggerPreferences[1]) {
                    imuLogger = new ShellImuUpdater(false, true);
                }
                if (loggerPreferences[2]) {
                    driverLogger = new ShellDriverUpdater(false, true);
                }

                while (true) {
                    try {
                        motorLogger.update(motors);
                        driverLogger.update(driver);
                        imuLogger.update(imu);
                        Thread.sleep(LOG_RATIO);
                    } catch (NullPointerException e) {
                        //nothing to worry about
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        new YoloException("interrupt exception", e, ExceptionType.MODEL).print();
                    }
                }
            }
        };
        Thread thread = new Thread(runner);
        thread.start();
    }

    public Hal(Motors motors, Kvaser kvaser, Imu imu, Driver driver) {
        this.motors = motors;
        this.kvaser = kvaser;
        this.imu = imu;
        this.driver = driver;

        addObserverToKvaser(motors);
        addObserverToKvaser(imu);
        addObserverToKvaser(driver);

    }

    public void addObserverToKvaser(Observer observer) {
        kvaser.addObserver(observer);
    }

    public void addObserverToMotors(Observer observer) {
        motors.addObserver(observer);
    }

    public void addObserverToImu(Observer observer) {
        imu.addObserver(observer);
    }

    public void addObserverToDriver(Observer observer) {
        driver.addObserver(observer);
    }

    public void setup(String canBitrate) throws YoloException {
        if (!kvaser.setup(canBitrate)) {
            throw new YoloException("cannot start Kvaser connection",
                    ExceptionType.KVASER);
        }
    }

    public void start() {
        Thread thread = new Thread(kvaser);
        thread.start();
    }

    public void close() {
        log("closing");
        kvaser.close();
    }

    public double get(Type type, int motor) {
        return motors.get(type, motor);
    }

    public Motors getMotor() {
        return motors;
    }

    public Kvaser getKvaser() {
        return kvaser;
    }

    public Driver getDriver() {
        return driver;
    }

    public Imu getImu() {
        return imu;
    }

    public ArrayList<CanMessage> getRaw() {
        return kvaser.getData();
    }

    public HashMap<Type, Double> getInfo(int motor) {
        HashMap<Type, Double> info = new HashMap<>();
        for (Type type : Type.values()) {
            Double value = null;
            try {
                value = get(type, motor);
            } catch (Exception e) {
                log(e);
            }
            info.put(type, value);
        }
        return info;
    }
}
