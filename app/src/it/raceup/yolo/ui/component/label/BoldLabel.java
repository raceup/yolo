package it.raceup.yolo.ui.component.label;

import java.awt.*;

public class BoldLabel extends FontLabel {
    public BoldLabel(String text) {
        super(text, new Font(
                DEFAULT_FONT.getName(),
                Font.BOLD,
                DEFAULT_FONT.getSize()
        ));
    }
}
