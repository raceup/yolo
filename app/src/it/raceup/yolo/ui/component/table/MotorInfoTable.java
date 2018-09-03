package it.raceup.yolo.ui.component.table;

import javax.swing.table.TableCellRenderer;


public class MotorInfoTable extends ColorMultiAlignTable {
    public MotorInfoTable(Object[][] data, String[] headers) {
        super(data, headers);
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        TableCellRenderer renderer = super.getCellRenderer(row, column);

        if (row <= 3) {
            return alignCenter(renderer);
        }

        return alignRight(renderer);
    }
}
