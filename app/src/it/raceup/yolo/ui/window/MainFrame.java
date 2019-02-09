package it.raceup.yolo.ui.window;

import it.raceup.yolo.Data;
import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.ui.component.motors.MotorsPanel;
import it.raceup.yolo.ui.utils.AboutDialog;

import javax.swing.*;
import java.awt.*;

import static it.raceup.yolo.Data.*;
import static it.raceup.yolo.utils.Os.setNativeLookAndFeelOrFail;

public class MainFrame extends JFrame {
    // frames
    private final MotorsPanel motorPanels = new MotorsPanel();
    private final BatteryFrame batteryFrame = new BatteryFrame();
    private final DynamicsFrame dynamicsFrame = new DynamicsFrame();
    //test code
    private final DriverFrame driverFrame = new DriverFrame();

    public MainFrame() {
        super(MOTORS_WINDOW_TITLE);

        setup();
        open();
    }

    private void setup() {
        setupLayout();
    }

    private void open() {
        try {
            pack();
            setSize(700, 500);
            setLocation(0, 0);  // top left corner
            setResizable(false);
            setNativeLookAndFeelOrFail();
            setVisible(true);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  // close app
            openFrames();  // open rest of frames
        } catch (Exception e) {
            new YoloException(
                    "cannot open view",
                    e,
                    ExceptionType.VIEW
            ).print();
        }

        try {
            loadIcon();
        } catch (Exception e) {
            new YoloException(
                    "cannot set image",
                    e,
                    ExceptionType.APP
            ).print();
        }
    }

    private void openFrames() {
        batteryFrame.open();
        dynamicsFrame.open();
    }

    private void setupLayout() {
        getRootPane().setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );  // border

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        add(motorPanels);
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
        item.addActionListener(e -> showHelpDialog());
        menu.add(item);

        item = new JMenuItem("About");  // help menu -> about
        item.addActionListener(e -> showAboutDialog());
        menu.add(item);

        return menu;
    }

    private void showAboutDialog() {
        new AboutDialog(ABOUT_TITLE, getAboutContent(), JOptionPane.INFORMATION_MESSAGE).show();
    }

    private void showHelpDialog() {
        new AboutDialog(HELP_TITLE, getHelpContent(), JOptionPane.WARNING_MESSAGE).show();
    }

    public void close() {
        setVisible(false);
    }

    public MotorsPanel getMotorPanels() {
        return motorPanels;
    }

    public BatteryFrame getBatteryFrame() {
        return batteryFrame;
    }

    public DynamicsFrame getDynamicsFrame() {
        return dynamicsFrame;
    }

    public DriverFrame getDriverFrame() {
        return driverFrame;
    }

    private void loadIcon() {
        Image icon = new Data().getAppImage();
        setIconImage(icon);  // set icon
    }
}
