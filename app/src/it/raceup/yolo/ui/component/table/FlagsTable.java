package it.raceup.yolo.ui.component.table;

import javax.swing.table.TableCellRenderer;

public class FlagsTable extends ColorMultiAlignTable {
    public FlagsTable(Object[][] data, String[] headers) {
        super(data, headers);
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        TableCellRenderer renderer = super.getCellRenderer(row, column);

        if (column == 0) {
            return alignLeft(renderer);
        }

        if (row <= 7) {
            return alignCenter(renderer);
        }

        return alignRight(renderer);
    }
}
