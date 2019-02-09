package it.raceup.yolo.app.updater;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.updaters.FileUpdater;
import it.raceup.yolo.models.car.Driver;
import it.raceup.yolo.models.data.CanMessage;
import it.raceup.yolo.models.data.Parser;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.models.kvaser.message.FromKvaserMessage;

import java.util.ArrayList;
import java.util.Observable;

public class ShellDriverUpdater extends ShellCsvUpdater{

    public static final String DEFAULT_FOLDER = FileUpdater.DEFAULT_FOLDER + "/driver/";
    private static final String[] COLUMNS = new String[]{
            "Time",
            "Steering Wheel",
            "Throttle", "Brake",
            "FR susp", "FL sups",
            "RR susp", "RL susp",
    };

    private static final String[] log = new String[COLUMNS.length];

    public ShellDriverUpdater(boolean logToShell, boolean logToFile) {
        super("DRIVER", COLUMNS, DEFAULT_FOLDER, logToShell, logToFile);
        for (int i = 0; i < log.length; i++) {
            log[i] = "0";
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            FromKvaserMessage message = new FromKvaserMessage(o);
            ArrayList<CanMessage> messages = message.getAsCanMessages();
            if (messages != null) {
                update(messages);
            }
        } catch (Exception e) {
            e.printStackTrace();
            new YoloException("cannot log  With Driver", e, ExceptionType.DRIVER)
                    .print();
        }
    }

    private void update(ArrayList<CanMessage> data) {
        for (CanMessage message : data) {
            Raw[] packets = new Parser(message).getParsedData();
            if (packets.length > 0) {
                update(packets);
            }
        }
    }

    private void update(Raw[] raw) {
        Driver imu = new Driver(raw);
        String[] temp = imu.toStringArray();
        for(int i = 0; i < log.length; i++){
            log[i]  = temp[i];
        }
        writeLog(log);
    }

    public void update(Driver driver){
        String[] temp = driver.toStringArray();
        for(int i = 0; i < log.length; i++){
            log[i]  = temp[i];
        }
        writeLog(log);
    }

}
