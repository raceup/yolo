package it.raceup.yolo.models.motor;

public class Flags {
    private boolean systemReady, error, warning, quitDcOn, dcOn, quitInvOn,
            invOn, derating;
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

    public boolean isQuitInvOn() {
        return quitInvOn;
    }

    public void setQuitInvOn(boolean quitInvOn) {
        this.quitInvOn = quitInvOn;
    }

    public boolean isInvOn() {
        return invOn;
    }

    public void setInvOn(boolean invOn) {
        this.invOn = invOn;
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
}
