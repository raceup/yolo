package it.raceup.yolo.ui.component.label;

import java.awt.*;

public class ItalicLabel extends FontLabel {
    public ItalicLabel(String text) {
        super(text, new Font(
                DEFAULT_FONT.getName(),
                Font.ITALIC,
                DEFAULT_FONT.getSize()
        ));
    }
}
