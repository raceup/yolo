package it.raceup.yolo.app.cmd;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.data.CanMessage;
import it.raceup.yolo.models.data.Parser;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Updates with CAN data
 */
public class CanUpdater extends Updater {
    private void update(Raw data) {
        // todo
    }

    private void update(ArrayList<CanMessage> data) {
        for (CanMessage message : data) {
            Raw[] packets = new Parser(message).getParsedData();
            for (Raw packet : packets) {
                update(packet);
            }
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

    // todo use
    private void updateLog(int motor, Type type, Double value) {
        if (logger != null) {
            String message = Integer.toString(motor) + ": " + value.toString() +
                    " " + type.toString();
            logger.log(logger.getMessage(message, true, true));
        }
    }
}
