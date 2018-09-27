package it.raceup.yolo.ui.component.label;

import javax.swing.*;
import java.awt.*;

import static it.raceup.yolo.utils.Misc.getTimeNow;

public class UpdatePanel extends JPanel {
    private static final String UPDATE_LABEL = "Last update: ";
    private final JLabel label = new JLabel();

    public UpdatePanel() {
        setup();
    }

    private void setup() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(label);
    }

    public void setText(String text) {
        label.setText(text);
    }

    public void updateWith(String label) {
        setText(UPDATE_LABEL + label);
    }

    public void updateWithTimeNow() {
        updateWith(getTimeNow());
    }
}
