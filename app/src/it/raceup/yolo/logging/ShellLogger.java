package it.raceup.yolo.logging;

public class ShellLogger extends StreamLogger {
    public ShellLogger() {
        this("SHELL LOGGER");
    }

    public ShellLogger(String tag) {
        super(System.out);
        TAG = tag;
    }

    @Override
    public void logError(String message) {
        this.log(System.err, message);
    }
}
