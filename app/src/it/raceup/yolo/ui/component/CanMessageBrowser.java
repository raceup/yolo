package it.raceup.yolo.ui.component;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.data.CanMessage;
import it.raceup.yolo.models.kvaser.message.FromKvaserMessage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static it.raceup.yolo.models.data.Base.DNF;
import static it.raceup.yolo.utils.Utils.getTimeNow;

/**
 * Table to show latest CAN messages
 */
public class CanMessageBrowser extends JPanel implements Observer {
    private final int MESSAGES_TO_SHOW = 64;
    private final String[] TABLE_HEADERS = new String[]{"ID", "time", "flags",
            "dlc", "data"};
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

        table = new JTable(data, TABLE_HEADERS);
        for (int column = 0; column < 4; column++) {
            table.getColumn(TABLE_HEADERS[column]).setPreferredWidth(20);
        }
        table.getColumn("data").setPreferredWidth(100);

        table.setEnabled(false);  // non-editable cells

        table.setPreferredScrollableViewportSize(new Dimension(512, 512));
        table.setFillsViewportHeight(true);

        tableContainer = new JScrollPane(table);
    }

    private JPanel getHeadPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        panel.add(new JLabel("Showing last " + MESSAGES_TO_SHOW
                + " messages received"));

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


    private void update(CanMessage message) {
        // todo
    }

    private void update(ArrayList<CanMessage> messages) {
        for (CanMessage message : messages) {
            update(message);
        }
        lastUpdateLabel.setText("Last update: " + lastUpdate);
    }

    @Override
    public void update(Observable observable, Object o) {
        try {
            lastUpdate = getTimeNow();
            FromKvaserMessage message = new FromKvaserMessage(o);
            ArrayList<CanMessage> messages = message.getAsCanMessages();
            if (messages != null) {
                update(messages);
            }
        } catch (Exception e) {
            new YoloException("cannot update message browser", e, ExceptionType
                    .VIEW).print();
        }
    }
}
