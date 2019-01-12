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
    private Type type;
    private double[] data;

    public Imu(Type type, double[] data) {
        checkImuType(type);
        this.type = type;
        this.data = data;
    }

    //default constructor
    public Imu() {
        type = null;
        data = new double[4];
    }


    public Imu(Raw[] raw){
        this();
        if (checkImuType(raw[0].getType())) {
            type = raw[0].getType();
            for (int i = 0; i < raw.length; i++) {
                data[i] = raw[i].getRaw();
            }
        }
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

    private void checkImuType(Raw[] data) {
        try {
            for (int i = 0; i < data.length; i++) {
                if (type != data[i].getType()) {
                    throw new YoloException("Wrong Imu type", ExceptionType.IMU);
                }
            }
        } catch (Exception e) {
            System.err.println("Wrong Imu type");
        }
    }


    public Type getImuType() {
        return type;
    }

    public double[] getImuData() {
        return data;
    }

    private void allToZero() {
        for (int i = 0; i < data.length; i++) {
            data[i] = 0;
        }
    }


    private void setImuData(Raw[] raw) {
        allToZero();
        if (checkImuType(raw[0].getType())) {
            type = raw[0].getType();
            for (int i = 0; i < raw.length; i++) {
                data[i] = raw[i].getRaw();
            }
        }
    }

    private void update(Raw[] raw) {
        //checkImuType(raw);
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
        if((type == Type.ACCELERATION) || (type == Type.ROLL_PITCH_YAW) || (type == Type.VELOCITY)) {
            notifyObservers(new Imu(type, data));
        }
    }

}
