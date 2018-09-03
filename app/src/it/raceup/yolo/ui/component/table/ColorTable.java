package it.raceup.yolo.ui.component.table;

import javax.swing.*;
import java.awt.*;

public class ColorTable extends MultiObjectTable {
    private static final String THIS_PACKAGE = "it.raceup.yolo.ui.component" +
            ".table";
    private static final String BASE_PATH = "/res/images/circle/small/";

    // create icons
    private static final Icon redIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
            THIS_PACKAGE.getClass().getResource(BASE_PATH + "red.png")));
    private static final Icon greenIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
            THIS_PACKAGE.getClass().getResource(BASE_PATH + "green.png")));

    public ColorTable(Object[][] data, String[] headers) {
        super(data, headers);
    }

    @Override
    public void setValueAt(Object o, int row, int column) {
        if (o instanceof Boolean) {
            boolean value = (Boolean) o;
            if (value) {
                setValueAt(greenIcon, row, column);
            } else {
                setValueAt(redIcon, row, column);
            }
        } else {
            super.setValueAt(o, row, column);
        }
    }
}
