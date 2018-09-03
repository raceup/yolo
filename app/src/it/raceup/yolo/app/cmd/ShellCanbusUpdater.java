package it.raceup.yolo.app.cmd;

import it.raceup.yolo.app.FileUpdater;
import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.data.CanMessage;
import it.raceup.yolo.models.kvaser.message.FromKvaserMessage;

import java.util.ArrayList;
import java.util.Observable;

import static it.raceup.yolo.models.data.CanMessage.getLineHeader;
import static it.raceup.yolo.utils.Utils.getLineSeparator;

/**
 * Updates with CAN data
 */
public class ShellCanbusUpdater extends FileUpdater {
    public ShellCanbusUpdater() {
        super("CAN UPDATER");
        writeLog(CanMessage.getLineHeader(","));
    }

    private void update(ArrayList<CanMessage> data) {
        String header = getLineHeader("|");
        log(header);
        log(getLineSeparator(header));

        for (CanMessage message : data) {
            log(message.getLine("|"));  // to std output
            writeLog(message.getLine(","));  // to file
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            FromKvaserMessage message = new FromKvaserMessage(o);
            ArrayList<CanMessage> messages = message.getAsCanMessages();
            if (messages != null) {
                update(messages);
            }
        } catch (Exception e) {
            new YoloException("cannot update car", e, ExceptionType.KVASER)
                    .print();
        }
    }
}
