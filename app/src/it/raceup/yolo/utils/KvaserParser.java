package it.raceup.yolo.utils;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import java.util.ArrayList;

public class KvaserParser {
    /**
     * This method takes a byte array and starting from a given index performe the conversion from byte to int.
     * Starting from a given index it takes every byte masking the other and shift the most significant to the left and so on.
     * <p>
     * datas byte[] array that contain bytes to be convert in int
     * index int    index where wanted int starts from
     */
    private static int hToNInt(byte[] datas, int index) {
        return (int) (((datas[index] & 0xFF) << 24) | ((datas[index + 1] & 0xFF) << 16) | ((datas[index + 2] & 0xFF) << 8) | ((datas[index + 3] & 0xFF)));
    }

    /**
     * Like previous method but convert byte in short
     * <p>
     * datas byte[] array that contain bytes to be convert in short
     * index int    index where wanted short starts from
     */
    private static short hToNShort(byte[] datas, int index) {
        return (short) (((datas[index] & 0xFF) << 8) | ((datas[index + 1]) & 0xFF));
    }

    /**
     * return       ArrayList that contain value of the packet ordered as the paper on Ellipse writes
     * ID int      identifier of the packet
     * datas byte[] data to convert
     */
    public static ArrayList<Integer> DataInterpreter(int ID, byte[] datas) {
        //some constants of the value of ID
        int LOG_STATUS = 768;
        int ACCELERATION = 801;
        int GYRO = 802;
        int QUATERNION = 817;
        int ROLL_PITCH_YAW = 818;
        int VELOCITY = 865;
        int GPS_LATITUDE_LONGITUDE = 885;
        //constants to select correct byte useful just to prevent hardcoding
        short FIRST_BYTE = 0;
        short THIRD_BYTE = 2;
        short FIFTH_BYTE = 4;
        short SEVENTH_BYTE = 6;
        ArrayList<Integer> values = new ArrayList<Integer>();

        int len = datas.length;
        int one = 1;
        byte[] datasBigEndian = new byte[len];
        for (int i = 0; i < len; i++) {
            datasBigEndian[i] = datas[len - i - 1];
        }
        try {
            //same code for every status:
            // first read bytes as int/short with methods hToNInt hToNShort
            // second 2's complement and cast to short if needed
            // third add result to ArrayList values
            if (ID == LOG_STATUS) {
                values.add((-1)* ((~hToNInt(datasBigEndian, FIFTH_BYTE) + one)));
                values.add((-1)* (short) ((~hToNShort(datasBigEndian, THIRD_BYTE) + one)));
                values.add((-1)* (short) ((~hToNShort(datasBigEndian, FIRST_BYTE) + one)));
                return values;
            }
            //used names are for values of 289 packet but it's the same for 290 and 306
            else if (ID == ACCELERATION | ID == GYRO | ID == ROLL_PITCH_YAW) {
                values.add((-1) * (short) ((~hToNShort(datasBigEndian, FIFTH_BYTE) + one)));
                values.add((-1)* (short) ((~hToNShort(datasBigEndian, THIRD_BYTE) + one)));
                values.add((-1)* (short) ((~hToNShort(datasBigEndian, FIRST_BYTE) + one)));
                return values;
            } else if (ID == QUATERNION) {
                values.add((-1)* (short) ((~hToNShort(datasBigEndian, SEVENTH_BYTE) + one)));
                values.add((-1)* (short) ((~hToNShort(datasBigEndian, FIFTH_BYTE) + one)));
                values.add((-1)* (short) ((~hToNShort(datasBigEndian, THIRD_BYTE) + one)));
                values.add((-1)* (short) ((~hToNShort(datasBigEndian, FIRST_BYTE) + one)));
                return values;
            } else if (ID == VELOCITY) {
                values.add((-1)* ((~hToNInt(datasBigEndian, FIRST_BYTE) + one)));
                return values;
            } else if (ID == GPS_LATITUDE_LONGITUDE) {
                values.add((-1)* ((~hToNInt(datasBigEndian, FIFTH_BYTE) + one)));
                values.add((-1)* ((~hToNInt(datasBigEndian, FIRST_BYTE) + one)));
                return values;
            } else {
                System.err.println("404: ID not found ->" + ID);
            }
        } catch (Exception e) {
            new YoloException("wrong input for parser", e, ExceptionType
                    .KVASER).print();
        }
        return values;
    }
}

