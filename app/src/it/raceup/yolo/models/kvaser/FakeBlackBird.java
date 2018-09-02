package it.raceup.yolo.models.kvaser;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.data.CanMessage;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Faking a Kvaser Blackbird for debug purposes.
 */
public class FakeBlackBird extends Kvaser {
    private static final String HTTP_SCHEME = "http";
    private static final int PORT = 8080;
    private static final String[] IDS = new String[]{"17", "18", "19", "20",
            "21", "22", "23", "24", "25", "33", "388", "389", "392", "393",
            "643", "644", "645", "646", "647", "648", "649", "656"};

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

    private static String getRandomMessage() {
        String message = "{";
        message += "'msg':" + Arrays.toString(getRandomData(8)) + ",";
        message += "'flag':" + Integer.toString(getRandomInt(10, 20)) + ",";
        message += "'dlc':" + Integer.toString(getRandomInt(20, 30)) + ",";
        message += "'id':" + IDS[getRandomInt(0, IDS.length - 1)] + ",";
        message += "'time':" + Integer.toString(getRandomInt(30, 40)) + "}";
        return message;
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

    private static String[] getRandomMessages(int number) {
        String[] messages = new String[number];
        for (int i = 0; i < number; i++) {
            messages[i] = getRandomMessage();
        }
        return messages;
    }

    private static String getRandomPacket() {
        int numberOfMessages = getRandomInt(1, 100);
        String[] messages = getRandomMessages(numberOfMessages);
        return "[" + String.join(",", messages) + "]";
    }

    private static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private static byte[] getRandomData(int length) {
        byte[] data = new byte[length];
        for (int i = 0; i < length; i++) {
            data[i] = (byte) getRandomInt(0, Byte.MAX_VALUE - 1);
        }
        return data;
    }

    private CanMessage[] readCan() {
        try {
            JSONArray raw = new JSONArray(getRandomPacket());
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
}
