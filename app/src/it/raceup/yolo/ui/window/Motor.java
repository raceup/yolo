package it.raceup.yolo.ui.window;

import it.raceup.yolo.models.data.Raw;

import javax.swing.*;
import java.awt.*;

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

        setSize(800, 400);
        setVisible(true);
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

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setAlignmentX(JScrollPane.RIGHT_ALIGNMENT);
        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );  // border

        panel.add(new JPanel());
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(new JPanel());

        add(panel);
        add(new JPanel());

        /*JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));

        JPanel leftPanel = new JPanel();
        leftPanel.add(flags);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(sp);
        rightPanel.add(temperature);

        JPanel leftHalf = new JPanel() {
            // Don't allow us to stretch vertically.
            public Dimension getMaximumSize() {
                Dimension pref = getPreferredSize();
                return new Dimension(Integer.MAX_VALUE,
                        pref.height);
            }
        };
        leftHalf.setLayout(new BoxLayout(leftHalf, BoxLayout.PAGE_AXIS));
        leftHalf.add(leftPanel);

        mainPanel.add(leftHalf);
        mainPanel.add(rightPanel);
        add(mainPanel);*/

    }

    public void update(Raw data) {
    }
}
