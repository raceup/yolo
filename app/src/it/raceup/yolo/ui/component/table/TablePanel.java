package it.raceup.yolo.ui.component.table;

import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static it.raceup.yolo.models.data.Base.DNF;
import static it.raceup.yolo.models.data.Base.getAsString;
import static it.raceup.yolo.models.data.Raw.isBoolean;

public class TablePanel extends JPanel {
    private final String[] labels;
    private final JTable table;
    public JButton viewButton = null;

    public TablePanel(String[] labels, String tag) {
        this.labels = labels;
        table = getDataTable();

        if (tag != null) {  // tag null -> do not add button
            viewButton = new JButton(tag);
        }

        setup();
    }

    private void setup() {
        setupLayout();

        if (viewButton != null) {
            add(Box.createRigidArea(new Dimension(0, 10)));
            add(viewButton);
        }

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(getTable());
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private JScrollPane getTable() {
        JScrollPane tableContainer = new JScrollPane(table);
        Dimension d = table.getPreferredSize();
        tableContainer.setPreferredSize(
                new Dimension(
                        d.width * 2,
                        table.getRowHeight() * table.getRowCount() + 22
                )
        );
        return tableContainer;
    }

    private JTable getDataTable() {
        String[] columns = new String[]{
                "Key", "Value"
        };  // headers for the table

        // actual data
        Object[][] data = new Object[labels.length][columns.length];
        for (int row = 0; row < data.length; row++) {
            data[row][0] = labels[row];
            data[row][1] = DNF;
        }

        JTable table = new InfoTable(data, columns);
        table.setEnabled(false);  // non-editable cells
        return table;
    }

    public void update(String type, Double data, boolean isBool) {
        if (Arrays.asList(labels).contains(type)) {
            int tableRow = Arrays.asList(labels).indexOf(type);
            if (tableRow >= 0) {
                if (isBool) {
                    table.setValueAt(data.intValue() == 1, tableRow, 1);
                } else {
                    table.setValueAt(getAsString(data), tableRow, 1);
                }
            }
        }
    }

    public void update(Raw data) {
        Type type = data.getType();
        update(type.toString(), data.getRaw(), isBoolean(type));
    }
}
