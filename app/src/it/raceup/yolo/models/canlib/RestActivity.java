package it.raceup.yolo.models.canlib;

import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;

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
    private int canHandle = CAN_ERROR;
    private int hnd = CAN_ERROR;

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

    public boolean canInitializeLibrary() {
        try {
            JSONObject result = getRestServiceCanInit().get();
            if (isOk(result)) {
                String session = result.getString("session");
                if (isValidSession(session)) {
                    setSession(session);
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean canUnloadLibrary() {
        try {
            JSONObject result = getRestServiceCanUnloadLibrary().get();
            return isOk(result);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean canOpenChannel(int channel, int flags) {
        try {
            RestService service = getRestServiceCanOpenChannel();
            service.addParam("channel", Integer.toString(channel));
            service.addParam("flags", Integer.toString(flags));
            JSONObject result = service.get();
            if (isOk(result)) {
                hnd = result.getInt("hnd");
                return true;
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean canClose() {
        try {
            RestService service = getRestServiceCanClose();
            service.addParam("hnd", Integer.toString(hnd));
            JSONObject result = service.get();
            return isOk(result);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean canSetBusParams(int freq) {
        try {
            RestService service = getRestServiceCanSetBusParams();
            service.addParam("hnd", Integer.toString(hnd));
            service.addParam("freq", Integer.toString(freq));
            JSONObject result = service.get();
            return isOk(result);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean canBusOn() {
        try {
            RestService service = getRestServiceCanBusOn();
            service.addParam("hnd", Integer.toString(hnd));
            JSONObject result = service.get();
            return isOk(result);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean canBusOff() {
        try {
            return true;  // todo implement
        } catch (Exception e) {
            return false;
        }
    }

    public boolean canSetBusOutputControl() {
        try {
            return true;  // todo implement
        } catch (Exception e) {
            return false;
        }
    }

    public JSONObject[] canRead() {
        try {
            return new JSONObject[]{};  // todo implement
        } catch (Exception e) {
            return new JSONObject[]{};
        }
    }

    public boolean canWrite() {
        try {
            return true;  // todo implement
        } catch (Exception e) {
            return false;
        }
    }

    public boolean canIoCtl() {
        try {
            return true;  // todo implement
        } catch (Exception e) {
            return false;
        }
    }

    public int canReadTimer() {
        try {
            return CAN_ERROR;  // todo implement
        } catch (Exception e) {
            return CAN_ERROR;
        }
    }

    public boolean canAddFilter() {
        try {
            return true;  // todo implement
        } catch (Exception e) {
            return false;
        }
    }

    public boolean canClearFilters() {
        try {
            return true;  // todo implement
        } catch (Exception e) {
            return false;
        }
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
        this.session = session;
        createServices();  // reload services with session id
    }

    private boolean isValidSession(String session) {
        return session != null && session.length() == SESSION_ID_LENGTH;
    }

    ///////////////////////////////////////////////////////////////////// Utils

    private boolean isOk(JSONObject jsonText) {
        try {
            int canStatus = jsonText.getInt("stat");
            return canStatus == CAN_OK;
        } catch (Exception e) {
            return false;
        }
    }
}

