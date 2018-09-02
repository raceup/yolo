package it.raceup.yolo.models.kvaser;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.data.CanMessage;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Faking a Kvaser Blackbird for debug purposes.
 */
public class FakeBlackBird extends Kvaser {
    private static final String HTTP_SCHEME = "http";
    private static final int PORT = 8080;
    private static final String SAMPLE_JSON = "[{'msg':[0,0,0,0,0,0," +
            "0,0],'flag':2,'dlc':8,'id':393,'time':15876454},{'msg':[0,0,0," +
            "0,0,0,0,0],'flag':2,'dlc':8,'id':392,'time':15876580}]";

    public FakeBlackBird(String ip) {
        this(HTTP_SCHEME, ip, PORT);
    }

    public FakeBlackBird(String scheme, String host, int port) {
        super("FAKE BLACKBIRD @ " + getUrl(scheme, host, port));
    }

    private static String getUrl(String scheme, String host, int port) {
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
        return true;
    }

    private boolean setupCan(int channel, int flags, int driverType,
                             int freq) {
        return true;
    }

    private boolean onBus() {
        return true;
    }

    private boolean offBus() {
        return true;
    }

    private CanMessage[] readCan() {
        try {
            JSONArray raw = new JSONArray(SAMPLE_JSON);
            CanMessage[] messages = new CanMessage[raw.length()];
            for (int i = 0; i < raw.length(); i++) {
                JSONObject message = raw.getJSONObject(i);
                messages[i] = CanMessage.parseJson(message);
            }

            Thread.sleep(50);

            return messages;
        } catch (Exception e) {
            log(
                    new YoloException(
                            "cannot read CAN",
                            e,
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
        return false;
    }

    @Override
    public boolean write(int id, byte[] data, int flags) {
        return writeCan(id, flags, data, 4);
    }

    private boolean closeCan() {
        return true;
    }

    private boolean closeConnection() {
        return true;
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
