package it.raceup.yolo.models.kvaser;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.canlib.RestActivity;
import it.raceup.yolo.models.data.CanMessage;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Kvaser Blackbird device interface
 */
public class BlackBird extends Kvaser {
    private static final String HTTP_SCHEME = "http";
    private static final int PORT = 8080;
    private final RestActivity restActivity;

    public BlackBird(String ip) {
        this(HTTP_SCHEME, ip, PORT);
    }

    public BlackBird(String scheme, String host, int port) {
        super("BLACKBIRD @ " + getUrl(scheme, host, port));
        restActivity = new RestActivity(getUrl(scheme, host, port));
    }

    public static String getUrl(String scheme, String host, int port) {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(scheme);
        uriBuilder.setHost(host);
        uriBuilder.setPort(port);
        return uriBuilder.toString();
    }

    @Override
    public boolean setup(String canBitrate) {
        if (openConnection()) {
            log("connected!");
        } else {
            log(new YoloException("cannot open connection", ExceptionType
                    .CANLIB));
            return false;
        }

        int bitrate = getCanlibVersionOfBitrate(canBitrate);
        if (setupCan(0, 8, 4, bitrate)) {
            log("can is up");
        } else {
            log(new YoloException("cannot open can", ExceptionType.CANLIB));
            return false;
        }

        if (onBus()) {
            log("on bus");
        } else {
            log(new YoloException("cannot on bus", ExceptionType.CANLIB));
            return false;
        }

        return true;
    }

    private boolean openConnection() {
        return restActivity.canInitializeLibrary();
    }

    private boolean setupCan(int channel, int flags, int driverType,
                             int freq) {
        boolean isOk = restActivity.canOpenChannel(channel, flags);
        if (!isOk) {
            log(new YoloException("cannot open channel", ExceptionType.CANLIB));
            return false;
        }

        isOk = restActivity.canSetBusOutputControl(driverType);
        if (!isOk) {
            log(new YoloException("cannot set bus output control", ExceptionType.CANLIB));
            return false;
        }

        isOk = restActivity.canSetBusParams(freq);
        if (!isOk) {
            log(new YoloException("cannot set bus params", ExceptionType
                    .CANLIB));
            return false;
        }

        return true;
    }

    private boolean onBus() {
        return restActivity.canBusOn();
    }

    private boolean offBus() {
        log("off bus");
        return restActivity.canBusOff();
    }

    private CanMessage[] readCan() {
        try {
            JSONArray raw = restActivity.canRead(Byte.MAX_VALUE);
            CanMessage[] messages = new CanMessage[raw.length()];
            for (int i = 0; i < raw.length(); i++) {
                JSONObject message = raw.getJSONObject(i);
                messages[i] = CanMessage.parseJson(message);
            }
            return messages;
        } catch (Exception e) {
            log(
                    new YoloException(
                            "cannot read CAN",
                            ExceptionType.KVASER
                    )
            );
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
            log("cannot close CAN");
        }

        if (!closeConnection()) {
            log("cannot close connection");
        }
    }
}
