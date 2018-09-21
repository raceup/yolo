package it.raceup.yolo.ui.component.table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public abstract class MultiAlignTable extends JTable {
    public MultiAlignTable(Object[][] data, String[] headers) {
        super(data, headers);
    }

    public static DefaultTableCellRenderer alignLeft() {
        return alignLeft(new DefaultTableCellRenderer());
    }

    public static DefaultTableCellRenderer alignRight() {
        return alignRight(new DefaultTableCellRenderer());
    }

    public static DefaultTableCellRenderer alignCenter() {
        return alignCenter(new DefaultTableCellRenderer());
    }

    public static DefaultTableCellRenderer alignLeft
            (DefaultTableCellRenderer renderer) {
        renderer.setHorizontalAlignment(SwingConstants.LEFT);
        return renderer;
    }

    public static DefaultTableCellRenderer alignRight
            (DefaultTableCellRenderer renderer) {
        renderer.setHorizontalAlignment(SwingConstants.RIGHT);
        return renderer;
    }

    public static DefaultTableCellRenderer alignCenter
            (DefaultTableCellRenderer renderer) {
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        return renderer;
    }

    @Override
    public abstract TableCellRenderer getCellRenderer(int row, int column);
}
