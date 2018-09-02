package it.raceup.yolo.app.cmd;

import it.raceup.yolo.app.KvaserApp;
import it.raceup.yolo.control.Hal;
import it.raceup.yolo.models.car.Car;
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
                new Car(),
                new FakeBlackBird(ip)  // todo use BlackBird in production
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
        view.setRequired(true);
        options.addOption(view);

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
        return options;
    }
}
