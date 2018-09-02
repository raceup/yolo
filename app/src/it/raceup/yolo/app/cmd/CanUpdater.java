package it.raceup.yolo.app.cmd;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.data.CanMessage;

import java.util.ArrayList;
import java.util.Observable;

import static it.raceup.yolo.models.data.CanMessage.getLineHeader;

/**
 * Updates with CAN data
 */
public class CanUpdater extends Updater {
    public CanUpdater() {
        super();
        writeLog(CanMessage.getLineHeader(","));
    }

    private void update(ArrayList<CanMessage> data) {
        log(getLineHeader("|"));

        for (CanMessage message : data) {
            log(message.getLine("|"));  // to std output
            writeLog(message.getLine(","));  // to file
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            this.update((ArrayList<CanMessage>) o);
        } catch (Exception e) {
            new YoloException("cannot update car", e, ExceptionType.KVASER)
                    .print();
        }
    }
}
