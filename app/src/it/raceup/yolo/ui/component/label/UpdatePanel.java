package it.raceup.yolo.ui.component.label;

import javax.swing.*;
import java.awt.*;

import static it.raceup.yolo.utils.Misc.getTimeNow;

public class UpdatePanel extends JPanel {
    private static final String UPDATE_LABEL = "Last update: ";
    private final JLabel lastUpdateLabel = new JLabel();

    public UpdatePanel() {
        setup();
    }

    private void setup() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(lastUpdateLabel);
    }

    public void update(String label) {
        lastUpdateLabel.setText(UPDATE_LABEL + label);
    }

    public void updateWithTimeNow() {
        update(getTimeNow());
    }
}
