package it.raceup.yolo.app.cmd;

import it.raceup.yolo.app.YoloApp;
import it.raceup.yolo.control.Hal;
import it.raceup.yolo.logging.ShellLogger;
import it.raceup.yolo.models.car.Car;
import it.raceup.yolo.models.kvaser.BlackBird;
import org.apache.commons.cli.*;

import java.util.ArrayList;

/**
 * Command line interface for telemetry (yolo-cli). Setups connection with
 * Kvaser and outputs current car data as soon as new data has arrived.
 */
public class App extends ShellLogger implements YoloApp {
    private CommandLine cmd;
    private ArrayList<String> options;
    private Hal hal;

    public static void main(String[] args) {
        App app = new App();
        app.parseArgs(args);

        try {
            app.setup();
            app.start();
        } catch (Exception e) {
            app.log(e);
        } finally {
            app.close();
        }
    }

    public void parseArgs(String[] args) {
        cmd = getCmdParser(args);
        options = parseCmdOptions();
        System.out.println(options.get(0));
    }

    public void setup() {
        setupKvaser();
        setupUpdater();
    }

    private void setupKvaser() {
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
    }

    private void setupUpdater() {
        if (options.get(2).equals("can")) {
            hal.addObserverToKvaser(new CanUpdater());
        } else if (options.get(2).equals("car")) {
            hal.addObserverToCar(new CarUpdater());
        }
    }

    public void start() {
        hal.start();
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
}
