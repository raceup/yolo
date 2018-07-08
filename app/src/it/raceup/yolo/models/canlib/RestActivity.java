package it.raceup.yolo.models.canlib;

import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import static it.raceup.yolo.models.canlib.CanlibRest.CAN_ERROR;
import static it.raceup.yolo.models.canlib.CanlibRest.CAN_OK;

/**
 * Sends/receives data to/from remote Kvaser
 */
public class RestActivity {
    // api keys
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
    public static final int IDENT_READ_TIMER = 13;
    public static final int IDENT_ADD_FILTER = 14;
    public static final int IDENT_CLEAR_FILTERS = 15;

    // api endpoints
    public final static String DEVICE_STATUS = "/deviceStatus";
    public final static String CAN_INITIALIZE_LIBRARY = "/canInitializeLibrary";
    public final static String CAN_OPEN_CHANNEL = "/canOpenChannel";
    public final static String CAN_READ = "/canRead";
    public final static String CAN_WRITE = "/canWrite";
    public final static String CAN_BUS_ON = "/canBusOn";
    public final static String CAN_BUS_OFF = "/canBusOff";
    public final static String CAN_SET_BUS_OUTPUT_CONTROL =
            "/canSetBusOutputControl";
    public final static String CAN_SET_BUS_PARAMS = "/canSetBusParams";
    public final static String CAN_CLOSE = "/canClose";
    public final static String CAN_UNLOAD_LIBRARY = "/canUnloadLibrary";
    public final static String CAN_FLUSH_RX = "/canIoCtl";
    public final static String CAN_READ_TIMER = "/canReadTimer";
    public final static String CAN_ADD_FILTER = "/canAddFilter";
    public final static String CAN_CLEAR_FILTERS = "/canClearFilters";

    private static final int CLEAR_ID = 1;
    private static final int SESSION_ID_LENGTH = 32;
    private String bitRateConstant = "-4";
    private String driverType = "4";
    private RestService restServiceDeviceStatus;
    private RestService restServiceCanInit;
    private RestService restServiceCanOpenChannel;
    private RestService restServiceCanRead;
    private RestService restServiceCanWrite;
    private RestService restServiceCanBusOn;
    private RestService restServiceCanBusOff;
    private RestService restServiceCanSetBusOutputControl;
    private RestService restServiceCanSetBusParams;
    private RestService restServiceCanClose;
    private RestService restServiceCanUnloadLibrary;
    private RestService restServiceCanFlushRx;
    private RestService restServiceCanReadTimer;
    private RestService restServiceCanAddFilter;
    private RestService restServiceCanClearFilters;
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

