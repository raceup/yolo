package it.raceup.yolo.ui.component.table;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;

import javax.swing.*;
import java.awt.*;

public class ColorTable extends MultiObjectTable {
    private static final String THIS_PACKAGE = "it.raceup.yolo.ui.component" +
            ".table";
    private static final String BASE_PATH = "/res/images/circle/small/";
    private static final String RED_PATH = BASE_PATH + "red.png";
    private static final String GREEN_PATH = BASE_PATH + "green.png";
    private Icon redIcon;
    private Icon greenIcon;

    public ColorTable(Object[][] data, String[] headers) {
        super(data, headers);

        loadIcons();
    }

    @Override
    public void setValueAt(Object o, int row, int column) {
        if (o instanceof Boolean) {
            boolean value = (Boolean) o;
            if (value) {
                if (greenIcon != null) {
                    setValueAt(greenIcon, row, column);
                } else {
                    setValueAt(1, row, column);
                }
            } else {
                if (redIcon != null) {
                    setValueAt(redIcon, row, column);
                } else {
                    setValueAt(0, row, column);
                }
            }
        } else {
            super.setValueAt(o, row, column);
        }
    }

    private void loadIcons() {
        try {
            redIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource(RED_PATH)
            ));
            greenIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource(GREEN_PATH)
            ));
        } catch (Exception e) {
            new YoloException(
                    "cannot set app icon",
                    ExceptionType.APP
            ).print();
        }
    }
}
