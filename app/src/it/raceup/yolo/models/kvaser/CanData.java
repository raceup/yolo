package it.raceup.yolo.models.kvaser;

import obj.Message;

import static it.raceup.yolo.utils.Utils.convertTime;

public class CanData {
    private final boolean error;
    private int length;
    private int id;
    private String time;
    private byte[] data;

    public CanData(Message raw) {
        this.error = raw.isErrorFrame();
        parse(raw);
    }

    public boolean isError() {
        return error;
    }

    private void parse(Message raw) {
        length = raw.length;
        id = raw.id;
        time = convertTime(raw.time);
        this.data = raw.data;
    }

    public int getLength() {
        return length;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        if (isError()) {
            return "Error frame";
        }

        String idString = String.format("%8s", Integer.toBinaryString(getId()))
                .replace(' ', '0');
        StringBuilder out = new StringBuilder(idString + " of length" + Integer.toString
                (getLength()) + " at " + getTime());
        out.append("\t");
        for (int i = 0; i < getData().length; i++) {
            out.append(Byte.toString(getData()[i])).append("\t");
        }
        return out.toString();
    }
}
