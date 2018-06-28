package it.raceup.yolo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {
    public static String getTimeNow(String format) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(cal.getTime());
    }
}
