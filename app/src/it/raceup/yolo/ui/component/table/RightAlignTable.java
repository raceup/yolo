package it.raceup.yolo.ui.component.table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class RightAlignTable extends JTable {
    private DefaultTableCellRenderer renderRight = new DefaultTableCellRenderer();

    {
        renderRight.setHorizontalAlignment(SwingConstants.RIGHT);
    }

    public RightAlignTable(Object[][] data, String[] headers) {
        super(data, headers);
    }

    @Override
    public TableCellRenderer getCellRenderer(int arg0, int arg1) {
        return renderRight;
    }
}
