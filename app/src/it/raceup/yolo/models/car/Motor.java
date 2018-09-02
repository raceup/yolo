package it.raceup.yolo.models.car;

import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.models.motor.Flags;
import it.raceup.yolo.models.motor.SetPoint;
import it.raceup.yolo.models.motor.Temperature;

import java.util.stream.Stream;

import static it.raceup.yolo.models.data.Raw.*;

public class Motor {
    private Flags flags = new Flags();
    private SetPoint setPoint = new SetPoint();
    private Temperature temperature = new Temperature();
    private String tag;

    public static String getLineHeader(String separator) {
        String[] labels = Stream.of(Raw.ALL)
                .map(Type::toString)
                .toArray(String[]::new);

        return getLine(labels, separator);
    }

    public Motor(String tag) {
        this.tag = tag;
    }

    // todo oop
    public void update(Raw data) {
        if (data.isTemperature()) {
            temperature.update(data);
        } else if (data.isFlag()) {
            flags.update(data);
        } else if (data.isSetPoint()) {
            setPoint.update(data);
        }
    }

    // todo oop
    public double get(Type type) {
        if (isTemperature(type)) {
            return temperature.get(type);
        } else if (isFlag(type)) {
            return flags.get(type);
        } else if (isSetPoint(type)) {
            return setPoint.get(type);
        }

        return 0;  // todo exception
    }

    public static String getLine(String[] types, String separator) {
        StringBuilder out = new StringBuilder();
        for (String type : types) {
            out.append(String.format("%-22s" + separator, type));
        }
        return out.toString();
    }

    public String getTag() {
        return tag;
    }

    public String getLine(String separator) {
        String[] values = Stream.of(Raw.ALL)  // todo test
                .map(this::get)
                .map(x -> Double.toString(x))
                .toArray(String[]::new);

        return getLine(values, separator);
    }


    public String toString(Type[] types) {
        StringBuilder out = new StringBuilder();

        for (Type type : types) {
            out.append(String.format("%-20s: ", type.toString()));
            out.append(String.format("%-8s| ", get(type)));
        }

        return out.toString();
    }

    public String toStringTemperatures() {
        return toString(Raw.TEMPERATURES);
    }

    public String toStringFlags() {
        return toString(Raw.FLAGS);
    }

    public String toStringSetPoints() {
        return toString(Raw.SET_POINTS);
    }

    @Override
    public String toString() {
        String out = "Motor " + tag + ":\n";
        out += "\tTEMPERATURES:\n";
        out += "\t\t" + toStringTemperatures() + "\n";

        out += "\tFLAGS:\n";
        out += "\t\t" + toStringFlags() + "\n";

        out += "\tSET POINTS:\n";
        out += "\t\t" + toStringSetPoints() + "\n";

        return out;
    }

}
