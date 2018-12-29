package it.raceup.yolo.models.data;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.utils.Misc;

import java.util.ArrayList;
import java.util.Arrays;


public class Parser {
    private static final int index_diff_1[] = new int[]{
            0x283, 0x284, 0x287, 0x288
    };
    private static final int index_diff_2[] = new int[]{
            0x285, 0x286, 0x289, 0x28A
    };
    private static final double TORQUE_CURRENT = 107.2 / 16384;
    private final int id;
    private final byte[] data;
    private final ArrayList<Raw> parsedData = new ArrayList<>();
    private int motorId;
    private int LOG_STATUS = 768;
    private int ACCELERATION = 801;
    private int GYRO = 802;
    private int QUATERNION = 817;
    private int ROLL_PITCH_YAW = 818;
    private int VELOCITY = 865;
    private int GPS_LATITUDE_LONGITUDE = 885;
    //constants to select correct byte useful just to prevent hardcoding
    private short FIRST_BYTE = 0;
    private short THIRD_BYTE = 2;
    private short FIFTH_BYTE = 4;
    private short SEVENTH_BYTE = 6;

    public Parser(int id, byte[] data) {
        this.id = id;
        this.data = data;

        parse();
    }

    public Parser(CanMessage message) {
        this(message.getId(), message.data);
    }

    private void parse() {
        motorId = parseMotorId();

        try {
            parseValues();
        } catch (Exception e) {
            new YoloException("cannot parse " + Arrays.toString(data), e,
                    ExceptionType.KVASER).print();
        }
    }

    private int parseMotorId() {
        for (int i = 0; i < 4; i++) {
            if (id == index_diff_1[i] || id == index_diff_2[i]) {
                return i;
            }
        }

        return 0;
    }

    public int getMotorId() {
        return motorId;
    }

    public void parseValues() {
        if (getValueType() == 1) {
            readMotor1();
        } else if(getValueType() ==2) {
            readMotor2();
        } else {
            readIMU();
        }
    }

    private void readMotor1() {
        parsedData.add(
                new Raw(
                        Misc.getBit(data[1], 0),
                        getMotorId(),
                        Type.SYSTEM_READY
                )
        );
        parsedData.add(
                new Raw(
                        Misc.getBit(data[1], 1),
                        getMotorId(),
                        Type.ERROR
                )
        );
        parsedData.add(
                new Raw(
                        Misc.getBit(data[1], 2),
                        getMotorId(),
                        Type.WARNING
                )
        );
        parsedData.add(
                new Raw(
                        Misc.getBit(data[1], 3),
                        getMotorId(),
                        Type.QUIT_DC_ON
                )
        );
        parsedData.add(
                new Raw(
                        Misc.getBit(data[1], 4),
                        getMotorId(),
                        Type.DC_ON
                )
        );
        parsedData.add(
                new Raw(
                        Misc.getBit(data[1], 5),
                        getMotorId(),
                        Type.QUIT_INVERTER_ON
                )
        );
        parsedData.add(
                new Raw(
                        Misc.getBit(data[1], 6),
                        getMotorId(),
                        Type.INVERTER_ON
                )
        );
        parsedData.add(
                new Raw(
                        Misc.getBit(data[1], 7),
                        getMotorId(),
                        Type.DERATING
                )
        );
        parsedData.add(
                new Raw(
                        data[2] | data[3] << 8,
                        getMotorId(),
                        Type.ACTUAL_VELOCITY
                )
        );
        parsedData.add(
                new Raw(
                        (data[4] | data[5] << 8) * TORQUE_CURRENT,
                        getMotorId(),
                        Type.TORQUE_CURRENT
                )
        );
        parsedData.add(
                new Raw(
                        data[6] | data[7] << 8,
                        getMotorId(),
                        Type.MAGNETIZING_CURRENT
                )
        );
    }

    private void readMotor2() {
        parsedData.add(
                new Raw(
                        (data[0] | data[1] << 8) / 10,
                        getMotorId(),
                        Type.TEMPERATURE_MOTOR
                )
        );
        parsedData.add(
                new Raw(
                        (data[2] | data[3] << 8) / 10,
                        getMotorId(),
                        Type.TEMPERATURE_INVERTER
                )
        );
        parsedData.add(
                new Raw(
                        (data[4] | data[5] << 8) / 10,
                        getMotorId(),
                        Type.TEMPERATURE_IGBT
                )
        );
        parsedData.add(
                new Raw(
                        data[6] | data[7] << 8,
                        getMotorId(),
                        Type.ERROR_INFO
                )
        );
    }

    public int getValueType() {
        for (int i = 0; i < 4; i++) {
            if (id == index_diff_1[i]) {
                return 1;
            }

            if (id == index_diff_2[i]) {
                return 2;
            }
        }

        return 0;
    }

