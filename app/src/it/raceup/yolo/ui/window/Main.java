package it.raceup.yolo.ui.window;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.ui.component.CanMessagesPanel;
import it.raceup.yolo.ui.component.MotorsPanel;
import it.raceup.yolo.ui.utils.AboutDialog;

import javax.swing.*;
import java.awt.*;

import static it.raceup.yolo.utils.Os.setNativeLookAndFeelOrFail;

public class Main extends JFrame {
    private static final String TITLE = "YOLO: telemetry by Race UP ED";
    private static final String ICON_PATH = "/res/images/logo.png";
    private final MotorsPanel motorPanels;
    private final CanMessagesPanel canMessagesPanel;
    private Image appIcon;

    public Main() {
        super(TITLE);

        motorPanels = new MotorsPanel();
        canMessagesPanel = new CanMessagesPanel();

        setup();
        open();
    }

    private void setup() {
        setupLayout();
    }

    private void open() {
        try {
            pack();
            setSize(1300, 500);
            setLocation(0, 0);  // top left corner
            setResizable(false);
            setNativeLookAndFeelOrFail();
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
            loadIcon();
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

        add(motorPanels);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(canMessagesPanel);

        setJMenuBar(createMenuBar());
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

    public MotorsPanel getMotorPanels() {
        return motorPanels;
    }

    public CanMessagesPanel getCanMessagesPanel() {
        return canMessagesPanel;
    }

    private void loadIcon() {
        try {
            appIcon = Toolkit.getDefaultToolkit().getImage(
                    getClass().getResource(ICON_PATH)
            );
        } catch (Exception e) {
            new YoloException(
                    "cannot set app icon",
                    ExceptionType.APP
            ).print();
        }
    }
}
