package it.raceup.yolo.error;

public enum ExceptionType {
    CANLIB("Canlib"),
    APP("App"),
    KVASER("CableKvaser"),
    UNKNOWN("Unknown"),
    MODEL("Model"),
    VIEW("View"),
    CONTROLLER("Controller"),
    LOGGING("Logging");

    private final String name;

    ExceptionType(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        // check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
