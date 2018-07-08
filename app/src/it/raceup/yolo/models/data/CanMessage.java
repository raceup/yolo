package it.raceup.yolo.models.data;

import obj.Message;
import org.json.JSONArray;
import org.json.JSONObject;

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
        JSONArray rawData = message.getJSONArray("data");
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

    public int getDlc() {
        return dlc;
    }

    public byte[] getData() {
        return data;
    }
}
