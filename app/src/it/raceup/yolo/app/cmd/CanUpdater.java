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
    private void update(ArrayList<CanMessage> data) {
        log(getLineHeader());

        for (CanMessage message : data) {
            String line = message.getLine();

            log(line);  // to std output
            writeLog(line);  // to file
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
