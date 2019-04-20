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
    private final int MAX_BUFFER_DIMENSION = 100 * 42; // every 30 sec open write close file 3000 for 30 sec on big can
    private Runnable runner;
    private ArrayList<CanMessage> buffer = new ArrayList<>();
    private ArrayList<CanMessage> copyOfBuffer;

    public ShellCanUpdater(boolean logToShell, boolean logToFile) {
        super("CAN", COLUMNS, DEFAULT_FOLDER, logToShell, logToFile);
    }

    private void update(ArrayList<CanMessage> messages) {
        buffer.addAll(messages);
        if (buffer.size() >= MAX_BUFFER_DIMENSION) {
            copyOfBuffer = new ArrayList<>(buffer);
            buffer.clear();
            Thread writer = new Thread(runner);
            writer.start();
            System.out.println("loggato");
        }

        runner = new Runnable() {
            @Override
            public void run() {
                for (CanMessage insideBuffer : copyOfBuffer) {
                    String[] columns = getColumns(insideBuffer);
                    writeLog(columns);
                    System.out.println("loggato ma davvero");
                }
            }
        };
    }

    public void update(CanMessage message) {
        String[] columns = getColumns(message);

        if (this.isLogToFile()) {
            writeLog(columns);  // to file
        }
    }

    private String[] getColumns(CanMessage message) {
        String[] columns = new String[COLUMNS.length];  // same as header

        columns[0] = Long.toString(message.getTime());  // basic info
        columns[1] = Integer.toString(message.getId());
        columns[2] = Integer.toString(message.getFlags());
        columns[3] = Integer.toString(message.getDlc());


            byte[] data = message.getData();
        for(int i = 0; i< columns.length - 4; i++){
            columns[i+4] = "0";
        }

            for(int i = 0; i< data.length; i++){
                columns[i +4] = Byte.toString(data[i]);
            }
            /*
            columns[4] = Byte.toString(data[0]);  // data
            columns[5] = Byte.toString(data[1]);
            columns[6] = Byte.toString(data[2]);
            columns[7] = Byte.toString(data[3]);
            columns[8] = Byte.toString(data[4]);
            columns[9] = Byte.toString(data[5]);
            columns[10] = Byte.toString(data[6]);
            columns[11] = Byte.toString(data[7]);
            */

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
            e.printStackTrace();
            new YoloException("cannot updateWith car", e, ExceptionType.KVASER)
                    .print();
        }
    }
}
