package it.raceup.yolo.utils;

import java.util.ArrayList;

public class Parser {
    /**
     * This method takes a byte array and starting from a given index performe the conversion from byte to int.
     * Starting from a given index it takes every byte masking the other and shift the most significant to the left and so on.
     *
     * datas byte[] array that contain bytes to be convert in int
     * index int    index where wanted int starts from
     */
    private static int hToNInt(byte[] datas, int index){
        return (int)(((datas[index] & 0xFF) << 24) | ((datas[index+1] & 0xFF) << 16) | ((datas[index+2] & 0xFF) << 8) | ((datas[index+3] & 0xFF)));
    }
    /**
     * Like previous method but convert byte in short
     *
     * datas byte[] array that contain bytes to be convert in short
     * index int    index where wanted short starts from
     */
    private static short hToNShort(byte[] datas, int index){
        return (short)(((datas[index] & 0xFF) << 8) | ((datas[index+1]) & 0xFF));
    }
    /**
     * return       ArrayList that contain value of the packet ordered as the paper on Ellipse writes
     * DLC int      identifier of the packet
     * datas byte[] data to convert
     */

    public static ArrayList<Integer> DataInterpreter(int DLC,  byte[] datas){
        //some costants of the value of DLC
        int LOG_STATUS = 256;
        int ACCELERATION = 289;
        int GYRO = 290;
        int QUATERNION = 305;
        int ROLL_PITCH_YAW = 306;
        int VELOCITY = 353;
        int GPS_LATITUDE_LONGITUDE = 373;
        //constants to select correct byte usefull just to prevent hardcoding
        short FIRST_BYTE = 0;
        short THIRD_BYTE = 2;
        short FIFTH_BYTE = 4;
        short SEVENTH_BYTE = 6;
        ArrayList<Integer> values = new ArrayList<Integer>();

        int len = datas.length;
        int one = 1;
        byte[] datasBigEndian = new byte[len];
        for(int i = 0;  i < len; i++){
            datasBigEndian[i] = datas[len-i-1];
        }

        if(DLC == LOG_STATUS){
            short clock_status = hToNShort(datasBigEndian, FIRST_BYTE);
            clock_status = (short)((~clock_status+one));
            short general_status = hToNShort(datasBigEndian, THIRD_BYTE);
            general_status = (short)((~general_status+one));
            int time_stamp = hToNInt(datasBigEndian, FIFTH_BYTE);
            time_stamp = (~time_stamp+one);
            values.add(time_stamp);
            values.add((int)general_status);
            values.add((int)clock_status);
            return values;
        }
        else if(DLC == ACCELERATION){
            short acceleration_z = hToNShort(datasBigEndian, FIRST_BYTE);
            acceleration_z = (short)((~acceleration_z+one));
            short acceleration_y = hToNShort(datasBigEndian, THIRD_BYTE);
            acceleration_y = (short)((~acceleration_y+one));
            short acceleration_x = hToNShort(datasBigEndian, FIFTH_BYTE);
            acceleration_x = (short)((~acceleration_x+one));
            values.add((int)acceleration_x);
            values.add((int)acceleration_y);
            values.add((int)acceleration_z);
            return values;
        }
        else if(DLC == GYRO){
            short gyro_z = hToNShort(datasBigEndian, FIRST_BYTE);
            gyro_z = (short)((~gyro_z+one));
            short gyro_y = hToNShort(datasBigEndian, THIRD_BYTE);
            gyro_y = (short)((~gyro_y+one));
            short gyro_x = hToNShort(datasBigEndian, FIFTH_BYTE);
            gyro_x = (short)((~gyro_x+one));
            values.add((int)gyro_x);
            values.add((int)gyro_y);
            values.add((int)gyro_z);
            return values;
        }
        else if(DLC == QUATERNION){
            short q3 = hToNShort(datasBigEndian, FIRST_BYTE);
            q3 = (short)((~q3+one));
            short q2 = hToNShort(datasBigEndian, THIRD_BYTE);
            q2 = (short)((~q2+one));
            short q1 = hToNShort(datasBigEndian, FIFTH_BYTE);
            q1 = (short)((~q1+one));
            short q0 = hToNShort(datasBigEndian, SEVENTH_BYTE);
            q0 = (short)((~q0+one));
            values.add((int)q0);
            values.add((int)q1);
            values.add((int)q2);
            values.add((int)q3);
            return values;
        }
        else if(DLC == ROLL_PITCH_YAW){
            short yaw = hToNShort(datasBigEndian, FIRST_BYTE);
            yaw = (short)((~yaw+one));
            short pitch = hToNShort(datasBigEndian, THIRD_BYTE);
            pitch = (short)((~pitch+one));
            short roll = hToNShort(datasBigEndian, FIFTH_BYTE);
            roll = (short)((~roll+one));
            values.add((int)roll);
            values.add((int)pitch);
            values.add((int)yaw);
            return values;
        }
        else if(DLC == VELOCITY){
            int roll = hToNInt(datasBigEndian, FIRST_BYTE);
            roll = ((~roll+one));
            values.add(roll);
            return values;
        }
        else if(DLC == GPS_LATITUDE_LONGITUDE){
            int longitude = hToNInt(datasBigEndian, FIRST_BYTE);
            longitude = ((~longitude+one));
            int latitude = hToNInt(datasBigEndian, FIFTH_BYTE);
            latitude = ((~latitude+one));
            values.add(latitude);
            values.add(longitude);
            return values;
        }
        else{
            System.err.println("404: DLC not found ->" + DLC);
        }
        return values;
    }
}

