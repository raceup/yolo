package it.raceup.yolo.models.kvaser;

import core.Canlib;
import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import obj.Handle;
import obj.Message;

public class Kvaser {
    private Handle handle;
    private int canBitrate;

    public Kvaser() throws YoloException {
        this(Canlib.canBITRATE_1M);
    }

    public Kvaser(int canBitrate) throws YoloException {
        this.canBitrate = canBitrate;
        try {
            setup();
        } catch (Exception e) {
            throw new YoloException(e.getMessage(), ExceptionType.CANLIB);
        }
    }

    public boolean hasData() {
        try {
            return handle.hasMessage();
        } catch (Exception e) {
            return false;
        }
    }

    public CanData getMostRecentData() {
        try {
            Message message = handle.read();
            CanData data = new CanData(message);
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    public void close() {
        try {
            handle.busOff();  // Going off bus and closing channel
            handle.close();
        } catch (Exception e) {
        }
    }

    private void setup() throws obj.CanlibException {
        handle = new Handle(0);  // Set up the channel and going on bus
        handle.setBusParams(this.canBitrate, 0, 0, 0, 0, 0);
    }

    public boolean startConnection() {
        try {
            handle.busOn();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean sendMessage(int id, byte[] data, int flags) {
        Message message = new Message(id, data, data.length, flags);
        try {
            handle.write(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
