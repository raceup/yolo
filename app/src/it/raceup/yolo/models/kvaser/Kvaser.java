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

    public boolean hasData() throws obj.CanlibException {
        return handle.hasMessage();
    }

    public CanData getMostRecentData() throws obj.CanlibException {
        Message message = handle.read();
        CanData data = new CanData(message);
        return data;
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

    public void startConnection() throws obj.CanlibException {
        handle.busOn();
    }
}
