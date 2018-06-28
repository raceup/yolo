package it.raceup.yolo.models.motor;

public class SetPoint {
    private boolean inverterOn, dcOn, enable, errorReset;
    private double targetVelocity, posTorqueLimit, negTorqueLimit;

    public boolean isInverterOn() {
        return inverterOn;
    }

    public void setInverterOn(boolean inverterOn) {
        this.inverterOn = inverterOn;
    }

    public boolean isDcOn() {
        return dcOn;
    }

    public void setDcOn(boolean dcOn) {
        this.dcOn = dcOn;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isErrorReset() {
        return errorReset;
    }

    public void setErrorReset(boolean errorReset) {
        this.errorReset = errorReset;
    }

    public double getTargetVelocity() {
        return targetVelocity;
    }

    public void setTargetVelocity(double targetVelocity) {
        this.targetVelocity = targetVelocity;
    }

    public double getPosTorqueLimit() {
        return posTorqueLimit;
    }

    public void setPosTorqueLimit(double posTorqueLimit) {
        this.posTorqueLimit = posTorqueLimit;
    }

    public double getNegTorqueLimit() {
        return negTorqueLimit;
    }

    public void setNegTorqueLimit(double negTorqueLimit) {
        this.negTorqueLimit = negTorqueLimit;
    }
}

