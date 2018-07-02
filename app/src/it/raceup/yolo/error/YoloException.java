package it.raceup.yolo.error;

public class YoloException extends Exception {
    private String message;
    private ExceptionType type;

    public YoloException(String message, ExceptionType type) {
        this.message = message;
        this.type = type;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
