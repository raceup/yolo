package it.raceup.yolo.models.data;

import java.util.Random;

public class Parser {
    private static final int index_diff_1[] = new int[]{0x283, 0x284, 0x287,
            0x288};
    private static final int index_diff_2[] = new int[]{0x285, 0x286, 0x289,
            0x28A};
    private int id;
    private byte[] data;
    private int motorId;
    private double motorValue;
    private Type dataType;

    public Parser(int id, byte[] data) {
        this.id = id;
        this.data = data;

        parse();
    }

    private void parse() {
        parseMotorId();
        parseValue();
        parseType();
    }

    private void parseMotorId() {
        /* todo uncomment to release for(int i = 0; i < 4; i++) {
            if(id == index_diff_1[i] || id == index_diff_2[i]) {
                return i;
            }

        }

        return 0;*/
        motorId = new Random().nextInt(4);
    }

    public int getMotorId() {
        return motorId;
    }

    public void parseValue() {
        if (getValueType() == 1) {
            // read amk 1
        } else {
            // read amk 2
        }

        motorValue = new Random().nextInt(10) / 10.0;  // todo parse
    }

    public int getValueType() {
        for (int i = 0; i < 4; i++) {
            if (id == index_diff_1[i]) {
                return 1;
            }

            if (id == index_diff_2[i]) {
                return 2;
            }
        }
        return 0;
    }

    public void parseType() {
        // todo parse
        Type[] all = Type.values();  // get the array
        int randomNum = new Random().nextInt(all.length);  // random int
        dataType = all[randomNum];  // get the random obj
    }

    public Type getDataType() {
        return dataType;
    }

    public double getMotorValue() {
        return motorValue;
    }

    public Raw buildRawData() {
        return new Raw(
                getMotorValue(),
                getMotorId(),
                getDataType()
        );
    }
}
