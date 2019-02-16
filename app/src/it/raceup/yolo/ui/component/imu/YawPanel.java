package it.raceup.yolo.ui.component.imu;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.car.Imu;
import it.raceup.yolo.ui.utils.ChartPanel;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

import static it.raceup.yolo.models.data.Base.DEGREES;

public class YawPanel extends JPanel implements Observer {
    // todo add polar plane
    private final JLabel valueLabel = new JLabel(DEGREES);  // setup label
    private final int ROLL_CODE = 0;
    private final int PITCH_CODE = 1;
    private final int YAW_CODE = 2;
    private double roll;
    private double pitch;
    private double yaw;
    final String[] listOfSeries = {"Roll", "Pitch", "Yaw"};
    private ChartPanel chartPanel;

    public YawPanel() {
        super();
        setup();
    }

    /**
     * Setups gui and panel
     */
    private void setup() {
        valueLabel.setAlignmentX(CENTER_ALIGNMENT);

        setupLayout();
    }

    private void setupLayout() {
        chartPanel = new ChartPanel(listOfSeries);
        add(chartPanel);
    }


    private void update(double roll, double pitch, double yaw) {
        this.roll = roll;
        chartPanel.updateSeriesOrFail(ROLL_CODE, roll);
        this.yaw = yaw;
        chartPanel.updateSeriesOrFail(YAW_CODE, yaw);
        this.pitch = pitch;
        chartPanel.updateSeriesOrFail(PITCH_CODE, pitch);
    }

    public void update(Imu imu) {
        //get raw data from Imu and pass to updater
        //update(imu.getImuData()[0], imu.getImuData()[1], imu.getImuData()[2]);
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            update((Imu) arg);
        } catch (Exception e) {
            new YoloException("cannot update yaw panel", e, ExceptionType.VIEW)
                    .print();
        }

    }
}
