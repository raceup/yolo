package it.raceup.yolo.ui.window;

import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.ui.utils.AboutDialog;

import javax.swing.*;
import java.awt.*;

import static it.raceup.yolo.models.data.Base.DNF;
import static it.raceup.yolo.models.data.Type.*;

public class Main extends JFrame {
    private static final String THIS_PACKAGE = "com.raceup.ed.bms.BmsGui";
    private static final String ICON_PATH = "/res/images/logo.png";
    private static final Image appIcon = Toolkit.getDefaultToolkit().getImage(
            THIS_PACKAGE.getClass().getResource(ICON_PATH)
    );

    public Main() {
        super("YOLO | Race Up Electric Division");
        setup();
        open();
    }

    private void setup() {
        setupLayout();
        pack();
        setSize(854, 421);
        setResizable(false);
        setVisible(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(appIcon);  // set icon
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
        add(getMotorsPanel());
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(getCanPanel());
        setJMenuBar(createMenuBar());
    }

    private void open() {
        pack();
        setSize(800, 400);
        setLocation(0, 0);  // top left corner
        setVisible(true);
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

    private JPanel getMotorsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel up = new JPanel();
        up.setLayout(new BoxLayout(up, BoxLayout.X_AXIS));
        up.add(getMotorPanel("Front left"));
        up.add(Box.createRigidArea(new Dimension(10, 0)));
        up.add(getMotorPanel("Front right"));

        JPanel down = new JPanel();
        down.setLayout(new BoxLayout(down, BoxLayout.X_AXIS));
        down.add(getMotorPanel("Rear left"));
        down.add(Box.createRigidArea(new Dimension(10, 0)));
        down.add(getMotorPanel("Rear right"));

        panel.add(up);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(down);

        return panel;
    }

    private JPanel getMotorPanel(String tag) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTable table = getMotorTable();
        JScrollPane tableContainer = new JScrollPane(table);
        Dimension d = table.getPreferredSize();
        tableContainer.setPreferredSize(
                new Dimension(
                        d.width * 2,
                        table.getRowHeight() * table.getRowCount() + 22
                )
        );
        panel.add(new JLabel(tag));
        panel.add(tableContainer);

        return panel;
    }

    private JPanel getCanPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel up = new JPanel();
        up.setLayout(new BoxLayout(up, BoxLayout.X_AXIS));
        up.add(getCanEditPanel("ID"));
        up.add(Box.createRigidArea(new Dimension(10, 0)));
        up.add(getCanEditPanel("Message (DEC)"));

        JPanel down = new JPanel();
        down.setLayout(new BoxLayout(down, BoxLayout.X_AXIS));
        down.add(new JButton("SEND"));

        panel.add(new JLabel("Send CAN message"), BorderLayout.CENTER);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(up);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(down, BorderLayout.CENTER);
        panel.add(Box.createRigidArea(new Dimension(0, 200)));

        return panel;
    }

    private JPanel getCanEditPanel(String editField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel(editField));
        JTextField editor = new JTextField();
        panel.add(editor);

        return panel;
    }

    public void update(Raw data) {
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());  // file
        menuBar.add(createHelpMenu());  // help/about
        return menuBar;
    }

    private JMenu createFileMenu() {
        JMenu menu = new JMenu("File");  // file menu

        JMenuItem item = new JMenuItem("Exit");
        item.addActionListener(e -> System.exit(0));
        menu.add(item);

        return menu;
    }

    private JMenu createHelpMenu() {
        JMenu menu = new JMenu("Help");  // help menu

        JMenuItem item = new JMenuItem("Help");  // help menu -> help
        item.addActionListener(e -> showHelpDialogOrFail());
        menu.add(item);

        item = new JMenuItem("About");  // help menu -> about
        item.addActionListener(e -> showAboutDialogOrFail());
        menu.add(item);

        return menu;
    }

    /**
     * Show a dialog about the app
     */
    private void showAboutDialogOrFail() {
        String content = "";  // todo
        String title = "About this app";
        new AboutDialog(this, content, title).setVisible(true);
    }

    /**
     * Show a help dialog about the app
     */
    private void showHelpDialogOrFail() {
        String content = "";  // todo
        String title = "Help";
        new AboutDialog(this, content, title).setVisible(true);
    }

}
