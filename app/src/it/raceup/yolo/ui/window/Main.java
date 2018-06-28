package it.raceup.yolo.ui.window;

import it.raceup.yolo.models.data.Raw;

import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        super("YOLO | Race Up Electric Division");
        setup();
    }

    public void setup() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(800, 400);
        setVisible(true);
    }

    public JPanel getContentPanel() {
        return null;
    }

    public void update(Raw data) {
    }
}
