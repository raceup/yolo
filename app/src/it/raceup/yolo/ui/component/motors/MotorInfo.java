package it.raceup.yolo.ui.component.motors;

import it.raceup.yolo.ui.component.table.TablePanel;

import static it.raceup.yolo.models.data.Type.*;

public class MotorInfo extends TablePanel {
    private static final String[] LABELS =
            new String[]{
                    SYSTEM_READY.toString(),
                    it.raceup.yolo.models.data.Type.ERROR.toString(),
                    DC_ON.toString(),
                    INVERTER_ON.toString(),
                    ACTUAL_VELOCITY.toString(),
                    TORQUE_CURRENT.toString(),
                    TEMPERATURE_MOTOR.toString(),
                    TEMPERATURE_INVERTER.toString(),
                    FRONT_SUSPENSION_POTENTIOMETER.toString(),
                    REAR_SUSPENSION_POTENTIOMETER.toString()
            };

    public MotorInfo(String tag) {
        super(LABELS, tag);
    }
}
