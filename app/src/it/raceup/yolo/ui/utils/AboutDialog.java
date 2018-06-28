package it.raceup.yolo.ui.utils;

import javax.swing.*;

/**
 * A simple about dialog with given content and title
 */
public class AboutDialog extends JDialog {
    public AboutDialog(JFrame parent, String content, String title) {
        super(parent, title, true);
        add(new JLabel(content));

        getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 20, 20,
                20));  // border
        pack();  // set size based on content
        setLocationRelativeTo(null);  // center in screen

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);  // destroy
    }
}
