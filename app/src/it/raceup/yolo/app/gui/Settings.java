package it.raceup.yolo.app.gui;

import javax.swing.*;

public class Settings extends JPanel {
    private static final String[] BITRATES = {"10k", "50k", "62k",
            "83k", "100k", "125k", "250k", "500k", "1m"};
    private JTextField ipEditor;
    private JComboBox bitrateChooser;

    public Settings() {
        ipEditor = new JTextField("192.168.1.10");
        bitrateChooser = new JComboBox(BITRATES);
        bitrateChooser.setSelectedIndex(BITRATES.length - 1);
        // todo ? bitrateChooser.addActionListener(this);

        setup();
    }

    public static String[] getSettings() {
        String[] settings = new String[]{, "1m"};  // default

        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);


        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            settings[0] = xField.getText();
            settings[1] = yField.getText();
        }

        return settings;
    }

    private void setup() {
        setupLayout();
    }

    private void setupLayout() {

        add(new JLabel("x:"));
        add(xField);
        add(Box.createHorizontalStrut(15)); // a spacer
        add(new JLabel("y:"));
        add(yField);
    }
}
