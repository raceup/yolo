package it.raceup.yolo.ui.component;

import javax.swing.*;
import java.awt.*;

import static it.raceup.yolo.models.data.Base.DNF;
import static it.raceup.yolo.models.data.Type.*;

public class MotorInfo extends JPanel {
    private JTable table = getMotorTable();

    public MotorInfo(String tag) {
        setup(tag);
    }

    private void setup(String tag) {
        setupLayout();

        add(new JLabel(tag));
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

        it.raceup.yolo.models.data.Type[] labels = new it.raceup.yolo.models.data.Type[]{
                SYSTEM_READY,
                it.raceup.yolo.models.data.Type.ERROR,
                DC_ON,
                INVERTER_ON,
                ACTUAL_VELOCITY,
                TORQUE_CURRENT,
                TEMPERATURE_MOTOR,
                TEMPERATURE_INVERTER,
        };

        // actual data
        Object[][] data = new Object[labels.length][columns.length];
        for (int row = 0; row < data.length; row++) {
            data[row][0] = labels[row];
            data[row][1] = DNF;
        }

        JTable table = new JTable(data, columns);
        table.setEnabled(false);  // non-editable cells
        return table;
    }
}
