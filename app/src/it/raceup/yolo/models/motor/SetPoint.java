package it.raceup.yolo.models.motor;

import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;

import static it.raceup.yolo.models.data.Base.EPSILON;

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

    public void update(Raw data) {
        if (data.getType() == Type.SP_INVERTER_ON) {
            setInverterOn(data.getRaw() > EPSILON);
        } else if (data.getType() == Type.SP_DC_ON) {
            setDcOn(data.getRaw() > EPSILON);
        } else if (data.getType() == Type.SP_ENABLE) {
            setEnable(data.getRaw() > EPSILON);
        } else if (data.getType() == Type.SP_ERROR_RESET) {
            setErrorReset(data.getRaw() > EPSILON);
        } else if (data.getType() == Type.TARGET_VELOCITY) {
            setTargetVelocity(data.getRaw());
        } else if (data.getType() == Type.POS_TORQUE_LIMIT) {
            setPosTorqueLimit(data.getRaw());
        } else if (data.getType() == Type.NEG_TORQUE_LIMIT) {
            setNegTorqueLimit(data.getRaw());
        }
    }

    public double get(Type type) {
        if (type == Type.SP_INVERTER_ON) {
            return isInverterOn() ? 1 : 0;
        } else if (type == Type.SP_DC_ON) {
            return isDcOn() ? 1 : 0;
        } else if (type == Type.SP_ENABLE) {
            return isEnable() ? 1 : 0;
        } else if (type == Type.SP_ERROR_RESET) {
            return isErrorReset() ? 1 : 0;
        } else if (type == Type.TARGET_VELOCITY) {
            return getTargetVelocity();
        } else if (type == Type.POS_TORQUE_LIMIT) {
            return getPosTorqueLimit();
        } else if (type == Type.NEG_TORQUE_LIMIT) {
            return getNegTorqueLimit();
        }

        return 0;  // todo exception
    }
}

