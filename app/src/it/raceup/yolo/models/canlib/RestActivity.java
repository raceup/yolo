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

    private static String getUrl(String baseUrl, String session) {
        try {
            URIBuilder uriBuilder = new URIBuilder(baseUrl);
            uriBuilder.setPath(session);
            return uriBuilder.toString();
        } catch (Exception e) {
            return baseUrl;
        }
    }

    public RestActivity(String url, String session) {
        this.baseUrl = url;
        this.url = getUrl(url, session);
        this.session = session;
        createServices();
    }

    public RestActivity(String url) {
        this(url, null);
    }

    ///////////////////////////////////////////////////////////// Rest Services

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

    //////////////////////////////////////////////////////////////// Canlib API

    public int deviceStatus() {
        try {
            JSONObject result = getRestServiceDeviceStatus().get();
            return result.getInt("usage");
        } catch (Exception e) {
            return CAN_ERROR;
        }
    }

    public String canInitializeLibrary() {
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

    public boolean canUnloadLibrary() {
        return false;  // todo implement
    }

    public int canOpenChannel() {
        return CAN_ERROR;  // todo implement
    }

    public boolean canClose() {
        return false;  // todo implement
    }

    public boolean canSetBusParams() {
        return false;  // todo implement
    }

    public boolean canBusOn() {
        return false;  // todo implement
    }

    public boolean canBusOff() {
        return false;  // todo implement
    }

    public boolean canSetBusOutputControl() {
        return false;  // todo implement
    }

    public JSONObject[] canRead() {
        return new JSONObject[]{};  // todo implement
    }

    public boolean canWrite() {
        return false;  // todo implement
    }

    public boolean canIoCtl() {
        return false;  // todo implement
    }

    public int canReadTimer() {
        return CAN_ERROR;  // todo implement
    }

    public boolean canAddFilter() {
        return false;  // todo implement
    }

    public boolean canClearFilters() {
        return false;  // todo implement
    }

    ///////////////////////////////////////////////////////////// Device status

    public boolean isDeviceFree() {
        try {
            int deviceStatus = deviceStatus();
            return deviceStatus == CAN_OK;
        } catch (Exception e) {
            return false;
        }
    }

    /////////////////////////////////////////////////////////////////// Session

    public void setSession(String session) {
        this.url = getUrl(this.baseUrl, session);
        createServices();  // reload services with session id
    }

    private boolean isOk(String jsonText) {
        try {
            JSONTokener jsonTokener = new JSONTokener(jsonText);
            JSONObject json = (JSONObject) jsonTokener.nextValue();
            int canStatus = json.getInt("stat");
            return canStatus == CAN_OK;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidSession(String session) {
        return session != null && session.length() == SESSION_ID_LENGTH;
    }
}

