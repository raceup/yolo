package it.raceup.yolo.app.gui;

import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.ui.component.label.BoldLabel;
import it.raceup.yolo.ui.component.label.ItalicLabel;

import javax.swing.*;
import java.awt.*;

public class ExceptionPanel extends JPanel {
    private final YoloException exception;

    public ExceptionPanel(Exception e) {
        this.exception = YoloException.parse(e);

        setup();
    }

    public static void showMessage(Exception e) {
        ExceptionPanel exceptionPanel = new ExceptionPanel(e);

        JOptionPane.showConfirmDialog(null, exceptionPanel,
                "Ooops! Something went wrong", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);

        System.exit(1);
    }

    private void setup() {
        setupLayout();

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(getTitleLabel());
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(getTypeLabel());
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(getSummaryLabel());
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(getMessageLabel());
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private JLabel getTitleLabel() {
        return new BoldLabel("title");  // todo
    }

    private JLabel getTypeLabel() {
        return new ItalicLabel("type");  // todo
    }

    private JLabel getSummaryLabel() {
        return new JLabel("summary");  // todo
    }

    private JLabel getMessageLabel() {
        return new JLabel("message");  // todo
    }
}
