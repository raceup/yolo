package it.raceup.yolo.models.data;

public enum Type {
    SYSTEM_READY,
    ERROR,
    WARNING,
    QUIT_DC_ON,
    DC_ON,
    QUIT_INVERTER_ON,
    INVERTER_ON,
    DERATING,
    ACTUAL_VELOCITY,
    TORQUE_CURRENT,
    MAGNETIZING_CURRENT,
    TEMPERATURE_MOTOR,
    TEMPERATURE_INVERTER,
    TEMPERATURE_IGBT,
    ERROR_INFO,
    SP_INVERTER_ON,
    SP_DC_ON,
    SP_ENABLE,
    SP_ERROR_RESET,
    TARGET_VELOCITY,
    POS_TORQUE_LIMIT,
    NEG_TORQUE_LIMIT,
    LOG_STATUS,
    ACCELERATION,
    GYRO,
    QUATERNION,
    ROLL_PITCH_YAW,
    VELOCITY,
    GPS_LATITUDE_LONGITUDE,
    THROTTLE,
    BRAKE,
    STEERINGWHEEL;

    @Override
    public String toString() {
        if (this == SYSTEM_READY) {
            return "System ready";
        } else if (this == ERROR) {
            return "Error";
        } else if (this == WARNING) {
            return "Warning";
        } else if (this == QUIT_DC_ON) {
            return "Quit DC ON";
        } else if (this == DC_ON) {
            return "DC ON";
        } else if (this == QUIT_INVERTER_ON) {
            return "Quit Inverter ON";
        } else if (this == INVERTER_ON) {
            return "Inverter ON";
        } else if (this == DERATING) {
            return "Derating";
        } else if (this == ACTUAL_VELOCITY) {
            return "Actual Velocity";
        } else if (this == TORQUE_CURRENT) {
            return "Torque Current";
        } else if (this == MAGNETIZING_CURRENT) {
            return "Magnetizing Current";
        } else if (this == TEMPERATURE_MOTOR) {
            return "Temperature MotorFrame";
        } else if (this == TEMPERATURE_INVERTER) {
            return "Temperature Inverter";
        } else if (this == TEMPERATURE_IGBT) {
            return "Temperature IGBT";
        } else if (this == ERROR_INFO) {
            return "Error Info";
        } else if (this == SP_INVERTER_ON) {
            return "Inverter ON";
        } else if (this == SP_DC_ON) {
            return "Enable";
        } else if (this == SP_ENABLE) {
            return "Enable";
        } else if (this == SP_ERROR_RESET) {
            return "Error reset";
        } else if (this == TARGET_VELOCITY) {
            return "Target Velocity";
        } else if (this == POS_TORQUE_LIMIT) {
            return "Pos Torque Limit";
        } else if (this == NEG_TORQUE_LIMIT) {
            return "Neg Torque Limit";
        } else if (this == LOG_STATUS) {
            return "Log Status";
        } else if (this == ACCELERATION) {
            return "Acceleration";
        } else if (this == GYRO) {
            return "Gyroscope";
        } else if (this == QUATERNION) {
            return "Quaternion";
        } else if (this == ROLL_PITCH_YAW) {
            return "Roll Pitch Yaw";
        } else if (this == VELOCITY) {
            return "Velocity";
        } else if (this == GPS_LATITUDE_LONGITUDE) {
            return "GPS coordinates";
        } else if (this == THROTTLE) {
            return "Throttle";
        } else if (this == BRAKE) {
            return "Brake";
        } else if (this == STEERINGWHEEL) {
            return "Steering wheel";
        }
        return "DNF";
    }
}