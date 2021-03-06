package it.raceup.yolo.logging.loggers;

public class ShellLogger implements Logger {
    private final StreamLogger out;
    private final StreamLogger err;

    public ShellLogger() {
        this("SHELL LOGGER");
    }

    public ShellLogger(String tag) {
        out = new StreamLogger(tag, System.out);
        err = new StreamLogger(tag, System.err);
    }

    @Override
    public void log(String message) {
        out.log(out.getMessage(message, true, true));
    }

    @Override
    public void log(Exception e) {
        err.log(e);
    }
}
