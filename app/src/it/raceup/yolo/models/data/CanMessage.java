package it.raceup.yolo.models.data;

public class CanMessage {
    private int id;
    private int dlc;
    private long time;
    private int flag;
    private byte[] data;

    public CanMessage(int id, int dlc, long time, int flag, byte[] data) {
        this.id = id;
        this.dlc = dlc;
        this.time = time;
        this.flag = flag;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public int getDlc() {
        return dlc;
    }

    public long getTime() {
        return time;
    }

    public int getFlag() {
        return flag;
    }

    public byte[] getData() {
        return data;
    }
}
