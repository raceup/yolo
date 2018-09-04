package it.raceup.yolo.ui.window;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.data.CanMessage;
import it.raceup.yolo.models.kvaser.message.FromKvaserMessage;
import it.raceup.yolo.ui.component.CanMessageBrowser;
import it.raceup.yolo.ui.component.CanMessageSender;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static it.raceup.yolo.utils.Os.setNativeLookAndFeelOrFail;

public class CanMessagesFrame extends JFrame implements Observer {
    private static final String TITLE = "YOLO: CAN bus";
    private final CanMessageSender canMessageSender;
    private final CanMessageBrowser canMessageBrowser;

    public CanMessagesFrame() {
        super(TITLE);

        canMessageSender = new CanMessageSender();  // todo connect to kvaser
        canMessageBrowser = new CanMessageBrowser();  // todo connect to kvaser

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
            new YoloException("cannot update car", e, ExceptionType.KVASER)
                    .print();
        }
    }
}
