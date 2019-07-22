//REPLACED BY DRIVER FRAME

package it.raceup.yolo.ui.component.driver;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.car.Driver;
import it.raceup.yolo.models.car.Tyre;
import it.raceup.yolo.ui.utils.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

import static it.raceup.yolo.models.data.Type.*;
import static it.raceup.yolo.utils.Os.setNativeLookAndFeelOrFail;

public class DriverPanel extends JFrame {
    private final String[] throttleBrakeTag = {"Throttle", "Brake"};
    private final String[] steeringPanelTag = {"Steerign Wheel Rotation"};
    private final String[] wheelTemperaturePanelTag = {"FL", "FR", "RL", "RR"};
    private final String[] brakePressurePanelTag = {"Front", "Rear"};
    private final int REFRESH_RATE = 2000;
    private ChartPanel throttleBrakeChartPanel;
    private ChartPanel steeringChartPanel;
    private ChartPanel wheelTemperatureChartPanel;
    private ChartPanel brakePressureChartPanel;
    private final Driver driver;
    private final Tyre tyre;

    public DriverPanel() {
        driver = new Driver();
        tyre = new Tyre();
        setup();
    }

    private void setup() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        createChart();
        add(throttleBrakeChartPanel);
        add(steeringChartPanel);
        add(wheelTemperatureChartPanel);
        add(brakePressureChartPanel);
        startChart();
    }

    private void createChart() {
        throttleBrakeChartPanel = new ChartPanel(throttleBrakeTag, "Throttle Brake");
        steeringChartPanel = new ChartPanel(steeringPanelTag, "Steering Wheel Angle");
        wheelTemperatureChartPanel = new ChartPanel(wheelTemperaturePanelTag, "Wheel Temperatures");
        brakePressureChartPanel = new ChartPanel(brakePressurePanelTag, "Brake Pressure");
    }

    private void startChart() {
        showThrottleBrake();
        showSteeringWheelChartPanel();
        showWheelTemperatureChartPanel();
        showBrakePressureChartPanel();
    }

    private void showBrakePressureChartPanel() {
        if (driver.getDriverData(BRAKE_PRESSURE) != null) {
            Timer updater = new Timer(REFRESH_RATE, e -> {
                //real code
                //brakePressureChartPanel.updateSeriesOrFail(0, driver.getDriverData(BRAKE_PRESSURE)[0]);
                //brakePressureChartPanel.updateSeriesOrFail(1, driver.getDriverData(BRAKE_PRESSURE)[1]);
                //test code
                brakePressureChartPanel.updateSeriesOrFail(0, ThreadLocalRandom.current().nextInt(0, 100));
                brakePressureChartPanel.updateSeriesOrFail(1, ThreadLocalRandom.current().nextInt(0, 100));
            });
            updater.start();
        }

    }

    public void open() {
        try {
            pack();
            setSize(1400, 950);
            setLocation(0, 0);  // under battery
            setResizable(false);
            setNativeLookAndFeelOrFail();
            // enable exit button
            setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            setVisible(true);
        } catch (Exception e) {
            new YoloException(
                    "cannot open DRIVER viewer",
                    e,
                    ExceptionType.VIEW
            ).print();
        }
    }

    private void showWheelTemperatureChartPanel() {
        if (tyre.getTyreData(WHEEL_TEMPERATURE) != null) {
            Timer updater = new Timer(REFRESH_RATE, e -> {
                //real code
/*
                wheelTemperatureChartPanel.updateSeriesOrFail(0, tyre.getTyreData(WHEEL_TEMPERATURE)[0]);
                wheelTemperatureChartPanel.updateSeriesOrFail(1, tyre.getTyreData(WHEEL_TEMPERATURE)[1]);
                wheelTemperatureChartPanel.updateSeriesOrFail(2, tyre.getTyreData(WHEEL_TEMPERATURE)[2]);
                wheelTemperatureChartPanel.updateSeriesOrFail(3, tyre.getTyreData(WHEEL_TEMPERATURE)[3]);
*/
                //test code

                wheelTemperatureChartPanel.updateSeriesOrFail(0, ThreadLocalRandom.current().nextInt(45, 50));
                wheelTemperatureChartPanel.updateSeriesOrFail(1, ThreadLocalRandom.current().nextInt(45, 50));
                wheelTemperatureChartPanel.updateSeriesOrFail(2, ThreadLocalRandom.current().nextInt(45, 50));
                wheelTemperatureChartPanel.updateSeriesOrFail(3, ThreadLocalRandom.current().nextInt(45, 50));


            });
            updater.start();
        }

    }

    private void showSteeringWheelChartPanel() {
        if (driver.getDriverData(STEERINGWHEEL) != null) {
            Timer updater = new Timer(REFRESH_RATE, e -> {
                //steeringChartPanel.updateSeriesOrFail(0, driver.getDriverData(STEERINGWHEEL)[0]);
                //test code, real code above
                steeringChartPanel.updateSeriesOrFail(0, ThreadLocalRandom.current().nextInt(40, 60));
            });
            updater.start();
        }
    }

    private void showThrottleBrake() {
        if (driver.getDriverData(THROTTLE) != null) {
            Timer updater = new Timer(REFRESH_RATE, e -> {
                //rela code
                //throttleBrakeChartPanel.updateSeriesOrFail(0, driver.getDriverData(THROTTLE)[0]);
                //throttleBrakeChartPanel.updateSeriesOrFail(1, driver.getDriverData(THROTTLE)[1]);

                throttleBrakeChartPanel.updateSeriesOrFail(0, ThreadLocalRandom.current().nextInt(0, 100));
                throttleBrakeChartPanel.updateSeriesOrFail(0, ThreadLocalRandom.current().nextInt(0, 100));
            });
            updater.start();
        }
    }
}
