package it.raceup.yolo.models.car;

import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.models.motor.Flags;
import it.raceup.yolo.models.motor.SetPoint;
import it.raceup.yolo.models.motor.Temperature;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.raceup.yolo.models.data.Raw.*;

public class Motor {
    private final Flags flags = new Flags();
    private final SetPoint setPoint = new SetPoint();
    private final Temperature temperature = new Temperature();
    private final String tag;
    private double time;

    public Motor(String tag) {
        this.tag = tag;
    }

    public Motor(String tag, double time) {
        this.tag = tag;
        this.time = time;
    }

    public static String getLineHeader(String separator) {
        List<String> values = Stream.of(Raw.ALL)
                .map(Type::toString)
                .collect(Collectors.toList());

        values.add(0, "Tag");

        return getLine(values.toArray(new String[values.size()]), separator);
    }

    public static String getLine(String[] types, String separator) {
        StringBuilder out = new StringBuilder();
        for (String type : types) {
            out.append(String.format("%-22s" + separator, type));
        }
        return out.toString();
    }

    // todo oop
    public void update(Raw data) {
        if (data.isTemperature()) {
            temperature.update(data);
            setTime(data.getTime());
        } else if (data.isFlag()) {
            flags.update(data);
            setTime(data.getTime());
        } else if (data.isSetPoint()) {
            setPoint.update(data);
            setTime(data.getTime());
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

        return 0;  // todo better exception or DNF
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getTag() {
        return tag;
    }

    public String getLine(String separator) {
        List<String> values = Stream.of(Raw.ALL)
                .map(this::get)
                .map(x -> Double.toString(x))
                .collect(Collectors.toList());

        values.add(0, getTag());

        return getLine(values.toArray(new String[values.size()]), separator);
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

    public double[] getTemeperatureValue() {
        return temperature.getValues();
    }

    public boolean[] getBooleanFlagsValue() {
        return flags.getBooleanValue();
    }

    public double[] getDoubleFlagsValue() {
        return flags.getFlagsValue();
    }

    public boolean[] getBooleanSetPointValue() {
        return setPoint.getBooleanSetPointValue();
    }

    public double[] getDoubleSetPointValue() {
        return setPoint.getDoubleSetPointValue();
    }

    public double getTime() {
        return time;
    }

    @Override
    public String toString() {
        String out = "MotorFrame " + tag + ":\n";
        out += "\tTEMPERATURES:\n";
        out += "\t\t" + toStringTemperatures() + "\n";

        out += "\tFLAGS:\n";
        out += "\t\t" + toStringFlags() + "\n";

        out += "\tSET POINTS:\n";
        out += "\t\t" + toStringSetPoints() + "\n";

        return out;
    }

}
