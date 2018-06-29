package it.raceup.yolo.ui.window;

import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.ui.component.CanMessageSender;
import it.raceup.yolo.ui.component.MotorInfo;
import it.raceup.yolo.ui.utils.AboutDialog;

import javax.swing.*;
import java.awt.*;

import static it.raceup.yolo.models.Car.MOTOR_TAGS;

public class Main extends JFrame {
    private static final String THIS_PACKAGE = "com.raceup.ed.bms.BmsGui";
    private static final String ICON_PATH = "/res/images/logo.png";
    private static final Image appIcon = Toolkit.getDefaultToolkit().getImage(
            THIS_PACKAGE.getClass().getResource(ICON_PATH)
    );
    private MotorInfo[] motorPanels = new MotorInfo[MOTOR_TAGS.length];
    private Motor[] motorWindows = new Motor[MOTOR_TAGS.length];
    private CanMessageSender canPanel = new CanMessageSender();

    public Main() {
        super("YOLO | Race Up Electric Division");

        for (int i = 0; i < MOTOR_TAGS.length; i++) {
            motorPanels[i] = new MotorInfo(MOTOR_TAGS[i]);
            motorWindows[i] = new Motor(MOTOR_TAGS[i]);
        }

        setup();
        open();
    }

    private void setup() {
        setupLayout();
        pack();
        setSize(854, 441);
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

    private void open() {
        pack();
        setSize(800, 400);
        setLocation(0, 0);  // top left corner
        setVisible(true);
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
