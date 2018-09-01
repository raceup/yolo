package it.raceup.yolo.models.kvaser;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.canlib.RestActivity;
import it.raceup.yolo.models.data.CanMessage;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class RemoteKvaser extends Kvaser {
    public static final String HTTP_SCHEME = "http";
    public static final int PORT = 8080;
    private String url;
    private RestActivity restActivity;

    public RemoteKvaser(String ip) {
        this(HTTP_SCHEME, ip, PORT);
    }

    public RemoteKvaser(String scheme, String host, int port) {
        url = getUrl(scheme, host, port);
        TAG = "REMOTE_KVASER @" + url;
        restActivity = new RestActivity(url);
    }

    @Override
    public boolean setup(int canBitrate) {
        if (openConnection()) {
            logAction("connected!");
        } else {
            logAction("can't connect");
            return false;
        }

        if (setupCan(0, 8, 4, canBitrate)) {
            logAction("can is up");
        } else {
            logError("can't open CAN");
            return false;
        }

        if (onBus()) {
            logAction("on bus");
        } else {
            logError("can't on bus");
            return false;
        }

        return true;
    }

    private static String getUrl(String scheme, String host, int port) {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(scheme);
        uriBuilder.setHost(host);
        uriBuilder.setPort(port);
        return uriBuilder.toString();
    }

    private boolean openConnection() {
        return restActivity.canInitializeLibrary();
    }

    private boolean setupCan(int channel, int flags, int driverType,
                             int freq) {
        boolean isOk = restActivity.canOpenChannel(channel, flags);
        if (!isOk) {
            logError("can't open channel");
            return false;
        }

        isOk = restActivity.canSetBusOutputControl(driverType);
        if (!isOk) {
            logError("can't set bus output control");
            return false;
        }

        isOk = restActivity.canSetBusParams(freq);
        if (!isOk) {
            logError("can't set bus params");
            return false;
        }

        return true;
    }

    private boolean onBus() {
        return restActivity.canBusOn();
    }

    private boolean offBus() {
        return restActivity.canBusOff();
    }

    private CanMessage[] readCan() {
        try {
            JSONArray raw = restActivity.canRead(Byte.MAX_VALUE);
            this.logAction("read " + raw.length() + " messages");
            CanMessage[] messages = new CanMessage[raw.length()];
            for (int i = 0; i < raw.length(); i++) {
                JSONObject message = raw.getJSONObject(i);
                messages[i] = CanMessage.parseJson(message);
            }
            return messages;
        } catch (Exception e) {
            new YoloException(
                    "cannot start connection",
                    ExceptionType.KVASER
            ).print();
            return new CanMessage[]{};
        }
    }

    @Override
    public CanMessage[] read() {
        return readCan();
    }

    private boolean writeCan(int id, int flag, byte[] msg, int dlc) {
        return restActivity.canWrite(id, flag, msg, dlc);
    }

    @Override
    public boolean write(int id, byte[] data, int flags) {
        return writeCan(id, flags, data, 4);  // todo check dlc
    }

    private boolean closeCan() {
        return offBus() && restActivity.canClose();
    }

    private boolean closeConnection() {
        return restActivity.canUnloadLibrary();
    }

    @Override
    public void close() {
        if (!closeCan()) {
            logAction("cannot close CAN");
        }

        if (!closeConnection()) {
            logAction("cannot close connection");
        }
    }
}
