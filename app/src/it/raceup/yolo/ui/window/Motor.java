package it.raceup.yolo.ui.window;

import it.raceup.yolo.models.data.Raw;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static it.raceup.yolo.models.data.Base.DNF;
import static it.raceup.yolo.models.data.Raw.*;

public class Motor extends JFrame {
    private JTable flags, sp, temperature;

    public Motor(final String title) {
        super(title);
        setup();
    }

    private void setup() {
        setupTables();
        setupLayout();
    }

    public void open() {
        setSize(600, 320);
        setResizable(false);
        setVisible(true);
    }

    public void close() {
        setVisible(false);
    }

    private void setupTables() {
        String[] columns = new String[]{
                "Key", "Value"
        };  // headers for the table

        // actual data
        Object[][] data = new Object[FLAGS.length][columns.length];
        for (int row = 0; row < data.length; row++) {
            data[row][0] = FLAGS[row];
            data[row][1] = DNF;
        }
        flags = new JTable(data, columns);
        flags.setEnabled(false);  // non-editable cells

        data = new Object[SET_POINTS.length][columns.length];
        for (int row = 0; row < data.length; row++) {
            data[row][0] = SET_POINTS[row];
            data[row][1] = DNF;
        }
        sp = new JTable(data, columns);
        sp.setEnabled(false);  // non-editable cells

        data = new Object[TEMPERATURES.length][columns.length];
        for (int row = 0; row < data.length; row++) {
            data[row][0] = TEMPERATURES[row];
            data[row][1] = DNF;
        }
        temperature = new JTable(data, columns);
        temperature.setEnabled(false);  // non-editable cells
    }

    private void setupLayout() {
        getContentPane().setLayout(
                new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS)
        );  // add components horizontally
        getRootPane().setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );  // border
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(getTablePanel(sp));
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(getTablePanel(temperature));

        add(getTablePanel(flags));
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(rightPanel);
    }

    private JPanel getTablePanel(JTable table) {
        JPanel panel = new JPanel();

        JScrollPane tableContainer = new JScrollPane(table);
        Dimension d = table.getPreferredSize();
        tableContainer.setPreferredSize(
                new Dimension(
                        (int) (d.width * 1.8),
                        table.getRowHeight() * table.getRowCount() + 22
                )
        );
        panel.add(tableContainer, BorderLayout.CENTER);

        return panel;
    }

    public void update(Raw data) {
        String value = Double.toString(data.getRaw());
        if (isTemperature(data.getType())) {
            int tableRow = Arrays.asList(TEMPERATURES).indexOf(data.getType());
            temperature.setValueAt(value, tableRow, 1);
        } else if (isSetPoint(data.getType())) {
            int tableRow = Arrays.asList(SET_POINTS).indexOf(data.getType());
            sp.setValueAt(value, tableRow, 1);
        } else if (isFlag(data.getType())) {
            int tableRow = Arrays.asList(FLAGS).indexOf(data.getType());
            flags.setValueAt(value, tableRow, 1);
        }
    }
}
