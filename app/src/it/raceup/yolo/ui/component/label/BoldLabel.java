package it.raceup.yolo.ui.component.label;

import javax.swing.*;

public class BoldLabel extends JLabel {
    private static final String BOLD_START = "<html><b>";
    private static final String BOLD_END = "</b></html>";

    public BoldLabel(String text) {
        super(BOLD_START + text + BOLD_END);
    }
}
