package it.raceup.yolo.ui.component.table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public abstract class MultipleAlignTable extends JTable {
    private static final DefaultTableCellRenderer renderRight = new
            DefaultTableCellRenderer();
    private static final DefaultTableCellRenderer renderCenter = new
            DefaultTableCellRenderer();
    private static final DefaultTableCellRenderer renderLeft = new
            DefaultTableCellRenderer();


    static {
        renderRight.setHorizontalAlignment(SwingConstants.RIGHT);
        renderCenter.setHorizontalAlignment(SwingConstants.CENTER);
        renderLeft.setHorizontalAlignment(SwingConstants.LEFT);
    }

    public MultipleAlignTable(Object[][] data, String[] headers) {
        super(data, headers);
    }

    public static DefaultTableCellRenderer alignRight() {
        return renderRight;
    }

    public static DefaultTableCellRenderer alignCenter() {
        return renderCenter;
    }

    public static DefaultTableCellRenderer alignLeft() {
        return renderLeft;
    }

    @Override
    public abstract TableCellRenderer getCellRenderer(int row, int column);
}
