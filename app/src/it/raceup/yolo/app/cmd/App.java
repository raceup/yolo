package it.raceup.yolo.app.cmd;

import it.raceup.yolo.app.KvaserApp;
import it.raceup.yolo.app.updater.*;
import it.raceup.yolo.control.Hal;
import it.raceup.yolo.models.car.Driver;
import it.raceup.yolo.models.car.Imu;
import it.raceup.yolo.models.car.Motors;
import it.raceup.yolo.models.kvaser.BlackBird;
import it.raceup.yolo.models.kvaser.FakeBlackBird;
import org.apache.commons.cli.*;

import java.util.ArrayList;

/**
 * Command line interface for telemetry (yolo-cli). Setups connection with
 * Kvaser and outputs current car data as soon as new data has arrived.
 */
public class App extends KvaserApp {
    private CommandLine cmd;
    private ArrayList<String> options;

    public App(String[] args) {
        super("CMD APP");
        parseArgs(args);
    }

    public static void main(String[] args) {
        App app = new App(args);
        app.open();
    }

    private void parseArgs(String[] args) {
        cmd = getCmdParser(args);
        options = parseCmdOptions();
    }

    @Override
    protected void setupKvaser() {
        String ip = options.get(0);
        String bitrate = options.get(1);

        hal = new Hal(
                new Motors(),
                new BlackBird(ip),
                new Imu(),
                new Driver()
        );

        try {
            hal.setup(bitrate);
        } catch (Exception e) {
            log(e);
            System.exit(1);
        }
    }

    @Override
    protected void setupUpdaters() {
        boolean log = Boolean.parseBoolean(options.get(3));

        switch (options.get(2)) {
            case "can":
                hal.addObserverToKvaser(new ShellCanUpdater(true, log));
                break;
            case "car":
                hal.addObserverToMotors(new ShellMotorsUpdater(true, log));
                break;
            case "bms":
                hal.addObserverToMotors(new ShellBatteryUpdater(true, log));
                break;
            case "imu":
                hal.addObserverToImu(new ShellImuUpdater(true, log));
                break;
            case "driver":
                hal.addObserverToDriver(new ShellDriverUpdater(true, log));
            case "all":
                hal.addObserverToKvaser(new ShellCanUpdater(true, log));
                hal.addObserverToMotors(new ShellMotorsUpdater(true, log));
                hal.addObserverToMotors(new ShellBatteryUpdater(true, log));
                hal.addObserverToMotors(new ShellImuUpdater(true, log));
                hal.addObserverToImu(new ShellImuUpdater(true, log));
                hal.addObserverToDriver(new ShellDriverUpdater(true, log));
                break;
        }
    }

    public void start() {
        hal.start();
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
                "with CAN messages, car model. Possible options are  [can, car, bms, imu, driver, all]");
        view.setRequired(true);
        options.addOption(view);

        Option logToFile = new Option("log", "log-to-file", false, "Do you want .csv logs?");
        logToFile.setRequired(false);
        options.addOption(logToFile);

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
            formatter.printHelp("yolo", options);
            System.exit(1);
        }

        return cmd;
    }

    private ArrayList<String> parseCmdOptions() {
        ArrayList<String> options = new ArrayList<>();

        options.add(cmd.getOptionValue("ip-kvaser"));
        options.add(cmd.getOptionValue("can-bitrate"));
        options.add(cmd.getOptionValue("view"));
        options.add(Boolean.toString(cmd.hasOption("log-to-file")));

        return options;
    }
}
