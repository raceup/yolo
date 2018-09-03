package it.raceup.yolo.ui.component;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.kvaser.message.FromKvaserMessage;

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

        JPanel up = getUpPanel();
        JPanel down = getDownPanel();

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(new JLabel("Send CAN message"));
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(up);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(down);
        add(Box.createRigidArea(new Dimension(0, 230)));
    }

    private JPanel getCanEditPanel(String editField, JTextField editor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel(editField));
        panel.add(editor);

        return panel;
    }

    private JPanel getUpPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(getCanEditPanel("ID (HEX)", editorId));
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(getCanEditPanel("Message (DEC)", editorMessage));
        return panel;
    }

    private JPanel getDownPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(sendButton);
        return panel;
    }

    private void sendMessage() {
        String msgId = editorId.getText();
        String msgContent = editorMessage.getText();
        // todo kvaser.sendMessage(Integer.parseInt(msgId), new byte[]{}, 0);
        new YoloException("sendMessage to " + msgId + " (" + msgContent +
                ") NOT IMPLEMENTED", ExceptionType.KVASER).print();
    }

    private void updateStatus(boolean success) {
        // todo
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
