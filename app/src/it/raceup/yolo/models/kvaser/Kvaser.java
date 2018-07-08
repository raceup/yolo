package it.raceup.yolo.models.kvaser;

import it.raceup.yolo.models.data.CanMessage;

public abstract class Kvaser {
    public static String TAG = "KVASER";

    public void setup(int canBitrate) {
    }

    public CanMessage[] read() {
        return null;
    }

    public boolean write(int id, byte[] data, int flags) {
        return false;
    }

    public void close() {
    }

    protected void logAction(String message) {
        String timing = "[" + System.currentTimeMillis() + "]";
        String content = ": " + message;
        System.out.println(timing + " " + TAG + content);
    }
}
