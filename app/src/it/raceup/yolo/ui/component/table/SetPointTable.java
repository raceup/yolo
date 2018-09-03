package it.raceup.yolo.ui.component.table;

import javax.swing.table.TableCellRenderer;

public class SetPointTable extends MultipleAlignTable {
    public SetPointTable(Object[][] data, String[] headers) {
        super(data, headers);
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        if (row <= 3) {
            return alignCenter();
        }

        return alignRight();
    }
}
