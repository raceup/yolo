package it.raceup.yolo.app.updater;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.updaters.FileUpdater;
import it.raceup.yolo.models.car.Imu;
import it.raceup.yolo.models.data.CanMessage;
import it.raceup.yolo.models.data.Parser;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.models.kvaser.message.FromKvaserMessage;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Updates with CAN data
 */
public class ShellImuUpdater extends ShellCsvUpdater {
    public static final String DEFAULT_FOLDER = FileUpdater.DEFAULT_FOLDER + "/imu/";
    private static final String[] COLUMNS = new String[]{
            "Time", "Log Status",
            "Acceleration X", "Acceleration Y", "Acceleration Z", //2 3 4
            "Gyroscope X", "Gyroscope Y", "Gyroscope Z", // 5 6 7
            "Quaternion W", "Quaternion X", "Quaternion Y", "Quaternion Z", //8 9 10 11
            "Roll", "Pitch", "Yaw", //12 13 14
            "Velocity", // 15
            "Latitude", "Longitude", //16 17
    };
    private static final String[] log = new String[COLUMNS.length];


    public ShellImuUpdater(boolean logToShell, boolean logToFile) {
        super("IMU", COLUMNS, DEFAULT_FOLDER, logToShell, logToFile);
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
            new YoloException("cannot log  With imu", e, ExceptionType.IMU)
                    .print();
        }
    }

    private void update(ArrayList<CanMessage> data) {
        for (CanMessage message : data) {
            Raw[] packets = new Parser(message).getParsedData();
            if (packets.length > 0) {
                //log[0] = Long.toString(message.getTime());

                update(packets);
            }
        }
    }

    private void update(Raw[] raw) {
        Imu imu = new Imu(raw);
        double[] data = imu.getImuData();
        log[0] = Double.toString(imu.getImuTime());
        if (imu.getImuType() == Type.ACCELERATION) {
            log[2] = Double.toString(data[0]);
            log[3] = Double.toString(data[1]);
            log[4] = Double.toString(data[2]);
            writeLog(log);
        } else if (imu.getImuType() == Type.GYRO) {
            log[5] = Double.toString(data[0]);
            log[6] = Double.toString(data[1]);
            log[7] = Double.toString(data[2]);
        } else if (imu.getImuType() == Type.QUATERNION) {
            log[8] = Double.toString(data[0]);
            log[9] = Double.toString(data[1]);
            log[10] = Double.toString(data[2]);
            log[11] = Double.toString(data[3]);
        } else if (imu.getImuType() == Type.ROLL_PITCH_YAW) {
            log[12] = Double.toString(data[0]);
            log[13] = Double.toString(data[1]);
            log[14] = Double.toString(data[3]);
        } else if (imu.getImuType() == Type.VELOCITY) {
            log[15] = Double.toString(data[0]);

        } else if (imu.getImuType() == Type.GPS_LATITUDE_LONGITUDE) {
            log[16] = Double.toString(data[0]);
            log[17] = Double.toString(data[1]);
        }
    }

}
