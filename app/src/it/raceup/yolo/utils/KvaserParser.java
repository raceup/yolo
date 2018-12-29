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
            if (ID == LOG_STATUS) {
                short clock_status = hToNShort(datasBigEndian, FIRST_BYTE);
                clock_status = (short) ((~clock_status + one));
                short general_status = hToNShort(datasBigEndian, THIRD_BYTE);
                general_status = (short) ((~general_status + one));
                int time_stamp = hToNInt(datasBigEndian, FIFTH_BYTE);
                time_stamp = (~time_stamp + one);
                values.add(time_stamp);
                values.add((int) general_status);
                values.add((int) clock_status);
                return values;
            }
            //used names are for values of 289 packet but it's the same for 290 and 306
            else if (ID == ACCELERATION | ID == GYRO | ID == ROLL_PITCH_YAW) {
                short acceleration_z = hToNShort(datasBigEndian, FIRST_BYTE);
                acceleration_z = (short) ((~acceleration_z + one));
                short acceleration_y = hToNShort(datasBigEndian, THIRD_BYTE);
                acceleration_y = (short) ((~acceleration_y + one));
                short acceleration_x = hToNShort(datasBigEndian, FIFTH_BYTE);
                acceleration_x = (short) ((~acceleration_x + one));
                values.add((int) acceleration_x);
                values.add((int) acceleration_y);
                values.add((int) acceleration_z);
                return values;
            } else if (ID == QUATERNION) {
                short q3 = hToNShort(datasBigEndian, FIRST_BYTE);
                q3 = (short) ((~q3 + one));
                short q2 = hToNShort(datasBigEndian, THIRD_BYTE);
                q2 = (short) ((~q2 + one));
                short q1 = hToNShort(datasBigEndian, FIFTH_BYTE);
                q1 = (short) ((~q1 + one));
                short q0 = hToNShort(datasBigEndian, SEVENTH_BYTE);
                q0 = (short) ((~q0 + one));
                values.add((int) q0);
                values.add((int) q1);
                values.add((int) q2);
                values.add((int) q3);
                return values;
            } else if (ID == VELOCITY) {
                int roll = hToNInt(datasBigEndian, FIRST_BYTE);
                roll = ((~roll + one));
                values.add(roll);
                return values;
            } else if (ID == GPS_LATITUDE_LONGITUDE) {
                int longitude = hToNInt(datasBigEndian, FIRST_BYTE);
                longitude = ((~longitude + one));
                int latitude = hToNInt(datasBigEndian, FIFTH_BYTE);
                latitude = ((~latitude + one));
                values.add(latitude);
                values.add(longitude);
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

