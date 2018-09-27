package it.raceup.yolo.ui.window;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.kvaser.message.FromKvaserMessage;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

import static it.raceup.yolo.Data.IMU_WINDOW_TITLE;
import static it.raceup.yolo.utils.Os.setNativeLookAndFeelOrFail;

public class ImuFrame extends JFrame implements Observer {
    public ImuFrame() {
        super(IMU_WINDOW_TITLE);
        setup();
    }

    public void open() {
        try {
            pack();
            setSize(600, 500);
            setLocation(625, 550);  // under battery
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
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            FromKvaserMessage message = new FromKvaserMessage(o);
            // todo updateWith
        } catch (Exception e) {
            new YoloException("cannot updateWith CAR", e, ExceptionType.KVASER)
                    .print();
        }
    }
}
