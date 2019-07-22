package it.raceup.yolo.ui.window;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.car.Imu;
import it.raceup.yolo.models.car.Tyre;
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
    private final TyresPanel tyresPanel = new TyresPanel();


    public DynamicsFrame() {
        super(IMU_WINDOW_TITLE);
        TyresPanel tp = new TyresPanel();
        setup();
    }

    public void open() {
        try {
            pack();
            setSize(700, 750);
            setLocation(725, 350);  // under battery
            setResizable(false); //true
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
        setupLayout();
    }

    private void setup() {
        setupLayout();
    }

    private void setupLayout() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(getTopPanel());
        //add(Box.createRigidArea(new Dimension(10, 0)));
    }

    private JPanel getTopPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

       // panel.add(imuPanel);
        panel.add(tyresPanel);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        return panel;
    }

    private void update(Imu imu) {
    }

    private void update(Tyre tyre) {
        tyresPanel.update(tyre);
    }


    @Override
    public void update(Observable observable, Object o) {
        try {
            if (o instanceof Tyre) {
                update((Tyre) o);
            } else if (o instanceof Imu) {
                update((Imu) o);
            }
        } catch (Exception e) {
            e.printStackTrace();
            new YoloException("cannot updateWith CAR", e, ExceptionType.IMU)
                    .print();
        }
    }


}
