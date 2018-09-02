package it.raceup.yolo.logging;

public class ShellLogger implements Logger {
    protected static String TAG = "STREAM LOGGER";
    private StreamLogger out;
    private StreamLogger err;

    public ShellLogger() {
        this("SHELL LOGGER");
    }

    public ShellLogger(String tag) {
        TAG = tag;
        out = new StreamLogger(System.out);
        err = new StreamLogger(System.err);
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
