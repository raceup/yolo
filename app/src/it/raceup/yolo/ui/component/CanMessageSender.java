package it.raceup.yolo.ui.component;

import it.raceup.yolo.models.kvaser.Kvaser;

import javax.swing.*;
import java.awt.*;

public class CanMessageSender extends JPanel {
    private final JTextField editorId = new JTextField();
    private final JTextField editorMessage = new JTextField();
    private final JButton sendButton = new JButton("SEND");
    private String currentId, currentMessage;
    private final Kvaser kvaser;

    public CanMessageSender(Kvaser kvaser) {
        setup();
        this.kvaser = kvaser;
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
        System.err.println("@" + msgId + ": " + msgContent);
        System.err.println("sendMessage(): NOT IMPLEMENTED");  // todo send
    }

    private void checkMessage() {
        currentId = editorId.getText();
        currentMessage = editorMessage.getText();  // todo add error check
    }
}
