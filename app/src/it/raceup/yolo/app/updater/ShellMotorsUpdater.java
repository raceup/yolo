package it.raceup.yolo.app.updater;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.updaters.FileUpdater;
import it.raceup.yolo.models.car.Motor;
import it.raceup.yolo.models.car.Motors;
import it.raceup.yolo.models.data.Raw;

import java.util.Observable;

/**
 * Updates with car data
 */
public class ShellMotorsUpdater extends ShellCsvUpdater {
    public static final String DEFAULT_FOLDER = FileUpdater.DEFAULT_FOLDER + "/motors/";
    private static final String[] COLUMNS = new String[]{
            "motor_tag",
            "system_ready", // 1
            "error",
            "warning",
            "quit_dc_on",
            "dc_on", //5
            "quit_inverter_on",
            "inverter_on",
            "derating", //8
            "actual_velocity",
            "torque_current", //10
            "magnetizing_current",
            "temperature_motor", //12
            "temperature_inverter",
            "temperature_igbt",
            "error_info", //15
            "sp_inverter_on",//16
            "sp_dc_on",
            "sp_enable",
            "sp_error_reset",//19
            "target_velocity", //20
            "pos_torque_limit",
            "neg_torque_limit",
            "time"//23
    };

    private final String SEPARATOR = ",";
    private static final String[] log = new String[COLUMNS.length];
    public ShellMotorsUpdater(boolean logToShell, boolean logToFile) {
        super("MOTORS", COLUMNS, DEFAULT_FOLDER, logToShell, logToFile);
        for (int i = 0; i < log.length; i++) {
            log[i] = "0";
        }
    }
    
    public void update(Motors motors){
        for(int i = 0; i < motors.numberOfMotors(); i++){
            update(motors.get(i));
        }
    }
    
    private void update(Motor motor) {
        String message = motor.toString();

        if (this.isLogToShell()) {
            String[] lines = message.split("\n");
            for (String line : lines) {
                log(line);  // to std output
            }
        }

        if (this.isLogToFile()) {
            log[0] = motor.getTag();
            boolean[] booleanFlagsValue = motor.getBooleanFlagsValue();
            for (int i = 0; i < 8; i++) {
                log[i+1] = Boolean.toString(booleanFlagsValue[i]);
            }
            double[] flagsValue = motor.getDoubleFlagsValue();
            log[9] = Double.toString(flagsValue[0]);
            log[10] = Double.toString(flagsValue[1]);
            log[11] = Double.toString(flagsValue[2]);
            double[] temeperatureValue = motor.getTemeperatureValue();
            log[12] = Double.toString(temeperatureValue[0]);
            log[13] = Double.toString(temeperatureValue[1]);
            log[14] = Double.toString(temeperatureValue[2]);
            log[15] = Double.toString(temeperatureValue[3]);
            boolean[] booleanSetPointValue = motor.getBooleanSetPointValue();
            log[16] = Boolean.toString(booleanSetPointValue[0]);
            log[17] = Boolean.toString(booleanSetPointValue[1]);
            log[18] = Boolean.toString(booleanSetPointValue[2]);
            log[19] = Boolean.toString(booleanSetPointValue[3]);
            double[] doubleSetPointValue = motor.getDoubleSetPointValue();
            log[20] = Double.toString(doubleSetPointValue[0]);
            log[21] = Double.toString(doubleSetPointValue[1]);
            log[22] = Double.toString(doubleSetPointValue[2]);
            log[23] = Double.toString(motor.getTime());
            writeLog(log);
           }
    }

    private void update(Raw motor){
        System.out.println(motor.getType());
        System.out.println(motor.getMotor());
        System.out.println(motor.getRaw());
        System.out.println();
        System.out.println();

    }

    private void update(Motor[] motors) {
        for (Motor motor : motors) {
            update(motor);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            this.update((Motor[]) o);
        } catch (Exception e) {
            new YoloException("cannot updateWith car", e, ExceptionType.KVASER)
                    .print();
        }
    }
}
