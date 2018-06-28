package it.raceup.yolo.models;

import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.motor.Flags;
import it.raceup.yolo.models.motor.SetPoint;
import it.raceup.yolo.models.motor.Temperature;

public class Motor {
    public Flags flags;
    public SetPoint setPoint;
    public Temperature temperature;
    public String tag;

    public Motor(String tag) {
        this.tag = tag;
    }

    public void update(Raw data) {
        if (data.isTemperature()) {
            temperature.update(data);
        } else if (data.isFlag()) {
            flags.update(data);
        } else if (data.isSetPoint()) {
            setPoint.update(data);
        }
    }
}
