package it.raceup.yolo.models.motor;

import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;

import static it.raceup.yolo.models.data.Raw.EPSILON;

public class Flags {
    private boolean systemReady, error, warning, quitDcOn, dcOn, quitInverterOn,
            inverterOn, derating;
    private double actualVelocity;
    private double torqueCurrent;
    private double magnetizingCurrent;

    public boolean isSystemReady() {
        return systemReady;
    }

    public void setSystemReady(boolean systemReady) {
        this.systemReady = systemReady;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean isWarning() {
        return warning;
    }

    public void setWarning(boolean warning) {
        this.warning = warning;
    }

    public boolean isQuitDcOn() {
        return quitDcOn;
    }

    public void setQuitDcOn(boolean quitDcOn) {
        this.quitDcOn = quitDcOn;
    }

    public boolean isDcOn() {
        return dcOn;
    }

    public void setDcOn(boolean dcOn) {
        this.dcOn = dcOn;
    }

    public boolean isQuitInverterOn() {
        return quitInverterOn;
    }

    public void setQuitInverterOn(boolean quitInvOn) {
        this.quitInverterOn = quitInvOn;
    }

    public boolean isInverterOn() {
        return inverterOn;
    }

    public void setInverterOn(boolean inverterOn) {
        this.inverterOn = inverterOn;
    }

    public boolean isDerating() {
        return derating;
    }

    public void setDerating(boolean derating) {
        this.derating = derating;
    }

    public double getActualVelocity() {
        return actualVelocity;
    }

    public void setActualVelocity(double actualVelocity) {
        this.actualVelocity = actualVelocity;
    }

    public double getTorqueCurrent() {
        return torqueCurrent;
    }

    public void setTorqueCurrent(double torqueCurrent) {
        this.torqueCurrent = torqueCurrent;
    }

    public double getMagnetizingCurrent() {
        return magnetizingCurrent;
    }

    public void setMagnetizingCurrent(double magnetizingCurrent) {
        this.magnetizingCurrent = magnetizingCurrent;
    }

    public void update(Raw data) {
        if (data.getType() == Type.SYSTEM_READY) {
            setSystemReady(data.getRaw() > EPSILON);
        } else if (data.getType() == Type.ERROR) {
            setError(data.getRaw() > EPSILON);
        } else if (data.getType() == Type.WARNING) {
            setWarning(data.getRaw() > EPSILON);
        } else if (data.getType() == Type.QUIT_DC_ON) {
            setQuitDcOn(data.getRaw() > EPSILON);
        } else if (data.getType() == Type.DC_ON) {
            setDcOn(data.getRaw() > EPSILON);
        } else if (data.getType() == Type.QUIT_INVERTER_ON) {
            setQuitInverterOn(data.getRaw() > EPSILON);
        } else if (data.getType() == Type.INVERTER_ON) {
            setInverterOn(data.getRaw() > EPSILON);
        } else if (data.getType() == Type.DERATING) {
            setDerating(data.getRaw() > EPSILON);
        } else if (data.getType() == Type.ACTUAL_VELOCITY) {
            setActualVelocity(data.getRaw());
        } else if (data.getType() == Type.TORQUE_CURRENT) {
            setTorqueCurrent(data.getRaw());
        } else if (data.getType() == Type.MAGNETIZING_CURRENT) {
            setMagnetizingCurrent(data.getRaw());
        }
    }
}
