package it.raceup.yolo.error;

public class YoloException extends Exception {
    private static final String errorFormatter = "%-30s";
    private final String message;
    private final ExceptionType type;

    public YoloException(Exception e) {
        this(e, ExceptionType.UNKNOWN);
    }

    public YoloException(Exception e, ExceptionType type) {
        this(e.toString(), type);
    }

    public YoloException(String message, Exception e, ExceptionType type) {
        this(message + "\n\t" + e.toString(), type);
    }

    public YoloException(String message, ExceptionType type) {
        this.message = message;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format(
                errorFormatter, "(exception of type " + type.toString() + ")"
        ) + " -- " + message;
    }

    public void print() {
        System.err.println(toString());
    }
}
