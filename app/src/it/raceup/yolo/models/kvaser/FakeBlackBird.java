package it.raceup.yolo.models.kvaser;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.data.CanMessage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Faking a Kvaser Blackbird for debug purposes.
 */
public class FakeBlackBird extends Kvaser {
    private static final String HTTP_SCHEME = "http";
    private static final int PORT = 8080;
    private static final String[] IDS = new String[]{"17", "18", "19", "20",
            "21", "22", "23", "24", "25", "33", "93", "96", "97", "388", "389", "392", "393",
            "643", "644", "645", "646", "647", "648", "649", "656", "768",
            "801", "802", "817", "818", "865", "885"};

    public FakeBlackBird(String ip) {
        this(HTTP_SCHEME, ip, PORT);
    }

    public FakeBlackBird(String scheme, String host, int port) {
        super("FAKE BLACKBIRD @ " + BlackBird.getUrl(scheme, host, port));
    }

    private static String getRandomMessage() {
        String message = "{";
        message += "'msg':" + Arrays.toString(getRandomData(8)) + ",";
        message += "'flag':" + Integer.toString(getRandomInt(10, 20)) + ",";
        message += "'dlc':" + Integer.toString(getRandomInt(20, 30)) + ",";
        message += "'id':" + IDS[getRandomInt(0, IDS.length - 1)] + ",";
        message += "'time':" +
                Integer.toString(getRandomInt(10000000, 100000000)) + "}";
        return message;
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

    @Override
    public boolean setup(String canBitrate) {
        log("connected!");
        log("can is up");
        log("on bus");
        return true;
    }

    @Override
    public CanMessage[] read() {
        //test code
        //return readCan();
        return readCanFromFile();
    }

    private boolean writeCan(int id, int flag, byte[] msg, int dlc) {
        return true;
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

    private CanMessage[] readCanFromFile() {
        File file = new File("/Users/Francesco/Desktop/da_fermi.log");
        ArrayList<CanMessage> toRet = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            byte data[] = new byte[8];


            while (sc.hasNextLine()) {

                String st = sc.nextLine();
                st = st.replace(" ", "");


                int flag = Integer.parseInt(st.substring(0, 1));
                //System.out.println("flag = " + flag);
                int id = Integer.parseInt(st.substring(1, 9), 16);
                //System.out.println("id = " + id);
                int len = Integer.parseInt(st.substring(9, 10), 16);
                //System.out.println("length = " + len);
                double time =0;

                if (len == 8) {
                    String b = st.substring(10, 26);
                    for (int i = 0; i < 8; i++) {
                        String temp = b.substring(i, 1 + i);
                        int intTemp = Integer.valueOf(temp, 16);
                        data[i] = (byte) intTemp;
                    }
                    time = Double.parseDouble(st.substring(26, st.length() - 1));
                   // System.out.println("time = " + time);
                } else if (len == 6) {
                    String b = st.substring(10, 24);
                    for (int i = 0; i < 6; i++) {
                        String temp = b.substring(i, 1 + i);
                        int intTemp = Integer.valueOf(temp, 16);
                        data[i] = (byte) intTemp;
                    }
                    time = Double.parseDouble(st.substring(24, st.length() - 1));
                    //System.out.println("time = " + time);
                }

                CanMessage toAdd = new CanMessage(id, data, len, flag, (long)time, 0);
                toRet.add(toAdd);
            }

            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException er) {
            er.printStackTrace();

        }

        CanMessage[] ret = new CanMessage[toRet.size()];
        for(int i=0; i<toRet.size(); i++){
            ret[i] = toRet.remove(0);
        }
        return ret;
    }

    private CanMessage[] readCan() {
        try {
            JSONArray raw = new JSONArray(getRandomPacket());
            CanMessage[] messages = new CanMessage[raw.length()];
            for (int i = 0; i < raw.length(); i++) {
                JSONObject message = raw.getJSONObject(i);
                messages[i] = CanMessage.parseJson(message);
            }

            Thread.sleep(10);

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
