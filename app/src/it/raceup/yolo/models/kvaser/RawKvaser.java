package it.raceup.yolo.models.kvaser;

import it.raceup.yolo.models.data.CanMessage;

import java.util.Observable;

public abstract class RawKvaser extends Observable {
    private CanMessage[] data;

    public void setData(CanMessage[] data) {
        this.data = data;
        setChanged();
        notifyObservers(data);
    }
}
