package it.raceup.yolo.models.data;

import java.util.Arrays;
import java.util.Random;

public class Raw {
    public static final Type[] TEMPERATURES = new Type[]{
            Type.TEMPERATURE_MOTOR,
            Type.TEMPERATURE_INVERTER,
            Type.TEMPERATURE_IGBT,
            Type.ERROR_INFO
    };
    public static final Type[] SET_POINTS = new Type[]{
            Type.SP_INVERTER_ON,
            Type.SP_DC_ON,
            Type.SP_ENABLE,
            Type.SP_ERROR_RESET,
            Type.TARGET_VELOCITY,
            Type.POS_TORQUE_LIMIT,
            Type.NEG_TORQUE_LIMIT
    };
    public static final Type[] FLAGS = new Type[]{
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

    public Raw(int id, byte[] data) {
        this(
                parseRawValue(data), parseMotorId(id), parseType(data)
        );
    }

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

    public static int parseMotorId(int id) {
        return new Random().nextInt(4);  // todo parse
    }

    public static double parseRawValue(byte[] data) {
        return new Random().nextInt(10) / 10.0;  // todo parse
    }

    public static Type parseType(byte[] data) {
        // todo parse
        Type[] all = Type.values();  // get the array
        int randomNum = new Random().nextInt(all.length);  // random int
        return all[randomNum];  // get the random obj
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
