package it.raceup.yolo.models.car;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.data.CanMessage;
import it.raceup.yolo.models.data.Parser;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.models.kvaser.message.FromKvaserMessage;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Imu extends Observable implements Observer {

    private double time;
    private double[] log_status;
    private double[] acceleration;
    private double[] gyro;
    private double[] roll_pitch_yaw;
    private double[] quaternion;
    private double velocity;
    private double[] gps;

    //default constructor
    public Imu() {
        log_status = new double[3];
        acceleration = new double[3];
        gyro = new double[3];
        roll_pitch_yaw = new double[3];
        quaternion = new double[4];
        gps = new double[2];
    }

    public Imu(double time, double[] log_status, double[] acceleration, double[] gyro, double[] roll_pitch_yaw, double[] quaternion, double velocity, double[] gps) {
        this.time = time;
        this.log_status = log_status;
        this.acceleration = acceleration;
        this.gyro = gyro;
        this.roll_pitch_yaw = roll_pitch_yaw;
        this.quaternion = quaternion;
        this.velocity = 0;
        this.gps = gps;
    }

    public Imu(Raw[] raw) {
        this();
        setImuData(raw);
    }

    private void setImuData(Raw[] raw) {
        setImuTime(raw[0]);
        for (int i = 0; i < raw.length; i++) {
            switch (raw[i].getType()) {
                case LOG_STATUS: {
                    log_status[0] = raw[i++].getRaw();
                    log_status[1] = raw[i++].getRaw();
                    log_status[2] = raw[i++].getRaw();
                    break;
                }
                case ACCELERATION: {
                    acceleration[0] = raw[i++].getRaw();
                    acceleration[1] = raw[i++].getRaw();
                    acceleration[2] = raw[i++].getRaw();
                    break;
                }
                case GYRO: {
                    gyro[0] = raw[i++].getRaw();
                    gyro[1] = raw[i++].getRaw();
                    gyro[2] = raw[i++].getRaw();
                    break;
                }
                case ROLL_PITCH_YAW: {
                    roll_pitch_yaw[0] = raw[i++].getRaw();
                    roll_pitch_yaw[1] = raw[i++].getRaw();
                    roll_pitch_yaw[2] = raw[i++].getRaw();
                    break;
                }
                case QUATERNION: {
                    gyro[0] = raw[i++].getRaw();
                    gyro[1] = raw[i++].getRaw();
                    gyro[2] = raw[i++].getRaw();
                    break;
                }
                case VELOCITY: {
                    velocity = raw[i++].getRaw();
                    break;
                }
                case GPS_LATITUDE_LONGITUDE: {
                    gps[0] = raw[i++].getRaw();
                    gps[1] = raw[i++].getRaw();
                    break;
                }
            }
        }
    }

    public void setImuTime(Raw raw) {
        time = raw.getTime();
    }

    public double getImuTime() {
        return time;
    }

    public double[] getImuData(Type type) {
        switch (type) {
            case LOG_STATUS:
                return log_status;
            case ACCELERATION:
                return acceleration;
            case ROLL_PITCH_YAW:
                return roll_pitch_yaw;
            case QUATERNION:
                return quaternion;
            case VELOCITY:
                return new double[]{velocity};
            case GPS_LATITUDE_LONGITUDE:
                return gps;
        }
        return new double[]{0};
    }

    public String[] toStringArray(){
        int index = 0;
        String[] toRet = new String[18]; //sums
        toRet[index] = Double.toString(time);
        toRet[index++] = Double.toString(log_status[0]);
        for (int i = 0; i < acceleration.length; i++){
            toRet[index++] = Double.toString(acceleration[i]);
        }
        for(int i = 0; i < gyro.length; i++){
            toRet[index++] = Double.toString(gyro[i]);
        }
        for(int i = 0; i < roll_pitch_yaw.length; i++){
            toRet[index++] = Double.toString(roll_pitch_yaw[i]);
        }
        for(int i = 0; i < quaternion.length; i++){
            toRet[index++] = Double.toString(quaternion[i]);
        }
        toRet[index++] = Double.toString(velocity);
        for(int i = 0; i < gps.length; i++){
            toRet[index++] = Double.toString(gps[i]);
        }
        return toRet;
    }

    public boolean checkImuType(Type type) {
        try {
            if (type == Type.LOG_STATUS || type ==
                    Type.ACCELERATION || type ==
                    Type.GYRO || type ==
                    Type.QUATERNION || type ==
                    Type.ROLL_PITCH_YAW || type ==
                    Type.VELOCITY || type ==
                    Type.GPS_LATITUDE_LONGITUDE)
                return true;
            else {
                return false;

            }
        } catch (Exception e) {
            new YoloException("Wrong Imu type", e, ExceptionType.IMU)
                    .print();
        }
        return false;
    }

    private void update(Raw[] raw) {
        setImuData(raw);
    }

    private void update(ArrayList<CanMessage> data) {
        for (CanMessage message : data) {
            Raw[] packets = new Parser(message).getParsedData();
            if (packets.length > 0) {
                update(packets);
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            FromKvaserMessage message = new FromKvaserMessage(o);
            ArrayList<CanMessage> messages = message.getAsCanMessages();
            if (messages != null) {

                update(messages);
                triggerObservers();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new YoloException("cannot update Imu car", e, ExceptionType.IMU).print();
        }

    }

    private void triggerObservers() {
        setChanged();
        notifyObservers(new Imu(time, log_status, acceleration, gyro, roll_pitch_yaw, quaternion, velocity, gps));
    }
}
