package it.raceup.yolo.ui.component.motors;

import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.models.data.Type;
import it.raceup.yolo.ui.component.table.MotorInfoTable;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static it.raceup.yolo.models.data.Base.DNF;
import static it.raceup.yolo.models.data.Base.getAsString;
import static it.raceup.yolo.models.data.Raw.isBoolean;
import static it.raceup.yolo.models.data.Type.*;

public class MotorInfo extends JPanel {
    public static final it.raceup.yolo.models.data.Type[] LABELS =
            new it.raceup.yolo.models.data.Type[]{
                    SYSTEM_READY,
                    it.raceup.yolo.models.data.Type.ERROR,
                    DC_ON,
                    INVERTER_ON,
                    ACTUAL_VELOCITY,
                    TORQUE_CURRENT,
                    TEMPERATURE_MOTOR,
                    TEMPERATURE_INVERTER,
            };
    public final JButton viewButton;
    private final JTable table = getMotorTable();

    public MotorInfo(String tag) {
        viewButton = new JButton(tag);
        setup();
    }

    private void setup() {
        setupLayout();

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(viewButton);
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

    private JTable getMotorTable() {
        String[] columns = new String[]{
                "Key", "Value"
        };  // headers for the table

        // actual data
        Object[][] data = new Object[LABELS.length][columns.length];
        for (int row = 0; row < data.length; row++) {
            data[row][0] = LABELS[row];
            data[row][1] = DNF;
        }

        JTable table = new MotorInfoTable(data, columns);
        table.setEnabled(false);  // non-editable cells
        return table;
    }

    public void update(Type type, Double data) {
        if (Arrays.asList(LABELS).contains(type)) {
            int tableRow = Arrays.asList(LABELS).indexOf(type);
            if (tableRow >= 0) {
                if (isBoolean(type)) {
                    table.setValueAt(data.intValue() == 1, tableRow, 1);
                } else {
                    table.setValueAt(getAsString(data), tableRow, 1);
                }
            }
        }
    }

    public void update(Raw data) {
        update(data.getType(), data.getRaw());
    }
}
