package it.raceup.yolo.models.canlib;

import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.NoSuchElementException;

import static it.raceup.yolo.models.canlib.CanlibRest.CAN_ERROR;
import static it.raceup.yolo.models.canlib.CanlibRest.CAN_OK;
import static it.raceup.yolo.models.canlib.RestService.*;

/**
 * Sends/receives data to/from remote Kvaser
 */
public class RestActivity {
    public static final int IDENT_DEVICE_STATUS = 1;
    public static final int IDENT_INIT = 2;
    public static final int IDENT_OPEN_CHANNEL = 3;
    public static final int IDENT_SET_BUS_OUTPUT_CONTROL = 4;
    public static final int IDENT_SET_BUS_PARAMS = 5;
    public static final int IDENT_BUS_ON = 6;
    public static final int IDENT_BUS_OFF = 7;
    public static final int IDENT_READ = 8;
    public static final int IDENT_FLUSH_RX = 9;
    public static final int IDENT_CLOSE_CHANNEL = 10;
    public static final int IDENT_UNLOAD = 11;
    public static final int IDENT_WRITE = 12;
    private static final int CLEAR_ID = 1;
    private static final int SESSION_ID_LENGTH = 32;
    private String bitRateConstant = "-4";
    private String driverType = "4";
    private RestService restServiceDeviceStatus;
    private RestService restServiceCanInit;
    private RestService restServiceCanOpenChannel;
    private RestService restServiceCanRead;
    private RestService restServiceCanBusOn;
    private RestService restServiceCanBusOff;
    private RestService restServiceCanSetBusOutputControl;
    private RestService restServiceCanSetBusParams;
    private RestService restServiceCanClose;
    private RestService restServiceCanUnloadLibrary;
    private RestService restServiceCanFlushRx;
    private String url;
    private String baseUrl;
    private String session;
    private int canHandle = 0;
    private int hnd;

    public RestActivity(String url, String session) {
        this.baseUrl = url;
        this.url = getUrl(url, session);
        this.session = session;
        createServices();
    }

    public RestActivity(String url) {
        this(url, null);
    }

    public RestService getRestServiceDeviceStatus() {
        return restServiceDeviceStatus;
    }

    public RestService getRestServiceCanInit() {
        return restServiceCanInit;
    }

    public RestService getRestServiceCanOpenChannel() {
        return restServiceCanOpenChannel;
    }

    public RestService getRestServiceCanRead() {
        return restServiceCanRead;
    }

    public RestService getRestServiceCanBusOn() {
        return restServiceCanBusOn;
    }

    public RestService getRestServiceCanBusOff() {
        return restServiceCanBusOff;
    }

    public RestService getRestServiceCanSetBusOutputControl() {
        return restServiceCanSetBusOutputControl;
    }

    public RestService getRestServiceCanSetBusParams() {
        return restServiceCanSetBusParams;
    }

    public RestService getRestServiceCanClose() {
        return restServiceCanClose;
    }

    public RestService getRestServiceCanUnloadLibrary() {
        return restServiceCanUnloadLibrary;
    }

    public RestService getRestServiceCanFlushRx() {
        return restServiceCanFlushRx;
    }

    private static String getUrl(String baseUrl, String session) {
        try {
            URIBuilder uriBuilder = new URIBuilder(baseUrl);
            uriBuilder.setPath(session);
            return uriBuilder.toString();
        } catch (Exception e) {
            return baseUrl;
        }
    }

    private String parseResult(String jsonText) {
        String log = "";

        try {
            JSONTokener jsonTokener = new JSONTokener(jsonText);
            JSONObject json = (JSONObject) jsonTokener.nextValue();
            int canStatus;
            int ident = json.getInt("ident");
            switch (ident) {
                case IDENT_DEVICE_STATUS:
                    int usage = json.getInt("usage");
                    if (usage == CanlibRest.kvrDeviceUsage_FREE) {
                        log = "\nkvrDeviceUsage_FREE";
                    } else if (usage == CanlibRest.kvrDeviceUsage_REMOTE) {
                        log = "\nkvrDeviceUsage_REMOTE";
                    }
                    break;
                case IDENT_INIT:
                    canStatus = json.getInt("stat");
                    log = CanlibRest.getErrorText(canStatus);
                    if (canStatus == CAN_OK) {
                        session = json.getString("session");
                    }
                    break;
                case IDENT_OPEN_CHANNEL:
                    int hnd = json.getInt("hnd");
                    canStatus = json.getInt("stat");
                    log = CanlibRest.getErrorText(canStatus);
                    if (canStatus == CAN_OK) {
                        this.hnd = hnd;
                    }
                    break;

                default:
                    canStatus = json.getInt("stat");
                    log = CanlibRest.getErrorText(canStatus);
            }
        } catch (Exception e) {
            log = "Get: Failed to parse JSON: " + jsonText;
        }

        return log;
    }

    public int getDeviceStatus() {
        try {
            JSONObject result = getRestServiceDeviceStatus().get();
            return result.getInt("usage");
        } catch (Exception e) {
            return CAN_ERROR;
        }
    }

    public boolean isDeviceFree() {
        try {
            int deviceStatus = getDeviceStatus();
            return deviceStatus == CAN_OK;
        } catch (Exception e) {
            return false;
        }
    }

    public String getSession() {
        if (!isValidSession(session)) {
            try {
                JSONObject result = getRestServiceCanInit().get();
                String session = result.getString("session");
                if (session.length() == SESSION_ID_LENGTH) {
                    return session;
                }

                throw new NoSuchElementException("No session found");
            } catch (Exception e) {
                return null;
            }
        }

        return session;
    }

    public void setSession(String session) {
        this.url = getUrl(this.baseUrl, session);
        createServices();  // reload services with session id
    }

    private void createServices() {
        restServiceDeviceStatus = new RestService(baseUrl, DEVICE_STATUS,
                IDENT_DEVICE_STATUS);
        restServiceCanInit = new RestService(baseUrl, CAN_INITIALIZE_LIBRARY,
                IDENT_INIT);
        restServiceCanOpenChannel = new RestService(url, CAN_OPEN_CHANNEL, IDENT_OPEN_CHANNEL);
        restServiceCanRead = new RestService(url, CAN_READ, IDENT_READ);
        restServiceCanBusOn = new RestService(url, CAN_BUS_ON, IDENT_BUS_ON);
        restServiceCanBusOff = new RestService(url, CAN_BUS_OFF, IDENT_BUS_OFF);
        restServiceCanSetBusOutputControl = new RestService(url, CAN_SET_BUS_OUTPUT_CONTROL, IDENT_SET_BUS_OUTPUT_CONTROL);
        restServiceCanSetBusParams = new RestService(url, CAN_SET_BUS_PARAMS, IDENT_SET_BUS_PARAMS);
        restServiceCanClose = new RestService(url, CAN_CLOSE, IDENT_CLOSE_CHANNEL);
        restServiceCanUnloadLibrary = new RestService(url, CAN_UNLOAD_LIBRARY, IDENT_UNLOAD);
        restServiceCanFlushRx = new RestService(url, CAN_FLUSH_RX, IDENT_FLUSH_RX);
    }

    private boolean isValidSession(String session) {
        return session != null && session.length() == SESSION_ID_LENGTH;
    }
}

