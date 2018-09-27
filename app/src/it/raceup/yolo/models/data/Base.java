package it.raceup.yolo.models.data;

public class Base {
    public static final double EPSILON = 1e-8;
    public static final String DEGREES = " °";
    public static final String DNF = "DNF";
    private static final String DECIMAL_FORMAT_3 = "%.3f";

    public static String getAsString(int value) {
        return Integer.toString(value);
    }

    public static String getAsString(double value) {
        return String.format(DECIMAL_FORMAT_3, value);
    }

    public static String forceIntAsString(double value) {
        return getAsString((int) value);
    }
}
