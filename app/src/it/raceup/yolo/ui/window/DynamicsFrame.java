package it.raceup.yolo.ui.window;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.car.Imu;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.models.kvaser.message.FromKvaserMessage;
import it.raceup.yolo.ui.component.driver.DriverPanel;
import it.raceup.yolo.ui.component.imu.ImuPanel;
import it.raceup.yolo.ui.component.tyres.TyresPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static it.raceup.yolo.Data.IMU_WINDOW_TITLE;
import static it.raceup.yolo.utils.Os.setNativeLookAndFeelOrFail;

public class DynamicsFrame extends JFrame implements Observer {
    private final ImuPanel imuPanel = new ImuPanel();
    private final DriverPanel driverPanel = new DriverPanel();
    private final TyresPanel tyresPanel = new TyresPanel();

    public DynamicsFrame() {
        super(IMU_WINDOW_TITLE);
        setup();
    }

    public void open() {
        try {
            pack();
            setSize(700, 700);
            setLocation(725, 350);  // under battery
            setResizable(false);
            setNativeLookAndFeelOrFail();

            // disable exit button
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            setVisible(true);
        } catch (Exception e) {
            new YoloException(
                    "cannot open CAR viewer",
                    e,
                    ExceptionType.VIEW
            ).print();
        }
    }

    private void setup() {
        setupLayout();
    }

    private void setupLayout() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(getTopPanel());
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(tyresPanel);
    }

    private JPanel getTopPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(imuPanel);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(driverPanel);

        return panel;
    }

    private void update(it.raceup.yolo.models.car.Imu imu) {

            if(imu.getImuType() == it.raceup.yolo.models.data.Type.ACCELERATION){
                imuPanel.updateAccelerationPanel(imu);
            }
            else if(imu.getImuType() == it.raceup.yolo.models.data.Type.ROLL_PITCH_YAW){
                imuPanel.updateYawPanel(imu);
            }
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            update((Imu) o);
        } catch (Exception e) {
            e.printStackTrace();
            new YoloException("cannot updateWith CAR", e, ExceptionType.KVASER)
                    .print();
        }
    }
}
