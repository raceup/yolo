package it.raceup.yolo.models.canlib;

import org.json.JSONObject;
import org.json.JSONTokener;

import static it.raceup.yolo.models.canlib.RestService.GET;


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
    String bitRateConstant = "-4";
    String driverType = "4";
    private RestService restServiceDeviceStatus,
            restServiceCanInit,
            restServiceCanOpenChannel,
            restServiceCanRead,
            restServiceCanBusOn,
            restServiceCanBusOff,
            restServiceCanSetBusOutputControl,
            restServiceCanSetBusParams,
            restServiceCanClose,
            restServiceCanUnloadLibrary,
            restServiceCanFlushRx;
    private String url;
    private String session;
    private int canHandle = 0;
    private int hnd;

    public RestActivity(String url) {
        this.url = url;

        setup();
    }

    public void setup() {
        restServiceDeviceStatus = new RestService(url + "/deviceStatus", GET);
        restServiceDeviceStatus.addParam("ident", Integer.toString(IDENT_DEVICE_STATUS));

        restServiceCanInit = new RestService(url + "/deviceStatus", GET);
        restServiceCanInit.addParam("ident", Integer.toString(IDENT_INIT));

        restServiceCanOpenChannel = new RestService(url + "/deviceStatus", GET);
        restServiceCanOpenChannel.addParam("ident", Integer.toString(IDENT_OPEN_CHANNEL));
        restServiceCanOpenChannel.addParam("channel", "0");
        restServiceCanOpenChannel.addParam("flags", "0");

        restServiceCanRead = new RestService(url + "/deviceStatus", GET);
        restServiceCanRead.addParam("ident", Integer.toString(IDENT_READ));
        restServiceCanRead.addParam("hnd", Integer.toString(canHandle));

        restServiceCanBusOn = new RestService(url + "/deviceStatus", GET);
        restServiceCanBusOn.addParam("ident", Integer.toString(IDENT_BUS_ON));
        restServiceCanBusOn.addParam("hnd", Integer.toString(canHandle));

        restServiceCanBusOff = new RestService(url + "/deviceStatus", GET);
        restServiceCanBusOff.addParam("ident", Integer.toString(IDENT_BUS_OFF));
        restServiceCanBusOff.addParam("hnd", Integer.toString(canHandle));

        restServiceCanSetBusOutputControl = new RestService(url + "/deviceStatus", GET);
        restServiceCanSetBusOutputControl.addParam("ident", Integer.toString(IDENT_SET_BUS_OUTPUT_CONTROL));
        restServiceCanSetBusOutputControl.addParam("hnd", Integer.toString(canHandle));
        restServiceCanSetBusOutputControl.addParam("drivertype", driverType);

        restServiceCanSetBusParams = new RestService(url + "/deviceStatus", GET);
        restServiceCanSetBusParams.addParam("ident", Integer.toString(IDENT_SET_BUS_PARAMS));
        restServiceCanSetBusParams.addParam("hnd", Integer.toString(canHandle));
        restServiceCanSetBusParams.addParam("freq", bitRateConstant);

        restServiceCanClose = new RestService(url + "/deviceStatus", GET);
        restServiceCanClose.addParam("ident", Integer.toString(IDENT_CLOSE_CHANNEL));
        restServiceCanClose.addParam("hnd", Integer.toString(canHandle));

        restServiceCanUnloadLibrary = new RestService(url + "/deviceStatus", GET);
        restServiceCanUnloadLibrary.addParam("ident", Integer.toString(IDENT_UNLOAD));

        restServiceCanFlushRx = new RestService(url + "/deviceStatus", GET);
        restServiceCanFlushRx.addParam("ident", Integer.toString(IDENT_FLUSH_RX));
        restServiceCanFlushRx.addParam("hnd", Integer.toString(canHandle));
        restServiceCanFlushRx.addParam("func", Integer.toString(CanlibRest.canIOCTL_FLUSH_RX_BUFFER));
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
                    if (canStatus == CanlibRest.canOK) {
                        session = json.getString("session");
                    }
                    break;
                case IDENT_OPEN_CHANNEL:
                    int hnd = json.getInt("hnd");
                    canStatus = json.getInt("stat");
                    log = CanlibRest.getErrorText(canStatus);
                    if (canStatus == CanlibRest.canOK) {
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

    public JSONObject getDeviceStatus() {
        try {
            return restServiceDeviceStatus.execute();
        } catch (Exception e) {
            return null;
        }
    }

    public String getSession() {
        try {
            JSONObject result = restServiceCanInit.execute();
            // todo validate result
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}

