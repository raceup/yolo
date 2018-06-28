package it.raceup.yolo.ui.window;

import it.raceup.yolo.models.data.Raw;

import javax.swing.*;

import static it.raceup.yolo.models.data.Base.DNF;
import static it.raceup.yolo.models.data.Raw.FLAGS;

public class Motor extends JFrame {
    public Motor(final String title) {
        super(title);
        setup();
    }

    public void setup() {
        String[] columns = new String[]{
                "Key", "Value"
        };  // headers for the table

        // actual data
        Object[][] data = new Object[FLAGS.length][columns.length];
        for (int row = 0; row < data.length; row++) {
            data[row][0] = FLAGS[row];
            data[row][1] = DNF;
        }

        // create table with data
        JTable table = new JTable(data, columns);

        // add the table to the frame
        add(new JScrollPane(table));


        setSize(800, 400);
        setVisible(true);
    }

    public JPanel getContentPanel() {
        return null;
    }

    public void update(Raw data) {
    }
}
