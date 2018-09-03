package it.raceup.yolo.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    private static final String PRETTY_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getTimeNow() {
        return getTimeNow(PRETTY_DATE_FORMAT);
    }

    public static String getTimeNow(String format) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(cal.getTime());
    }

    public static String convertTime(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat(PRETTY_DATE_FORMAT);
        return format.format(date);
    }

    public static int getBit(byte content, int position) {
        return ((content >> position) & 1);
    }

    public static String getLineSeparator(String line) {
        StringBuilder separator = new StringBuilder("");
        int length = line.length();
        for (int i = 0; i < length; i++) {
            separator.append("-");
        }
        return separator.toString();
    }
}
