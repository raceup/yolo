package it.raceup.yolo.ui.window;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.kvaser.message.FromKvaserMessage;
import it.raceup.yolo.ui.component.battery.BMSInfo;
import it.raceup.yolo.ui.component.battery.BatteryInfo;
import it.raceup.yolo.ui.component.label.UpdatePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static it.raceup.yolo.Data.BATTERY_WINDOW_TITLE;
import static it.raceup.yolo.utils.Os.setNativeLookAndFeelOrFail;

public class BatteryFrame extends JFrame implements Observer {
    private BatteryInfo batteryInfo = new BatteryInfo();
    private BMSInfo bmsInfo = new BMSInfo();
    private UpdatePanel lastUpdate = new UpdatePanel();

    public BatteryFrame() {
        super(BATTERY_WINDOW_TITLE);

        setup();
    }

    public void open() {
        try {
            pack();
            setSize(470, 300);
            setLocation(625, 0);  // right to motors
            setResizable(false);
            setNativeLookAndFeelOrFail();

            // disable exit button
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            setVisible(true);
        } catch (Exception e) {
            new YoloException(
                    "cannot open BMS viewer",
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

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(batteryInfo);

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(bmsInfo);

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(lastUpdate);
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            FromKvaserMessage message = new FromKvaserMessage(o);
            // todo updateWith

            lastUpdate.updateWithTimeNow();
        } catch (Exception e) {
            new YoloException("cannot updateWith Battery info frame", e, ExceptionType.KVASER)
                    .print();
        }
    }
}
