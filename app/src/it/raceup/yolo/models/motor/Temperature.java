package it.raceup.yolo.models.motor;

import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;

public class Temperature {
    private double motor;
    private double inverter;
    private double igbt;
    private int errorInfo;

    public double getMotor() {
        return motor;
    }

    public void setMotor(double motor) {
        this.motor = motor;
    }

    public double getInverter() {
        return inverter;
    }

    public void setInverter(double inverter) {
        this.inverter = inverter;
    }

    public double getIgbt() {
        return igbt;
    }

    public void setIgbt(double igbt) {
        this.igbt = igbt;
    }

    public int getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(int errorInfo) {
        this.errorInfo = errorInfo;
    }

    public void update(Raw data) {
        if (data.getType() == Type.TEMPERATURE_MOTOR) {
            setMotor(data.getRaw());
        } else if (data.getType() == Type.TEMPERATURE_INVERTER) {
            setInverter(data.getRaw());
        } else if (data.getType() == Type.TEMPERATURE_IGBT) {
            setIgbt(data.getRaw());
        } else if (data.getType() == Type.ERROR_INFO) {
            setErrorInfo((int) data.getRaw());
        }
    }

    public double get(Type type) {
        if (type == Type.TEMPERATURE_MOTOR) {
            return getMotor();
        } else if (type == Type.TEMPERATURE_INVERTER) {
            return getInverter();
        } else if (type == Type.TEMPERATURE_IGBT) {
            return getIgbt();
        } else if (type == Type.ERROR_INFO) {
            return getErrorInfo();
        }

        return 0;  // todo exception
    }
}
