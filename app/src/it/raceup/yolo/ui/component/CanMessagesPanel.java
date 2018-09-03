package it.raceup.yolo.ui.component;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.data.CanMessage;
import it.raceup.yolo.models.kvaser.message.FromKvaserMessage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class CanMessagesPanel extends JPanel implements Observer {
    private final CanMessageSender canMessageSender;
    private final CanMessageBrowser canMessageBrowser;

    public CanMessagesPanel() {
        canMessageSender = new CanMessageSender();  // todo connect to kvaser
        canMessageBrowser = new CanMessageBrowser();  // todo connect to kvaser

        setup();
    }

    private void setup() {
        setupLayout();
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

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
