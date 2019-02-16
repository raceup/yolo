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

//this is a model for steering wheel, throttle brake
public class Driver extends Observable implements Observer {

    private double time;
    private double[] throttleBrake;
    private double steeringWheel;
    private double frontSuspensionPotentiometer;
    private double rearSuspensionPotentiometer;
    private double[] brake_pressure; // FRONT REAR

    public Driver() {
        this.time = 0;
        throttleBrake = new double[2];
        frontSuspensionPotentiometer = 0;
        rearSuspensionPotentiometer = 0;
        brake_pressure = new double[2];
    }

    public Driver(Raw[] raw) {
        this();
        setDriverData(raw);
    }

    private Driver(double time, double[] throttleBrake, double steeringWheel, double frontSuspensionPotentiometer, double rearSuspensionPotentiometer, double[] brake_pressure) {
        this.time = time;
        this.throttleBrake = throttleBrake;
        this.steeringWheel = steeringWheel;
        this.frontSuspensionPotentiometer = frontSuspensionPotentiometer;
        this.rearSuspensionPotentiometer = rearSuspensionPotentiometer;
        this.brake_pressure = brake_pressure;
    }

    public void setDriverData(Raw[] raw) {
        setDriverTime(raw[0]);
        for (int i = 0; i < raw.length; i++) {
            switch (raw[i].getType()) {
                case THROTTLE: {
                    throttleBrake[0] = raw[i++].getRaw();
                    throttleBrake[1] = raw[i++].getRaw();
                    break;
                }
                case STEERINGWHEEL: {
                    steeringWheel = raw[i++].getRaw();
                    break;
                }
                case FRONT_SUSPENSION_POTENTIOMETER: {
                    frontSuspensionPotentiometer = raw[i++].getRaw();
                    break;
                }
                case REAR_SUSPENSION_POTENTIOMETER: {
                    rearSuspensionPotentiometer = raw[i++].getRaw();
                    break;
                }
                case BRAKE_PRESSURE: {
                    brake_pressure[0] = raw[i++].getRaw();
                    brake_pressure[1] = raw[i++].getRaw();
                }
            }
        }
    }

    public void setDriverTime(Raw raw) {
        time = raw.getTime();
    }

    public double[] getDriverData(Type type) {
        switch (type) {
            case THROTTLE:
                return throttleBrake;
            case STEERINGWHEEL:
                return new double[]{steeringWheel};
            case FRONT_SUSPENSION_POTENTIOMETER:
                return new double[]{frontSuspensionPotentiometer};
            case REAR_SUSPENSION_POTENTIOMETER:
                return new double[]{rearSuspensionPotentiometer};
            case BRAKE_PRESSURE:
                return brake_pressure;
        }
        return new double[]{0};
    }

    public double getDriverTime() {
        return time;
    }

    public String[] toStringArray() {
        String[] toRet = new String[8];
        toRet[0] = Double.toString(time);
        toRet[1] = Double.toString(throttleBrake[0]);
        toRet[2] = Double.toString(throttleBrake[1]);
        toRet[3] = Double.toString(steeringWheel);
        toRet[4] = Double.toString(frontSuspensionPotentiometer);
        toRet[5] = Double.toString(rearSuspensionPotentiometer);
        toRet[6] = Double.toString(brake_pressure[0]);
        toRet[7] = Double.toString(brake_pressure[1]);
        return toRet;
    }

    private boolean checkDriverType(Type type) {
        try {
            if (type == Type.STEERINGWHEEL ||
                    type == Type.THROTTLE ||
                    type == Type.BRAKE ||
                    type == Type.FRONT_SUSPENSION_POTENTIOMETER ||
                    type == Type.REAR_SUSPENSION_POTENTIOMETER
            )
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
        setDriverData(raw);
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
        notifyObservers(new Driver(time, throttleBrake, steeringWheel, frontSuspensionPotentiometer, rearSuspensionPotentiometer, brake_pressure));
    }

}