package it.raceup.yolo.app.gui;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private static final String[] BITRATES = {"10k", "50k", "62k",
            "83k", "100k", "125k", "250k", "500k", "1m"};
    private JTextField ipEditor;
    private JComboBox bitrateChooser;

    public SettingsPanel() {
        ipEditor = new JTextField("192.168.1.10");
        bitrateChooser = new JComboBox(BITRATES);
        bitrateChooser.setSelectedIndex(BITRATES.length - 1);

        setup();
    }

    public static String[] getSettings() {
        SettingsPanel settingsPanel = new SettingsPanel();

        int result = JOptionPane.showConfirmDialog(null, settingsPanel,
                "Enter Kvaser settings", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            return settingsPanel.parseSettings();
        }

        return null;
    }

    private void setup() {
        setupLayout();
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(getIpSettingsPanel());
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(getBitrateSettingsPanel());

    }

    private JPanel getIpSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Enter Kvaser IP"));
        panel.add(ipEditor);

        return panel;
    }

    private JPanel getBitrateSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Choose bitrate"));
        panel.add(bitrateChooser);

        return panel;
    }

    public String[] parseSettings() {
        return new String[]{
                ipEditor.getText(),
                (String) bitrateChooser.getSelectedItem()
        };
    }
}
