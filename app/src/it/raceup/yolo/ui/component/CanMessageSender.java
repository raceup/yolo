package it.raceup.yolo.ui.component;

import javax.swing.*;
import java.awt.*;

public class CanMessageSender extends JPanel {
    private JTextField editorId = new JTextField();
    private JTextField editorMessage = new JTextField();
    private JButton sendButton = new JButton("SEND");
    private String currentId, currentMessage;

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

        add(new JLabel("Send CAN message"), BorderLayout.CENTER);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(up);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(down);
        add(Box.createRigidArea(new Dimension(0, 200)));
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
        System.err.println("sendMessage(): NOT IMPLEMENTED");  // todo send
    }

    private void checkMessage() {
        currentId = editorId.getText();
        currentMessage = editorMessage.getText();  // todo add error check
    }
}
