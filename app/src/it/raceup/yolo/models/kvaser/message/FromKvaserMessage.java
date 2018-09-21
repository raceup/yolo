package it.raceup.yolo.models.kvaser.message;

import it.raceup.yolo.models.data.CanMessage;

import java.util.ArrayList;

/**
 * Message incoming from Kvaser
 */
public class FromKvaserMessage {
    private final Object data;

    public FromKvaserMessage(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public ArrayList<CanMessage> getAsCanMessages() {
        if (data instanceof ArrayList) {  // todo test
            if (((ArrayList) data).get(0) instanceof CanMessage) {
                return (ArrayList<CanMessage>) data;
            }
        }

        return null;
    }

    public Boolean getAsBoolean() {
        if (data instanceof Boolean) {
            return (Boolean) data;
        }

        return null;
    }
}
