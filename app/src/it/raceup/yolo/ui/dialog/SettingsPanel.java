package it.raceup.yolo.ui.dialog;

import it.raceup.yolo.ui.utils.AboutDialog;

import javax.swing.*;
import java.awt.*;

import static it.raceup.yolo.Data.*;

public class SettingsPanel extends JPanel {
    private final JTextField ipEditor;
    private final JComboBox<String> bitrateChooser;
    private final JCheckBox checkboxMotorsLog = new JCheckBox("Motors", false);
    //private final JCheckBox checkboxCanLog = new JCheckBox("CAN bus");
    //private final JCheckBox checkboxBatteryLog = new JCheckBox("Battery and BMS");
    private final JCheckBox checkboxIMULog = new JCheckBox("IMU", false);
    private final JCheckBox checkboxDriverLog = new JCheckBox("Telemetry", false);

    public SettingsPanel() {
        ipEditor = new JTextField("192.168.43.228");
        bitrateChooser = new JComboBox<>(BITRATES);
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
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel upper = new JPanel();
        upper.setLayout(new BoxLayout(upper, BoxLayout.X_AXIS));

        upper.add(getIpSettingsPanel());
        upper.add(Box.createRigidArea(new Dimension(10, 0)));
        upper.add(getBitrateSettingsPanel());

        add(upper);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(getLogsPanel());
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

    private JPanel getLogsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel checksPanel = new JPanel();  // where place check boxes
        checksPanel.setLayout(new BoxLayout(checksPanel, BoxLayout.X_AXIS));

        checksPanel.add(checkboxMotorsLog);
        //checksPanel.add(checkboxCanLog);
        //checksPanel.add(checkboxBatteryLog);
        checksPanel.add(checkboxIMULog);
        checksPanel.add(checkboxDriverLog);

        panel.add(Box.createHorizontalGlue());
        panel.add(new JLabel("Select which logs to generate"));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(checksPanel);

        return panel;
    }

    public String[] parseSettings() {
        return new String[]{
                ipEditor.getText(),
                (String) bitrateChooser.getSelectedItem(),
                Boolean.toString(checkboxMotorsLog.isSelected()),
                //Boolean.toString(checkboxCanLog.isSelected()),
                //Boolean.toString(checkboxBatteryLog.isSelected()),
                Boolean.toString(checkboxIMULog.isSelected()),
                Boolean.toString(checkboxDriverLog.isSelected())
        };
    }


    private void showHelpDialog() {
        new AboutDialog(
                HELP_TITLE,
                getHelpContent(),
                JOptionPane.WARNING_MESSAGE
        ).show();
    }
}
