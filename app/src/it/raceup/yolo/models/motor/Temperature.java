package it.raceup.yolo.models.motor;

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
}
