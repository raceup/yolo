package it.raceup.yolo.models.kvaser;

import it.raceup.yolo.models.canlib.RestActivity;
import it.raceup.yolo.models.data.CanMessage;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class RemoteKvaser {
    public static final String HTTP_SCHEME = "http";
    public static final int PORT = 8080;
    private String url;
    private RestActivity restActivity;

    public RemoteKvaser(String ip) {
        this(HTTP_SCHEME, ip, PORT);
    }

    public RemoteKvaser(String scheme, String host, int port) {
        url = getUrl(scheme, host, port);
        restActivity = new RestActivity(url);
    }

    private static String getUrl(String scheme, String host, int port) {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(scheme);
        uriBuilder.setHost(host);
        uriBuilder.setPort(port);
        return uriBuilder.toString();
    }

    public boolean openConnection() {
        return restActivity.canInitializeLibrary();
    }

    public boolean setupCan(int channel, int flags, int driverType,
                            int freq) {
        boolean isOk = restActivity.canOpenChannel(channel, flags);
        if (!isOk) {
            return false;
        }

        isOk = restActivity.canSetBusOutputControl(driverType);
        if (!isOk) {
            return false;
        }

        isOk = restActivity.canSetBusParams(freq);
        return isOk;
    }

    public boolean onBus() {
        return restActivity.canBusOn();
    }

    public boolean offBus() {
        return restActivity.canBusOff();
    }

    public CanMessage[] readCan() {
        try {
            JSONArray raw = restActivity.canRead(Byte.MAX_VALUE);
            CanMessage[] messages = new CanMessage[raw.length()];
            for (int i = 0; i < raw.length(); i++) {
                JSONObject message = raw.getJSONObject(i);
                messages[i] = CanMessage.parseJson(message);
            }
            return messages;
        } catch (Exception e) {
            return new CanMessage[]{};
        }
    }

    public boolean writeCan(int id, int flag, byte[] msg, int dlc) {
        return restActivity.canWrite(id, flag, msg, dlc);
    }

    public boolean closeCan() {
        return restActivity.canClose();
    }

    public boolean closeConnection() {
        return restActivity.canUnloadLibrary();
    }
}
