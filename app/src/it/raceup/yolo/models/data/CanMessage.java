package it.raceup.yolo.models.data;

import obj.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

public class CanMessage extends Message {
    private int dlc;

    public CanMessage(int id, byte[] data, int length, int flags, long time,
                      int dlc) {
        super(id, data, length, flags, time);
        this.dlc = dlc;
    }

    public CanMessage(Message message) {
        this(message.id, message.data, message.length, message.flags,
                message.time, -1);
    }

    public static CanMessage parseJson(JSONObject message) {
        JSONArray rawData = message.getJSONArray("msg");
        byte[] data = new byte[rawData.length()];
        for (int j = 0; j < rawData.length(); j++) {
            data[j] = (byte) rawData.optInt(j);
        }

        return new CanMessage(
                message.getInt("id"),
                data,
                data.length,
                message.getInt("flag"),
                message.getInt("time"),
                message.getInt("dlc")
        );
    }

    public int getId() {
        return id;
    }

    public int getFlags() {
        return flags;
    }

    public long getTime() {
        return time;
    }

    public int getDlc() {
        return dlc;
    }

    public byte[] getData() {
        return data;
    }

    public static String getLineHeader() {
        StringBuilder line = new StringBuilder(getLine("Time", "ID", "Flags", "Dlc", "Data"));
        int length = line.length();
        line.append("\n");
        for (int i = 0; i < length; i++) {
            line.append("-");
        }
        line.append("\n");
        return line.toString();
    }

    public static String getLine(String time, String id, String flags,
                                 String dlc, String data) {
        String out = String.format("|%-14s|", time);
        out += String.format("%-15s|", id);
        out += String.format("%-15s|", flags);
        out += String.format("%-15s|", dlc);
        out += String.format("%-15s|", data);
        return out;
    }

    public String getLine() {
        return getLine(
                Long.toString(getTime()),
                Integer.toString(getId()),
                Integer.toString(getFlags()),
                Integer.toString(getDlc()),
                Arrays.toString(getData())
        );
    }

    public static String getDict(String time, String id, String flags,
                                 String dlc, String data) {
        String out = "CanMessage {\n";
        out += "\tTime: " + time + "\n";
        out += "\tID: " + id + "\n";
        out += "\tFlags: " + flags + "\n";
        out += "\tDlc: " + dlc + "\n";
        out += "\tData: " + data + "\n}";
        return out;
    }

    public String getDict() {
        return getDict(
                Long.toString(getTime()),
                Integer.toString(getId()),
                Integer.toString(getFlags()),
                Integer.toString(getDlc()),
                Arrays.toString(getData())
        );
    }

    @Override
    public String toString() {
        return getDict();
    }
}
