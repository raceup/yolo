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

    public static String getLineHeader() {
        String[] labels = Stream.of(Raw.ALL)  // todo test
                .map(Type::toString)
                .toArray(String[]::new);

        StringBuilder line = new StringBuilder(getLine(labels));

        int length = line.length();
        line.append("\n");
        for (int i = 0; i < length; i++) {
            line.append("-");
        }
        line.append("\n");

        return line.toString();
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
            temperature.get(type);
        } else if (isFlag(type)) {
            flags.get(type);
        } else if (isSetPoint(type)) {
            setPoint.get(type);
        }

        return 0;  // todo exception
    }

    public static String getLine(String[] types) {
        StringBuilder out = new StringBuilder();
        for (String type : types) {
            out.append(String.format("|%-22s|", type));
        }
        return out.toString();
    }

    public String getTag() {
        return tag;
    }

    public String getLine() {
        String[] values = Stream.of(Raw.ALL)  // todo test
                .map(this::get)
                .map(x -> Double.toString(x))
                .toArray(String[]::new);

        return getLine(values);
    }


    public String toString(Type[] types) {
        StringBuilder out = new StringBuilder();

        for (Type type : types) {
            out.append(String.format("\t\t%-40s: ", type.toString()));
            out.append(String.format("%-40s: ", get(type)));
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
        out += toStringTemperatures() + "\n";

        out += "\tFLAGS:\n";
        out += toStringFlags() + "\n";

        out += "\tSET POINTS:\n";
        out += toStringSetPoints() + "\n";

        return out;
    }

}
