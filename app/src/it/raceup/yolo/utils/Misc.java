package it.raceup.yolo.utils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static it.raceup.yolo.Data.PRETTY_DATE_FORMAT;

public class Misc {
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

    public static String getFileContent(String filePath) throws IOException {
        InputStream in = Toolkit.getDefaultToolkit().getClass().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder builder = new StringBuilder();

        String line = reader.readLine();
        while (line != null) {
            builder.append(line).append("\n");
            line = reader.readLine();
        }

        return builder.toString();
    }
}
