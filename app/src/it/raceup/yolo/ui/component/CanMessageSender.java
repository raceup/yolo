package it.raceup.yolo.ui.component;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.kvaser.message.FromKvaserMessage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class CanMessageSender extends JPanel implements Observer {
    private final JTextField editorId = new JTextField();
    private final JTextField editorMessage = new JTextField();
    private final JButton sendButton = new JButton("Send");
    private final JLabel statusLabel = new JLabel("Waiting for user input");

    public CanMessageSender() {
        setup();
    }

    private void setup() {
        setupLayout();
        sendButton.addActionListener(e -> sendMessage());
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(getHeadPanel());

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(getEditorPanel());

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(getSendPanel());
    }

    private JPanel getCanEditPanel(String editField, JTextField editor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel(editField));
        panel.add(editor);

        return panel;
    }

    private JPanel getHeadPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        panel.add(new JLabel("Send CAN messages to Kvaser"));

        return panel;
    }

    private JPanel getEditorPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(getCanEditPanel("ID (dec)", editorId));
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(getCanEditPanel("Message (dec)", editorMessage));
        return panel;
    }

    private JPanel getSendPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(statusLabel);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(sendButton);
        return panel;
    }

    private void sendMessage() {
        setNotClickable();
        statusLabel.setText("Sending...");

        try {
            // String msgId = editorId.getText();
            // String msgContent = editorMessage.getText();
            // todo kvaser.sendMessage(Integer.parseInt(msgId), new byte[]{}, 0);
            throw new NotImplementedException();
        } catch (Exception e) {
            updateStatus(false);
            new YoloException(e, ExceptionType.VIEW).print();
        } finally {
            setClickable();
        }
    }

    private void updateStatus(boolean success) {
        setClickable();  // message sent and acknowledged -> can send another
        if (success) {
            statusLabel.setText("Message sent");
        } else {
            statusLabel.setText("Cannot send message");
        }
    }

    private void setClickable() {
        sendButton.setEnabled(true);
    }

    private void setNotClickable() {
        sendButton.setEnabled(false);
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            FromKvaserMessage message = new FromKvaserMessage(o);
            Boolean success = message.getAsBoolean();
            if (success != null) {
                updateStatus(success);
            }
        } catch (Exception e) {
            new YoloException("cannot update car", e, ExceptionType.KVASER)
                    .print();
        }
    }
}
