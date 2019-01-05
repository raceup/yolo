package it.raceup.yolo.models.car;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;

import java.util.Observable;
import java.util.Observer;

public class Imu extends Observable implements Observer {
    private final Type type;
    private final double[] data;

    public Imu(Type type, double[] data) {
        checkImuType(type);
        this.type = type;
        this.data = data;
    }
    //default constructor
    public Imu(){
        type = null;
        data = null;
    }

    public void checkImuType(Type type) {
    try {
        if (type == Type.LOG_STATUS || type ==
                Type.ACCELERATION || type ==
                Type.GYRO || type ==
                Type.QUATERNION || type ==
                Type.ROLL_PITCH_YAW || type ==
                Type.VELOCITY || type ==
                Type.GPS_LATITUDE_LONGITUDE)
            return;
        else {
            throw new Exception();

        }
    }catch(Exception e){new YoloException("Wrong Imu type", e, ExceptionType.IMU)
            .print();
        }
    }

    public Type getImuType() {
        return type;
    }

    public double[] getImuData() {
        return data;
    }

    private void setImuData(Raw[] raw) {
        for (int i = 0; i < raw.length; i++) {
            data[i] = raw[i].getRaw();
        }
    }

    public void update(Raw[] raw) {
        try {
            for (int i = 0; i < raw.length; i++) {
                if (raw[i].getType() != type) {
                    throw new Exception();
                }
            }
        }catch(Exception e){new YoloException("Trying to update Imu values of " + raw[0].getType() + " but Imu type: " + type + " was found", e, ExceptionType.IMU)
                .print();
        }
        setImuData(raw);
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            if (arg != null) {
                update((Raw[]) arg);
                triggerObservers();
            }
        } catch (Exception e) {
            new YoloException("cannot update Imu car", e, ExceptionType.KVASER)
                    .print();
        }

    }

    private void triggerObservers() {
        setChanged();
        notifyObservers();
    }
}
