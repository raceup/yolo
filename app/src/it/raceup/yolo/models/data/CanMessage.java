package it.raceup.yolo.models.data;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public static CanMessage parseJson(JSONObject message) {
        JSONArray rawData = message.getJSONArray("data");
        byte[] data = new byte[rawData.length()];
        for (int j = 0; j < rawData.length(); j++) {
            data[j] = (byte) rawData.optInt(j);
        }

        return new CanMessage(
                message.getInt("id"),
                message.getInt("dlc"),
                message.getInt("time"),
                message.getInt("flag"),
                data
        );
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
