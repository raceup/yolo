package it.raceup.yolo.models.kvaser;

import it.raceup.yolo.logging.Logger;
import it.raceup.yolo.models.data.CanMessage;

public abstract class Kvaser extends Logger {
    public Kvaser() {
        TAG = "KVASER";
    }

    public boolean setup(int canBitrate) {
        return false;
    }

    public CanMessage[] read() {
        return null;
    }

    public boolean write(int id, byte[] data, int flags) {
        return false;
    }

    public void close() {
    }
}
