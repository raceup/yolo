package it.raceup.yolo.ui.component.table;

import javax.swing.table.TableCellRenderer;

public class SetPointTable extends ColorMultiAlignTable {
    public SetPointTable(Object[][] data, String[] headers) {
        super(data, headers);
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        TableCellRenderer renderer = super.getCellRenderer(row, column);

        if (column == 0) {
            return alignLeft(renderer);
        }

        if (row <= 3) {
            return alignCenter(renderer);
        }

        return alignRight(renderer);
    }
}
