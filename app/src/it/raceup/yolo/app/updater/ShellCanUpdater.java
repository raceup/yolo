package it.raceup.yolo.app.updater;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.updaters.FileUpdater;
import it.raceup.yolo.models.data.CanMessage;
import it.raceup.yolo.models.kvaser.message.FromKvaserMessage;

import java.util.ArrayList;
import java.util.Observable;

import static it.raceup.yolo.models.data.CanMessage.getLineHeader;
import static it.raceup.yolo.utils.Misc.getLineSeparator;

/**
 * Updates with CAN data
 */
public class ShellCanUpdater extends ShellCsvUpdater {
    public static final String DEFAULT_FOLDER = FileUpdater.DEFAULT_FOLDER + "/can/";
    private static final String[] COLUMNS = new String[]{
            "Time", "ID", "Flags", "Dlc", "byte 1", "byte 2", "byte 3", "byte 4", "byte 5", "byte 6", "byte 7", "byte 8"
    };
    private final String SEPARATOR = "|";

    public ShellCanUpdater(boolean logToShell, boolean logToFile) {
        super("CAN", COLUMNS, DEFAULT_FOLDER, logToShell, logToFile);
    }

    private void update(ArrayList<CanMessage> messages) {
        if (this.isLogToShell()) {
            String header = getLineHeader(SEPARATOR);
            log(getLineSeparator(header));  // log to shell
        }

        for (CanMessage message : messages) {
            if (this.isLogToShell()) {
                log(message.getLine(SEPARATOR));  // to std output
            }

            String[] columns = getColumns(message);

            if (this.isLogToFile()) {
                writeLog(columns);  // to file
            }
        }
    }

    private String[] getColumns(CanMessage message) {
        String[] columns = new String[COLUMNS.length];  // same as header

        columns[0] = Long.toString(message.getTime());  // basic info
        columns[1] = Integer.toString(message.getId());
        columns[2] = Integer.toString(message.getFlags());
        columns[3] = Integer.toString(message.getDlc());

        byte[] data = message.getData();
        columns[4] = Byte.toString(data[0]);  // data
        columns[5] = Byte.toString(data[1]);
        columns[6] = Byte.toString(data[2]);
        columns[7] = Byte.toString(data[3]);
        columns[8] = Byte.toString(data[4]);
        columns[9] = Byte.toString(data[5]);
        columns[10] = Byte.toString(data[6]);
        columns[11] = Byte.toString(data[7]);

        return columns;
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
            new YoloException("cannot updateWith car", e, ExceptionType.KVASER)
                    .print();
        }
    }
}
