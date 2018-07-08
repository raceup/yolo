package it.raceup.yolo.models.kvaser;

import it.raceup.yolo.models.canlib.RestActivity;
import org.apache.http.client.utils.URIBuilder;

public class RemoteKvaser {
    public static final String HTTP_SCHEME = "http";
    public static final int PORT = 8080;
    private String url;
    private int hnd;
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
        if (restActivity.isDeviceFree()) {
            String session = restActivity.getSession();
            System.out.println(session);
            if (session != null) {
                restActivity.setSession(session);
                return true;
            }
        }

        return false;
    }

    public boolean setupCan(int channel, int flags, int driverType,
                            int freq) {
        // todo open can to get hnd
        // todo set bus control
        // todo set bus param
        return false;
    }

    public boolean onBus() {
        // todo can bus on
        return false;
    }

    public boolean offBus() {
        // todo can bus off
        return false;
    }

    public String[] readCan() {
        // todo can read with max messages
        return new String[]{};
    }

    public boolean writeCan() {
        // todo write can
        return false;
    }

    public boolean closeCan() {
        // todo close can
        return false;
    }

    public boolean closeConnection() {
        // todo close connection
        return false;
    }

    public String[] getMessages() {
        // todo can read
        return new String[]{};
    }
}
