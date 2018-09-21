package it.raceup.yolo.ui.dialog;

import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.ui.component.label.ItalicLabel;

import javax.swing.*;
import java.awt.*;

public class ExceptionPanel extends JPanel {
    private final String DEBUG_MESSAGE = "Waiting for Kvaser to exit...\n" +
            "If you want to report this bug, please reach out to\n" +
            "https://github.com/raceup/yolo/issues/";
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
        add(getTypeLabel());
        add(getSummaryLabel());
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(getErrorLabel());
        add(getDebugLabel());
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private JLabel getTypeLabel() {
        return new ItalicLabel(this.exception.getType().name());
    }

    private JLabel getSummaryLabel() {
        return new JLabel(this.exception.getMessage());
    }

    private JLabel getErrorLabel() {
        StackTraceElement[] stackTraceElements = this.exception.getStackTrace();
        StackTraceElement lastElement = stackTraceElements[stackTraceElements.length - 1];
        return new JLabel(lastElement.toString());
    }

    private JLabel getDebugLabel() {
        return new JLabel(DEBUG_MESSAGE);
    }
}
