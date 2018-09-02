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

    public String getDataAsString() {
        byte[] data = getData();
        String[] bytes = new String[data.length];
        for (int i = 0; i < bytes.length; i++) {  // todo use stream
            bytes[i] = String.format("%3s", Byte.toString(data[i]));
        }
        return Arrays.toString(bytes);
    }

    public static String getLineHeader(String separator) {
        return getLine("Time", "ID",
                "Flags", "Dlc", "Data", separator);
    }

    public static String getLine(String time, String id, String flags,
                                 String dlc, String data, String separator) {
        String formatter = "%-8s" + separator;
        String out = String.format(formatter, time);
        out += String.format(formatter, id);
        out += String.format(formatter, flags);
        out += String.format(formatter, dlc);
        out += String.format("%-40s" + separator, data);
        return out;
    }

    public String getLine(String separator) {
        return getLine(
                Long.toString(getTime()),
                Integer.toString(getId()),
                Integer.toString(getFlags()),
                Integer.toString(getDlc()),
                getDataAsString(),
                separator
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
