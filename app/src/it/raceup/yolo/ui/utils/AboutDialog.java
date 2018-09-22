package it.raceup.yolo.ui.utils;

import javax.swing.*;

/**
 * A simple about dialog with given content and title
 */
public class AboutDialog {
    private final String title;
    private final String content;
    private final int type;

    public AboutDialog(String title, String content, int type) {
        this.title = title;
        this.content = content;
        this.type = type;
    }

    public void show() {
        JOptionPane.showMessageDialog(null, this.content, this.title, this.type);
    }
}
