package it.raceup.yolo.models.kvaser;

import it.raceup.yolo.models.canlib.RestActivity;

public class RemoteKvaser {
    public static final String HTTP_SCHEME = "http://";
    public static final String PORT = "8080";
    private String ip;
    private String url;
    private RestActivity restActivity;

    public RemoteKvaser(String ip) {
        this.ip = ip;
        url = HTTP_SCHEME + ip + ":" + PORT;
        restActivity = new RestActivity(url);
    }

    public boolean setup() {
        return false;
    }

    public String getSessionId() {
        return "";
    }

    public String getDeviceStatus() {
        // todo parse json
        return restActivity.getDeviceStatus().toString();
    }
}
