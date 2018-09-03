package it.raceup.yolo.ui.component.table;

import javax.swing.table.TableCellRenderer;

public class TemperatureTable extends MultiAlignTable {
    public TemperatureTable(Object[][] data, String[] headers) {
        super(data, headers);
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        return alignRight();
    }
}