    public RestService getRestServiceCanWrite() {
        return restServiceCanWrite;
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

    public RestService getRestServiceCanReadTimer() {
        return restServiceCanReadTimer;
    }

    public RestService getRestServiceCanAddFilter() {
        return restServiceCanAddFilter;
    }

    public RestService getRestServiceCanClearFilters() {
        return restServiceCanClearFilters;
    }

    private void createServices() {
        restServiceDeviceStatus = new RestService(baseUrl, DEVICE_STATUS,
                IDENT_DEVICE_STATUS);
        restServiceCanInit = new RestService(baseUrl, CAN_INITIALIZE_LIBRARY,
                IDENT_INIT);
        restServiceCanOpenChannel = new RestService(url, CAN_OPEN_CHANNEL, IDENT_OPEN_CHANNEL);
        restServiceCanRead = new RestService(url, CAN_READ, IDENT_READ);
        restServiceCanWrite = new RestService(url, CAN_WRITE, IDENT_WRITE);
        restServiceCanBusOn = new RestService(url, CAN_BUS_ON, IDENT_BUS_ON);
        restServiceCanBusOff = new RestService(url, CAN_BUS_OFF, IDENT_BUS_OFF);
        restServiceCanSetBusOutputControl = new RestService(url, CAN_SET_BUS_OUTPUT_CONTROL, IDENT_SET_BUS_OUTPUT_CONTROL);
        restServiceCanSetBusParams = new RestService(url, CAN_SET_BUS_PARAMS, IDENT_SET_BUS_PARAMS);
        restServiceCanClose = new RestService(url, CAN_CLOSE, IDENT_CLOSE_CHANNEL);
        restServiceCanUnloadLibrary = new RestService(url, CAN_UNLOAD_LIBRARY, IDENT_UNLOAD);
        restServiceCanFlushRx = new RestService(url, CAN_FLUSH_RX, IDENT_FLUSH_RX);
        restServiceCanReadTimer = new RestService(url, CAN_READ_TIMER,
                IDENT_READ_TIMER);
        restServiceCanAddFilter = new RestService(url, CAN_ADD_FILTER,
                IDENT_ADD_FILTER);
        restServiceCanClearFilters = new RestService(url, CAN_CLEAR_FILTERS,
                IDENT_CLEAR_FILTERS);
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
            RestService service = getRestServiceCanBusOff();
            service.addParam("hnd", Integer.toString(hnd));
            JSONObject result = service.get();
            return isOk(result);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean canSetBusOutputControl(int drivertype) {
        try {
            RestService service = getRestServiceCanSetBusOutputControl();
            service.addParam("hnd", Integer.toString(hnd));
            service.addParam("drivertype", Integer.toString(drivertype));
            JSONObject result = service.get();
            return isOk(result);
        } catch (Exception e) {
            return false;
        }
    }

    public JSONArray canRead(int max) {
        try {
            RestService service = getRestServiceCanRead();
            service.addParam("hnd", Integer.toString(hnd));
            service.addParam("max", Integer.toString(max));
            JSONObject result = service.get();
            if (isOk(result)) {
                return result.getJSONArray("msgs");
            }

            return new JSONArray();
        } catch (Exception e) {
            return new JSONArray();
        }
    }

    public boolean canWrite(int id, int flag, byte[] msg, int dlc) {
        try {
            RestService service = getRestServiceCanWrite();
            service.addParam("hnd", Integer.toString(hnd));
            service.addParam("id", Integer.toString(id));
            service.addParam("flag", Integer.toString(flag));
            service.addParam("msg", getCanMsg(msg));
            service.addParam("dlc", Integer.toString(dlc));
            JSONObject result = service.get();
            return isOk(result);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean canIoCtl(int func) {
        try {
            RestService service = getRestServiceCanFlushRx();
            service.addParam("hnd", Integer.toString(hnd));
            service.addParam("func", Integer.toString(func));
            JSONObject result = service.get();
            return isOk(result);
        } catch (Exception e) {
            return false;
        }
    }

    public long canReadTimer() {
        try {
            RestService service = getRestServiceCanReadTimer();
            service.addParam("hnd", Integer.toString(hnd));
            JSONObject result = service.get();
            if (isOk(result)) {
                return result.getLong("time");
            }

            return CAN_ERROR;
        } catch (Exception e) {
            return CAN_ERROR;
        }
    }

    public boolean canAddFilter(int type, int id, int counterThreshold, int
            counterMax, int flags) {
        try {
            RestService service = getRestServiceCanAddFilter();
            service.addParam("hnd", Integer.toString(hnd));
            service.addParam("type", Integer.toString(type));
            service.addParam("id", Integer.toString(id));
            service.addParam("counterThreshold", Integer.toString(counterThreshold));
            service.addParam("counterMax", Integer.toString(counterMax));
            service.addParam("flags", Integer.toString(flags));
            JSONObject result = service.get();
            return isOk(result);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean canClearFilters() {
        try {
            RestService service = getRestServiceCanClearFilters();
            service.addParam("hnd", Integer.toString(hnd));
            JSONObject result = service.get();
            return isOk(result);
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

    private String getCanMsg(byte[] msg) {
        String[] bytes = new String[msg.length];
        for (int i = 0; i < msg.length; i++) {
            bytes[i] = Byte.toString(msg[i]);
        }

        return String.join(",", bytes);
    }
}

