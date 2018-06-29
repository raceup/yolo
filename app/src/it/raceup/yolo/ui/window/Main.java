package it.raceup.yolo.ui.window;

import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.ui.component.CanMessageSender;
import it.raceup.yolo.ui.component.MotorInfo;
import it.raceup.yolo.ui.utils.AboutDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static it.raceup.yolo.models.Car.MOTOR_TAGS;

public class Main extends JFrame {
    private static final String THIS_PACKAGE = "com.raceup.ed.bms.BmsGui";
    private static final String ICON_PATH = "/res/images/logo.png";
    private static final Image appIcon = Toolkit.getDefaultToolkit().getImage(
            THIS_PACKAGE.getClass().getResource(ICON_PATH)
    );
    private final MotorInfo[] motorPanels = new MotorInfo[MOTOR_TAGS.length];
    private final Motor[] motorWindows = new Motor[MOTOR_TAGS.length];
    private final CanMessageSender canPanel = new CanMessageSender();

    public Main() {
        super("YOLO | Race Up Electric Division");

        for (int i = 0; i < MOTOR_TAGS.length; i++) {
            motorPanels[i] = new MotorInfo(MOTOR_TAGS[i]);
            motorWindows[i] = new Motor(MOTOR_TAGS[i]);
        }

        setup();
        setWindowsTogglers();
        open();
    }

    private void setup() {
        setupLayout();
    }

    private void open() {
        pack();
        setSize(900, 500);
        setLocation(0, 0);  // top left corner
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
        add(canPanel);
        setJMenuBar(createMenuBar());
    }

    private JPanel getMotorsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel up = new JPanel();
        up.setLayout(new BoxLayout(up, BoxLayout.X_AXIS));
        up.add(motorPanels[0]);
        up.add(Box.createRigidArea(new Dimension(10, 0)));
        up.add(motorPanels[1]);

        JPanel down = new JPanel();
        down.setLayout(new BoxLayout(down, BoxLayout.X_AXIS));
        down.add(motorPanels[2]);
        down.add(Box.createRigidArea(new Dimension(10, 0)));
        down.add(motorPanels[3]);

        panel.add(up);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(down);

        return panel;
    }

    private void setWindowsTogglers() {
        for (int i = 0; i < motorPanels.length; i++) {
            motorPanels[i].viewButton.addActionListener(getCheckAction(i));
        }
    }

    private ActionListener getCheckAction(int motor) {
        return actionEvent -> {
            if (motorWindows[motor].isVisible()) {
                motorWindows[motor].close();
            } else {
                motorWindows[motor].open();
            }
        };
    }

    public void update(Raw data) {
        int motor = data.getMotor();

        try {
            motorPanels[motor].update(data);
        } catch (Exception e) {
            System.err.println("update(Raw data): CANNOT UPDATE MOTOR PANEL");
        }

        try {
            motorWindows[motor].update(data);
        } catch (Exception e) {
            System.err.println("update(Raw data): CANNOT UPDATE MOTOR WINDOW");
        }
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

    private void showAboutDialogOrFail() {
        String content = "";  // todo
        String title = "About this app";
        new AboutDialog(this, content, title).setVisible(true);
    }

    private void showHelpDialogOrFail() {
        String content = "";  // todo
        String title = "Help";
        new AboutDialog(this, content, title).setVisible(true);
    }

}
