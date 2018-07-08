package it.raceup.yolo.models.data;

import it.raceup.yolo.utils.Utils;

import java.util.ArrayList;
import java.util.Random;

public class Parser extends CanMessage {
    private static final int index_diff_1[] = new int[]{
            0x283, 0x284, 0x287, 0x288
    };
    private static final int index_diff_2[] = new int[]{
            0x285, 0x286, 0x289, 0x28A
    };
    private int motorId;
    private static final double TORQUE_CURRENT = 107.2 / 16384;
    private ArrayList<Raw> parsedData = new ArrayList<>();

    public Parser(int id, byte[] data) {
        super(id, 0, 0, 0, data);
        parse();
    }

    private void parse() {
        parseMotorId();
        parseValues();
    }

    private void parseMotorId() {
        /* todo uncomment for release for(int i = 0; i < 4; i++) {
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

    public void parseValues() {
        if (getValueType() == 1) {
            readMotor1();
        } else {
            readMotor2();
        }
    }

    private void readMotor1() {
        parsedData.add(
                new Raw(
                        Utils.getBit(getData()[1], 0),
                        getMotorId(),
                        Type.SYSTEM_READY
                )
        );
        parsedData.add(
                new Raw(
                        Utils.getBit(getData()[1], 1),
                        getMotorId(),
                        Type.ERROR
                )
        );
        parsedData.add(
                new Raw(
                        Utils.getBit(getData()[1], 2),
                        getMotorId(),
                        Type.WARNING
                )
        );
        parsedData.add(
                new Raw(
                        Utils.getBit(getData()[1], 3),
                        getMotorId(),
                        Type.QUIT_DC_ON
                )
        );
        parsedData.add(
                new Raw(
                        Utils.getBit(getData()[1], 4),
                        getMotorId(),
                        Type.DC_ON
                )
        );
        parsedData.add(
                new Raw(
                        Utils.getBit(getData()[1], 5),
                        getMotorId(),
                        Type.QUIT_INVERTER_ON
                )
        );
        parsedData.add(
                new Raw(
                        Utils.getBit(getData()[1], 6),
                        getMotorId(),
                        Type.INVERTER_ON
                )
        );
        parsedData.add(
                new Raw(
                        Utils.getBit(getData()[1], 7),
                        getMotorId(),
                        Type.DERATING
                )
        );
        parsedData.add(
                new Raw(
                        getData()[2] | getData()[3] << 8,
                        getMotorId(),
                        Type.ACTUAL_VELOCITY
                )
        );
        parsedData.add(
                new Raw(
                        (getData()[4] | getData()[5] << 8) * TORQUE_CURRENT,
                        getMotorId(),
                        Type.TORQUE_CURRENT
                )
        );
        parsedData.add(
                new Raw(
                        getData()[6] | getData()[7] << 8,
                        getMotorId(),
                        Type.MAGNETIZING_CURRENT
                )
        );
    }

    private void readMotor2() {
        parsedData.add(
                new Raw(
                        (getData()[0] | getData()[1] << 8) / 10,
                        getMotorId(),
                        Type.TEMPERATURE_MOTOR
                )
        );
        parsedData.add(
                new Raw(
                        (getData()[2] | getData()[3] << 8) / 10,
                        getMotorId(),
                        Type.TEMPERATURE_INVERTER
                )
        );
        parsedData.add(
                new Raw(
                        (getData()[4] | getData()[5] << 8) / 10,
                        getMotorId(),
                        Type.TEMPERATURE_IGBT
                )
        );
        parsedData.add(
                new Raw(
                        getData()[6] | getData()[7] << 8,
                        getMotorId(),
                        Type.ERROR_INFO
                )
        );
    }

    public int getValueType() {
        for (int i = 0; i < 4; i++) {
            if (getId() == index_diff_1[i]) {
                return 1;
            }

            if (getId() == index_diff_2[i]) {
                return 2;
            }
        }
        return 0;
    }

    public Raw[] getParsedData() {
        return parsedData.toArray(new Raw[parsedData.size()]);
    }
}
