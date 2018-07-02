package it.raceup.yolo.models.kvaser;

import obj.Message;

public class CanData {
    private final boolean isError;
    private int length;
    private int id;
    private long time;
    private byte[] data;

    public CanData(Message raw) {
        this.isError = raw.isErrorFrame();
        parse(raw);
    }

    public boolean isError() {
        return isError;
    }

    private void parse(Message raw) {
        length = raw.length;
        id = raw.id;
        time = raw.time;
        this.data = raw.data;
    }

    public int getLength() {
        return length;
    }

    public int getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public byte[] getData() {
        return data;
    }
}
