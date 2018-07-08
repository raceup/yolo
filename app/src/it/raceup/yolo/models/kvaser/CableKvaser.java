package it.raceup.yolo.models.kvaser;

import it.raceup.yolo.models.data.CanMessage;
import obj.Handle;
import obj.Message;

public class CableKvaser extends Kvaser {
    private Handle handle;

    public CableKvaser() {
        TAG = "CABLE_KVASER";
    }

    public boolean hasData() {
        try {
            return handle.hasMessage();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public CanMessage[] read() {
        try {
            Message message = handle.read();
            CanMessage data = new CanMessage(message);
            return new CanMessage[]{data};
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void close() {
        try {
            handle.busOff();  // Going off bus and closing channel
            handle.close();
        } catch (Exception e) {
        }
    }

    @Override
    public boolean setup(int canBitrate) {
        try {
            handle = new Handle(0);  // Set up the channel and going on bus
            handle.setBusParams(canBitrate, 0, 0, 0, 0, 0);
            if (!startConnection()) {
                logAction("cannot start connection");
                return false;
            }

            return true;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    private boolean startConnection() {
        try {
            handle.busOn();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean write(int id, byte[] data, int flags) {
        Message message = new Message(id, data, data.length, flags);
        try {
            handle.write(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
