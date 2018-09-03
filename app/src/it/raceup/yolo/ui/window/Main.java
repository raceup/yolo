package it.raceup.yolo.ui.window;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.ui.component.CanMessageBrowser;
import it.raceup.yolo.ui.component.CanMessageSender;
import it.raceup.yolo.ui.component.MotorInfo;
import it.raceup.yolo.ui.utils.AboutDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static it.raceup.yolo.models.car.Car.DEFAULT_MOTORS;

public class Main extends JFrame {
    private static final String THIS_PACKAGE = "it.raceup.yolo.ui.window";
    private static final String ICON_PATH = "/res/images/logo.png";
    private static Image appIcon = null;

    static {
        try {
            appIcon = Toolkit.getDefaultToolkit().getImage(
                    THIS_PACKAGE.getClass().getResource(ICON_PATH)
            );
        } catch (Exception e) {
            new YoloException(
                    "cannot set app icon",
                    ExceptionType.APP
            ).print();
            appIcon = null;
        }
    }

    private final MotorInfo[] motorPanels = new MotorInfo[DEFAULT_MOTORS.length];
    private final Motor[] motorWindows = new Motor[DEFAULT_MOTORS.length];
    private final CanMessageSender canMessageSender;
    private final CanMessageBrowser canMessageBrowser;

    public Main() {
        super("YOLO | Race Up Electric Division");

        for (int i = 0; i < DEFAULT_MOTORS.length; i++) {
            motorPanels[i] = new MotorInfo(DEFAULT_MOTORS[i]);
            motorWindows[i] = new Motor(DEFAULT_MOTORS[i]);
        }

        canMessageSender = new CanMessageSender();  // todo connect to kvaser
        canMessageBrowser = new CanMessageBrowser();  // todo connect to kvaser

        setup();
        setWindowsTogglers();
        open();
    }

    private void setup() {
        setupLayout();
    }

    private void open() {
        try {
            pack();
            setSize(1024, 512);
            setLocation(0, 0);  // top left corner
            setResizable(false);
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // close app
        } catch (Exception e) {
            new YoloException(
                    "cannot open view",
                    e,
                    ExceptionType.VIEW
            ).print();
        }

        try {
            setIconImage(appIcon);  // set icon
        } catch (Exception e) {
            new YoloException(
                    "cannot set image",
                    e,
                    ExceptionType.APP
            ).print();
        }
    }

    private void setupLayout() {
        getRootPane().setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );  // border

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        add(getMotorsPanel());
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(getCanMessageSender());

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

    private JPanel getCanMessageSender() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(canMessageSender);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(canMessageBrowser);

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
        update(data.getMotor(), data.getType(), data.getRaw());
    }

    public void update(int motor, it.raceup.yolo.models.data.Type type,
                       Double data) {
        try {
            motorPanels[motor].update(type, data);
        } catch (Exception e) {
            System.err.println("update(Raw data): CANNOT UPDATE MOTOR PANEL");
        }

        try {
            motorWindows[motor].update(type, data);
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

    public void close() {
        setVisible(false);
    }
}
