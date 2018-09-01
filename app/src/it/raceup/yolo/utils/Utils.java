package it.raceup.yolo.utils;

import java.io.PrintStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static String getTimeNow(String format) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(cal.getTime());
    }

    public static String convertTime(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static int getBit(byte content, int position) {
        return ((content >> position) & 1);
    }

    public static void printByteArray(PrintStream writer, byte[] data) {
        StringBuilder out = new StringBuilder("[");  // todo check speed
        for (byte b : data) {
            out.append(Byte.toString(b)).append(" ");
        }
        out.append("]");
        writer.print(out.toString());
    }
}
