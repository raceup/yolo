package it.raceup.yolo.models.kvaser;

import core.Canlib;
import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import obj.CanlibException;
import obj.Handle;
import obj.Message;

public class CanDump {
    private int canBitrate;

    public CanDump() throws YoloException {
        this(Canlib.canBITRATE_1M);
    }

    public CanDump(int canBitrate) throws YoloException {
        this.canBitrate = canBitrate;
        try {
            setup();
        } catch (Exception e) {
            throw new YoloException(e.getMessage(), ExceptionType.CANLIB);
        }
    }

    /*
     * Waits for messages and prints them to the screen
     */
    private static void DumpMessageLoop(Handle handle) {
        boolean finished = false;

        System.out.println("Channel 0 opened.");
        System.out.println("   ID    DLC DATA                      Timestamp");

        while (!finished) {
            try {
                while (handle.hasMessage()) {
                    Message m = handle.read();
                    dumpMessage(m);
                }
            } catch (CanlibException e) {
                e.printStackTrace();
                System.err.println("An error occurred while reading messages");
                finished = true;
            }
        }
    }

    /*
     * Prints a received message
     */
    private static void dumpMessage(Message m) {
        if (m.isErrorFrame()) {
            System.out.println("***Error frame received***");
        } else {
            String idString = String.format("%8s", Integer.toBinaryString(m.id)).replace(' ', '0');
            System.out.printf("%s  %d  %2d %2d %2d %2d %2d %2d %2d %2d   %d\n",
                    idString, m.length, m.data[0], m.data[1], m.data[2], m.data[3], m.data[4],
                    m.data[5], m.data[6], m.data[7], m.time);
        }
    }

    private void setup() throws obj.CanlibException {
        // Setting up the channel and going on bus
        Handle handle = new Handle(0);
        handle.setBusParams(this.canBitrate, 0, 0, 0, 0, 0);
        handle.busOn();

        // Start dumping messages
        DumpMessageLoop(handle);

        // Going off bus and closing channel
        handle.busOff();
        handle.close();
    }
}
