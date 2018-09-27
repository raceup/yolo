package it.raceup.yolo.ui.window;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.data.CanMessage;
import it.raceup.yolo.models.kvaser.message.FromKvaserMessage;
import it.raceup.yolo.ui.component.can.CanMessageBrowser;
import it.raceup.yolo.ui.component.can.CanMessageSender;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static it.raceup.yolo.Data.CAN_WINDOW_TITLE;
import static it.raceup.yolo.utils.Os.setNativeLookAndFeelOrFail;

public class CanMessagesFrame extends JFrame implements Observer {
    private final CanMessageSender canMessageSender;
    private final CanMessageBrowser canMessageBrowser;

    public CanMessagesFrame() {
        super(CAN_WINDOW_TITLE);

        canMessageSender = new CanMessageSender();
        canMessageBrowser = new CanMessageBrowser();

        setup();
    }

    public void open() {
        try {
            pack();
            setSize(600, 500);
            setLocation(0, 550);  // bottom of motors
            setResizable(false);
            setNativeLookAndFeelOrFail();

            // disable exit button
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            setVisible(true);
        } catch (Exception e) {
            new YoloException(
                    "cannot open CANBUS viewer",
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

        add(canMessageSender);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(canMessageBrowser);
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            FromKvaserMessage message = new FromKvaserMessage(o);
            ArrayList<CanMessage> messages = message.getAsCanMessages();
            if (messages != null) {
                canMessageBrowser.update(messages);
            }
        } catch (Exception e) {
            new YoloException("cannot updateWith CAN", e, ExceptionType.KVASER)
                    .print();
        }
    }
}
