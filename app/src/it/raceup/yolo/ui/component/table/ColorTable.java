package it.raceup.yolo.ui.component.table;

import it.raceup.yolo.Data;
import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;

import javax.swing.*;

public class ColorTable extends MultiObjectTable {
    private Icon redIcon;
    private Icon greenIcon;

    public ColorTable(Object[][] data, String[] headers) {
        super(data, headers);
        loadIcons();
    }


    @Override
    public void setValueAt(Object o, int row, int column) {
        createDefaultRenderers();
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
        Data data = new Data();

        try {
            redIcon = data.getRedIcon();
            greenIcon = data.getGreenIcon();
        } catch (Exception e) {
            new YoloException(
                    "cannot set red/green icons",
                    ExceptionType.APP
            ).print();
        }
    }
}
