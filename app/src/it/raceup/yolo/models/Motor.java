package it.raceup.yolo.models;

import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.models.motor.Flags;
import it.raceup.yolo.models.motor.SetPoint;
import it.raceup.yolo.models.motor.Temperature;

import static it.raceup.yolo.models.data.Raw.*;

public class Motor {
    public Flags flags = new Flags();
    public SetPoint setPoint = new SetPoint();
    public Temperature temperature = new Temperature();
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

    public double get(Type type) {
        if (isTemperature(type)) {
            temperature.get(type);
        } else if (isFlag(type)) {
            flags.get(type);
        } else if (isSetPoint(type)) {
            setPoint.get(type);
        }

        return 0;  // todo exception
    }
}
