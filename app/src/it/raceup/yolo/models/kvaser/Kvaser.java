package it.raceup.yolo.models.kvaser;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.Logger;
import it.raceup.yolo.logging.ShellLogger;
import it.raceup.yolo.models.data.CanMessage;
import it.raceup.yolo.models.kvaser.message.CanCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import static core.Canlib.*;

/**
 * Common interface for Kvaser devices
 */
// todo docs
public abstract class Kvaser extends RawKvaser implements Logger, Runnable,
        Observer {
    private static final Map<String, Integer> CAN_BITRATE_TO_CANLIB_VERSION =
            new HashMap<>();

    static {
        CAN_BITRATE_TO_CANLIB_VERSION.put("10k", canBITRATE_10K);
        CAN_BITRATE_TO_CANLIB_VERSION.put("50k", canBITRATE_50K);
        CAN_BITRATE_TO_CANLIB_VERSION.put("62k", canBITRATE_62K);
        CAN_BITRATE_TO_CANLIB_VERSION.put("83k", canBITRATE_83K);
        CAN_BITRATE_TO_CANLIB_VERSION.put("100k", canBITRATE_100K);
        CAN_BITRATE_TO_CANLIB_VERSION.put("125k", canBITRATE_125K);
        CAN_BITRATE_TO_CANLIB_VERSION.put("250k", canBITRATE_250K);
        CAN_BITRATE_TO_CANLIB_VERSION.put("500k", canBITRATE_500K);
        CAN_BITRATE_TO_CANLIB_VERSION.put("1m", canBITRATE_1M);
    }
    private final ShellLogger logger;

    public Kvaser() {
        this("KVASER");
    }

    public Kvaser(String tag) {
        super(128);
        logger = new ShellLogger(tag);
    }

    public void run() {
        boolean keepRunning = !Thread.interrupted();
        while (keepRunning) {
            try {
                CanMessage[] messages = read();
                setData(messages);
                keepRunning = !Thread.interrupted();
            } catch (Exception e) {
                keepRunning = false;
                new YoloException("cannot loop kvaser", e, ExceptionType
                        .KVASER).print();
            }
        }
    }

    public int getCanlibVersionOfBitrate(String canBitrate) {
        try {
            return CAN_BITRATE_TO_CANLIB_VERSION.get(canBitrate);
        } catch (Exception e) {
            return canERR_PARAM;
        }
    }

    public abstract boolean setup(String canBitrate);

    public abstract CanMessage[] read();

    public abstract boolean write(int id, byte[] data, int flags);

    public boolean write(CanCommand command) {
        return write(
                command.getId(),
                command.getData(),
                command.getFlag()
        );
    }

    public abstract void close();

    public void log(String message) {
        logger.log(message);
    }

    public void log(Exception e) {
        logger.log(e);
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            write((CanCommand) o);
        } catch (Exception e) {
            new YoloException("cannot update kvaser", e, ExceptionType.KVASER)
                    .print();
        }
    }
}
