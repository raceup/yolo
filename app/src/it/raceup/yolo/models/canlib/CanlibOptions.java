package it.raceup.yolo.models.canlib;

import java.util.HashMap;

public class CanlibOptions {
    private static final HashMap<String, Integer> bitRate;
    private static final HashMap<String, Integer> modeType;

    static {
        bitRate = new HashMap<>();
        bitRate.put("1 Mbit/s", 1000);
        bitRate.put("500 kbit/s", 500);
        bitRate.put("250 kbit/s", 250);
        bitRate.put("125 kbit/s", 125);
        bitRate.put("100 kbit/s", 100);
        bitRate.put("83 kbit/s", 83);
        bitRate.put("62 kbit/s", 62);
        bitRate.put("50 kbit/s", 50);
        bitRate.put("10 kbit/s", 10);
    }

    static {
        modeType = new HashMap<>();
        modeType.put("Normal mode", 0);
        modeType.put("Silent mode", 1);
    }
}
