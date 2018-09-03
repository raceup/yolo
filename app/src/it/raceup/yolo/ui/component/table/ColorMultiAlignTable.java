package it.raceup.yolo.ui.component.table;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public abstract class ColorMultiAlignTable extends ColorTable {
    public ColorMultiAlignTable(Object[][] data, String[] headers) {
        super(data, headers);
    }

    public TableCellRenderer alignLeft
            (TableCellRenderer renderer) {
        try {
            return MultiAlignTable.alignLeft((
                    DefaultTableCellRenderer) renderer);
        } catch (Exception e) {
            return renderer;
        }

    }

    public TableCellRenderer alignRight
            (TableCellRenderer renderer) {
        try {
            return MultiAlignTable.alignRight((
                    DefaultTableCellRenderer) renderer);
        } catch (Exception e) {
            return renderer;
        }

    }

    public TableCellRenderer alignCenter
            (TableCellRenderer renderer) {
        try {
            return MultiAlignTable.alignCenter((
                    DefaultTableCellRenderer) renderer);
        } catch (Exception e) {
            System.err.println("cannot align");
            return renderer;
        }

    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        return super.getCellRenderer(row, column);  // allows different types
    }
}
