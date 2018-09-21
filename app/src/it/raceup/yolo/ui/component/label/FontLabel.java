package it.raceup.yolo.ui.component.label;

import javax.swing.*;
import java.awt.*;

public class FontLabel extends JLabel {
    public static final Font DEFAULT_FONT = UIManager.getFont("Label.font");
    private final Font font;

    public FontLabel(String text, Font font) {
        super(text);

        this.font = font;
        this.setFont(font);
        this.setText(text);  // reload text with new font
    }
}
