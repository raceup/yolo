package it.raceup.yolo.models.kvaser;

import it.raceup.yolo.models.data.CanMessage;

import java.util.ArrayList;
import java.util.Observable;

public abstract class RawKvaser extends Observable {
    private int bufferSize = 32;
    private ArrayList<CanMessage> data = new ArrayList<>();

    public RawKvaser(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    private void updateBuffer(CanMessage[] newData) {
        for (CanMessage message : newData) {
            addNewMessage(message);
        }
    }

    private void addNewMessage(CanMessage message) {
        data.add(message);

        if (data.size() >= this.bufferSize) {  // buffer is full
            triggerObservers();
            data.clear();  // clean buffer (now size is 0)
        }
    }

    private void triggerObservers() {
        setChanged();
        notifyObservers(data);  // notify max data capacity
    }

    public ArrayList<CanMessage> getData() {
        return data;
    }

    public void setData(CanMessage[] data) {
        updateBuffer(data);
    }
}
