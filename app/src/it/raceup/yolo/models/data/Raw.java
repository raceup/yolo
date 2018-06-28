package it.raceup.yolo.models.data;

import java.util.Arrays;

public class Raw {
    public static double EPSILON = 1e-8;
    public static Type[] TEMPERATURES = new Type[]{
            Type.TEMPERATURE_MOTOR,
            Type.TEMPERATURE_INVERTER,
            Type.TEMPERATURE_IGBT,
            Type.ERROR_INFO
    };
    public static Type[] SET_POINTS = new Type[]{
            Type.SP_INVERTER_ON,
            Type.SP_DC_ON,
            Type.SP_ENABLE,
            Type.SP_ERROR_RESET,
            Type.TARGET_VELOCITY,
            Type.POS_TORQUE_LIMIT,
            Type.NEG_TORQUE_LIMIT
    };
    public static Type[] FLAGS = new Type[]{
            Type.SYSTEM_READY,
            Type.ERROR,
            Type.WARNING,
            Type.QUIT_DC_ON,
            Type.DC_ON,
            Type.QUIT_INVERTER_ON,
            Type.INVERTER_ON,
            Type.DERATING,
            Type.ACTUAL_VELOCITY,
            Type.TORQUE_CURRENT,
            Type.MAGNETIZING_CURRENT
    };
    public Type type;
    public double raw;
    private int motor;

    public Raw(double raw, int motor, Type type) {
        this.type = type;
        this.raw = raw;
        this.motor = motor;
    }

    public Type getType() {
        return type;
    }

    public double getRaw() {
        return raw;
    }

    public int getMotor() {
        return motor;
    }

    public static boolean isTemperature(Type type) {
        return Arrays.asList(TEMPERATURES).contains(type);
    }

    public static boolean isSetPoint(Type type) {
        return Arrays.asList(SET_POINTS).contains(type);
    }

    public static boolean isFlag(Type type) {
        return Arrays.asList(FLAGS).contains(type);
    }


    public boolean isTemperature() {
        return isTemperature(getType());
    }

    public boolean isSetPoint() {
        return isSetPoint(getType());
    }

    public boolean isFlag() {
        return isFlag(getType());
    }
}
