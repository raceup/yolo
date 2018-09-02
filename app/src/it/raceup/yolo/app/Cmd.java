package it.raceup.yolo.app;

import it.raceup.yolo.control.Hal;
import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.logging.FileLogger;
import it.raceup.yolo.logging.ShellLogger;
import it.raceup.yolo.models.car.Car;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.models.kvaser.BlackBird;
import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static it.raceup.yolo.utils.Utils.getTimeNow;

/**
 * Command line interface for telemetry (yolo-cli). Setups connection with
 * Kvaser and outputs current car data as soon as new data has arrived.
 */
public class Cmd extends ShellLogger {
    private static final String logFile = System.getProperty("user.dir") +
            "/logs/" + getTimeNow("YYYY-MM-dd_HH-mm-ss") + ".log";
    private CommandLine cmd;
    private ArrayList<String> options;
    private Hal hal;
    private FileLogger logger;

    public static void main(String[] args) {
        Cmd cmd = new Cmd();
        cmd.parseArgs(args);

        try {
            cmd.setup();
            cmd.start();
        } catch (Exception e) {
            cmd.log(e);
        } finally {
            cmd.close();
        }
    }

    public void parseArgs(String[] args) {
        cmd = getCmdParser(args);
        options = parseCmdOptions();
        System.out.println(options.get(0));
    }

    public void setup() {
        hal = new Hal(
                new Car(),
                new BlackBird(options.get(0))
        );

        try {
            hal.setup(options.get(1));
        } catch (Exception e) {
            log(e);
            System.exit(1);
        }

        try {
            FileLogger.create(logFile);
            logger = new FileLogger(logFile);
        } catch (Exception e) {
            new YoloException("cannot create log file", ExceptionType
                    .UNKNOWN).print();
        }
    }

    public void start() {
        hal.start();

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, 1000);
    }

    public void close() {
        hal.close();
    }

    private Options getCmdOptions() {
        Options options = new Options();

        Option ip = new Option("ip", "ip-kvaser", true, "IP of " +
                "Blackbird");
        ip.setRequired(true);
        options.addOption(ip);

        Option canBitrate = new Option("bitrate", "can-bitrate", true,
                "Bitrate of CAN. Possible options are: [10k, 50k, 62k, 83k, 100k, 125k, 250k, 500k, 1m]");
        canBitrate.setRequired(true);
        options.addOption(canBitrate);

        Option view = new Option("view", "view", true, "Update screen " +
                "with CAN messages, car model. Possible options are  [can, car]");
        ip.setRequired(true);
        options.addOption(ip);

        return options;
    }

    private CommandLine getCmdParser(String[] args) {
        Options options = getCmdOptions();
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            log(e);
            formatter.printHelp("utility-name", options);
            System.exit(1);
        }

        return cmd;
    }

    private ArrayList<String> parseCmdOptions() {
        ArrayList<String> options = new ArrayList<>();
        options.add(cmd.getOptionValue("ip-kvaser"));
        options.add(cmd.getOptionValue("can-bitrate"));
        options.add(cmd.getOptionValue("view"));
        return options;
    }

    private void update() {
        for (int i = 0; i < hal.numberOfMotors(); i++) {
            HashMap<Type, Double> info = hal.getInfo(i);
            for (Type type : info.keySet()) {
                Double value = info.get(type);

                updateScreen(i, type, value);
                updateLog(i, type, value);
            }
        }
    }

    private void updateScreen(int motor, Type type, double value) {
        //
    }

    private void updateLog(int motor, Type type, Double value) {
        if (logger != null) {
            String message = Integer.toString(motor) + ": " + value.toString() +
                    " " + type.toString();
            logger.log(logger.getMessage(message, true, true));
        }
    }
}
