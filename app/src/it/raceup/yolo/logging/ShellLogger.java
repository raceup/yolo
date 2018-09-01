package it.raceup.yolo.logging;

public class ShellLogger extends StreamLogger {
    public ShellLogger() {
        super(System.out);
    }

    @Override
    public void logError(String message) {
        this.log(System.err, message);
    }
}
