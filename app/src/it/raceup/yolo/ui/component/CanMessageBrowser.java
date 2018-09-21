package it.raceup.yolo.ui.component;

import it.raceup.yolo.models.data.CanMessage;
import it.raceup.yolo.ui.component.table.CanMessageTable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static it.raceup.yolo.models.data.Base.*;
import static it.raceup.yolo.utils.Misc.getTimeNow;

/**
 * Table to show latest CAN messages
 */
public class CanMessageBrowser extends JPanel {
    private final int MESSAGES_TO_SHOW = 64;
    private final String[] TABLE_HEADERS = new String[]{"ID", "time", "flags",
            "dlc", "byte 1", "byte 2", "byte 3", "byte 4", "byte 5", "byte " +
            "6", "byte 7", "byte 8"};
    private String lastUpdate = getTimeNow();
    private final JLabel lastUpdateLabel = new JLabel("Last update: " +
            lastUpdate);
    private JTable table;  // newest messages upfront, oldest at the bottom
    private JScrollPane tableContainer;

    public CanMessageBrowser() {
        setup();
    }

    private void setup() {
        setupTable();
        setupLayout();
    }

    private void setupTable() {
        Object[][] data = new Object[MESSAGES_TO_SHOW][TABLE_HEADERS.length];
        for (int row = 0; row < data.length; row++) {
            for (int column = 0; column < TABLE_HEADERS.length; column++) {
                data[row][column] = DNF;
            }
        }

        table = new CanMessageTable(data, TABLE_HEADERS);
        for (String TABLE_HEADER : TABLE_HEADERS) {
            table.getColumn(TABLE_HEADER).setPreferredWidth(20);
        }
        table.getColumn("time").setPreferredWidth(60);

        table.setEnabled(false);  // non-editable cells

        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        table.setFillsViewportHeight(true);

        tableContainer = new JScrollPane(table);
    }

    private JPanel getHeadPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        panel.add(new JLabel("Showing last " + MESSAGES_TO_SHOW
                + " messages received. Most recent are on top"));

        return panel;
    }

    private JPanel getLastUpdatePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        panel.add(lastUpdateLabel);

        return panel;
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(getHeadPanel());

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(tableContainer);

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(getLastUpdatePanel());
    }

    private void shiftBuffer() {
        for (int row = MESSAGES_TO_SHOW - 1; row > 0; row--) {
            for (int column = 0; column < TABLE_HEADERS.length; column++) {
                Object value = table.getValueAt(row - 1, column);
                table.setValueAt(value, row, column);
            }
        }
    }

    private void addNewMessage(CanMessage message) {
        shiftBuffer();
        update(0, message);
    }


    private void update(int row, CanMessage message) {
        table.setValueAt(getAsString(message.getId()), row, 0);
        table.setValueAt(forceIntAsString(message.getTime()), row, 1);
        table.setValueAt(getAsString(message.getFlags()), row, 2);
        table.setValueAt(getAsString(message.getDlc()), row, 3);
        table.setValueAt(message.getData()[0], row, 4);
        table.setValueAt(message.getData()[1], row, 5);
        table.setValueAt(message.getData()[2], row, 6);
        table.setValueAt(message.getData()[3], row, 7);
        table.setValueAt(message.getData()[4], row, 8);
        table.setValueAt(message.getData()[5], row, 9);
        table.setValueAt(message.getData()[6], row, 10);
        table.setValueAt(message.getData()[7], row, 11);
    }

    public void update(ArrayList<CanMessage> messages) {
        lastUpdate = getTimeNow();

        shiftBuffer();

        // todo reverse list ?? check time
        int maxRow = Math.min(messages.size(), MESSAGES_TO_SHOW);
        for (int row = 0; row < maxRow; row++) {
            update(row, messages.get(row));
        }

        lastUpdateLabel.setText("Last update: " + lastUpdate);
    }
}
