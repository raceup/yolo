package it.raceup.yolo.models.kvaser.message;

/**
 * Command to send via CAN
 */
public class CanCommand {
    private final int id;
    private final String message;
    private final int flag;

    public CanCommand(int id, int message, int flag) {
        this(
                id,
                Integer.toHexString(message),
                flag
        );
    }

    public CanCommand(int id, String message, int flag) {
        this.id = id;
        this.message = message;
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public int getFlag() {
        return flag;
    }

    public byte[] getData() {
        // todo convert hex to byte
        return new byte[8];
    }
}
