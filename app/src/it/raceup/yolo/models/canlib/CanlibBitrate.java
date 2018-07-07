package it.raceup.yolo.models.canlib;

import java.util.HashMap;

public class CanlibBitrate {
    private static final HashMap<String, Integer> rate;

    static {
        rate = new HashMap<>();
        rate.put("1 Mbit/s", 1000);
        rate.put("500 kbit/s", 500);
        rate.put("250 kbit/s", 250);
        rate.put("125 kbit/s", 125);
        rate.put("100 kbit/s", 100);
        rate.put("83 kbit/s", 83);
        rate.put("62 kbit/s", 62);
        rate.put("50 kbit/s", 50);
        rate.put("10 kbit/s", 10);
    }
}