    public Raw[] getParsedData() {
        return parsedData.toArray(new Raw[parsedData.size()]);
    }

    private static int hToNInt(byte[] datas, int index) {
        return (int) (((datas[index] & 0xFF) << 24) | ((datas[index + 1] & 0xFF) << 16) | ((datas[index + 2] & 0xFF) << 8) | ((datas[index + 3] & 0xFF)));
    }
    private static short hToNShort(byte[] datas, int index) {
        return (short) (((datas[index] & 0xFF) << 8) | ((datas[index + 1]) & 0xFF));
    }

    public void readIMU() {
        try {

            //same code for every status:
            // first read bytes as int/short with methods hToNInt hToNShort
            // second 2's complement and cast to short if needed
            // third add result to row object

            int len = data.length;
            int one = 1;
            byte[] datasBigEndian = new byte[len];
            for (int i = 0; i < len; i++) {
                datasBigEndian[i] = data[len - i - 1];
            }
            if (id == LOG_STATUS) {
                parsedData.add(new Raw(((-1) * ((~hToNInt(datasBigEndian, FIFTH_BYTE) + one))), Type.LOG_STATUS));
                parsedData.add(new Raw(((-1) * ((~hToNShort(datasBigEndian, THIRD_BYTE) + one))), Type.LOG_STATUS));
                parsedData.add(new Raw(((-1) * ((~hToNShort(datasBigEndian, FIFTH_BYTE) + one))), Type.LOG_STATUS));
            }
            //used names are for values of 289 packet but it's the same for 290 and 306
            else if (id == ACCELERATION) {
                parsedData.add(new Raw(((-1) * ((~hToNShort(datasBigEndian, FIFTH_BYTE) + one))), Type.ACCELERATION));
                parsedData.add(new Raw(((-1) * ((~hToNShort(datasBigEndian, THIRD_BYTE) + one))), Type.ACCELERATION));
                parsedData.add(new Raw(((-1) * ((~hToNShort(datasBigEndian, FIRST_BYTE) + one))), Type.ACCELERATION));
            }else if (id == GYRO) {
                parsedData.add(new Raw(((-1) * ((~hToNShort(datasBigEndian, FIFTH_BYTE) + one))), Type.GYRO));
                parsedData.add(new Raw(((-1) * ((~hToNShort(datasBigEndian, THIRD_BYTE) + one))), Type.GYRO));
                parsedData.add(new Raw(((-1) * ((~hToNShort(datasBigEndian, FIRST_BYTE) + one))), Type.GYRO));
            }else if (id == ROLL_PITCH_YAW) {
                parsedData.add(new Raw(((-1) * ((~hToNShort(datasBigEndian, FIFTH_BYTE) + one))), Type.ROLL_PITCH_YAW));
                parsedData.add(new Raw(((-1) * ((~hToNShort(datasBigEndian, THIRD_BYTE) + one))), Type.ROLL_PITCH_YAW));
                parsedData.add(new Raw(((-1) * ((~hToNShort(datasBigEndian, FIRST_BYTE) + one))), Type.ROLL_PITCH_YAW));
            }else if (id == QUATERNION) {
                parsedData.add(new Raw(((-1) * ((~hToNShort(datasBigEndian, SEVENTH_BYTE) + one))), Type.QUATERNION));
                parsedData.add(new Raw(((-1) * ((~hToNShort(datasBigEndian, FIFTH_BYTE) + one))), Type.QUATERNION));
                parsedData.add(new Raw(((-1) * ((~hToNShort(datasBigEndian, THIRD_BYTE) + one))), Type.QUATERNION));
                parsedData.add(new Raw(((-1) * ((~hToNShort(datasBigEndian, FIRST_BYTE) + one))), Type.QUATERNION));
            } else if (id == VELOCITY) {
                parsedData.add(new Raw(((-1) * ((~hToNShort(datasBigEndian, FIRST_BYTE) + one))), Type.VELOCITY));
            } else if (id == GPS_LATITUDE_LONGITUDE) {
                parsedData.add(new Raw(((-1) * ((~hToNShort(datasBigEndian, FIFTH_BYTE) + one))), Type.GPS_LATITUDE_LONGITUDE));
                parsedData.add(new Raw(((-1) * ((~hToNShort(datasBigEndian, FIRST_BYTE) + one))), Type.GPS_LATITUDE_LONGITUDE));
            } else {
                System.err.println("404: ID not found ->" + id);
            }
        } catch (Exception e) {
            new YoloException("wrong input for parser", e, ExceptionType
                    .KVASER).print();
        }
    }
}
