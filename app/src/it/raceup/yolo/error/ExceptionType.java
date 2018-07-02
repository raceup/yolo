package it.raceup.yolo.error;

public enum ExceptionType {
    CANLIB("Canlib"),
    KVASER("Kvaser"),
    NULL("Null"),
    UNKNOWN("Unknown");

    private final String name;

    ExceptionType(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
