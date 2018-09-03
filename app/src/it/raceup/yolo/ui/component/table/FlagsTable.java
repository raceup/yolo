package it.raceup.yolo.ui.component.table;

import javax.swing.table.TableCellRenderer;

public class FlagsTable extends MultipleAlignTable {
    public FlagsTable(Object[][] data, String[] headers) {
        super(data, headers);
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        if (row <= 7) {
            return alignCenter();
        }

        return alignRight();
    }
}
