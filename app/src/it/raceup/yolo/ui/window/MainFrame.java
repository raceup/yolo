package it.raceup.yolo.ui.window;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;
import it.raceup.yolo.ui.component.MotorsPanel;
import it.raceup.yolo.ui.utils.AboutDialog;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static it.raceup.yolo.utils.Os.setNativeLookAndFeelOrFail;

public class MainFrame extends JFrame {
    private static final String TITLE = "YOLO: AMK and inverters";

    // res
    private static final String ICON_PATH = "/res/images/logo.png";
    private static final String ABOUT_PATH = "/res/strings/about/content.html";
    private static final String HELP_PATH = "/res/strings/help/content.html";
    private static Image appIcon;
    private static String aboutContent;
    private static String helpContent;

    // frames
    private final MotorsPanel motorPanels;
    private final CanMessagesFrame canMessagesFrame;
    private final BatteryFrame batteryFrame;
    private final CarFrame carFrame;

    public MainFrame() {
        super(TITLE);

        motorPanels = new MotorsPanel();
        canMessagesFrame = new CanMessagesFrame();
        batteryFrame = new BatteryFrame();
        carFrame = new CarFrame();

        setup();
        open();
    }

    private void setup() {
        setupLayout();
    }

    private void open() {
        try {
            pack();
            setSize(600, 500);
            setLocation(0, 0);  // top left corner
            setResizable(false);
            setNativeLookAndFeelOrFail();
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // close app
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

        try {
            loadDialogContent();
        } catch (Exception e) {
            new YoloException(
                    "cannot find ",
                    e,
                    ExceptionType.APP
            ).print();
        }
    }

    private void openFrames() {
        canMessagesFrame.open();
        // todo show this frame batteryFrame.open();
        // todo show this frame carFrame.open();
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
        item.addActionListener(e -> showHelpDialogOrFail());
        menu.add(item);

        item = new JMenuItem("About");  // help menu -> about
        item.addActionListener(e -> showAboutDialogOrFail());
        menu.add(item);

        return menu;
    }

    private void showAboutDialogOrFail() {
        String title = "About this app";
        new AboutDialog(this, aboutContent, title).setVisible(true);
    }

    private void showHelpDialogOrFail() {
        String title = "Help";
        new AboutDialog(this, helpContent, title).setVisible(true);
    }

    public void close() {
        setVisible(false);
    }

    public MotorsPanel getMotorPanels() {
        return motorPanels;
    }

    public CanMessagesFrame getCanMessagesFrame() {
        return canMessagesFrame;
    }

    public BatteryFrame getBatteryFrame() {
        return batteryFrame;
    }

    private void loadIcon() {
        appIcon = Toolkit.getDefaultToolkit().getImage(
                getClass().getResource(ICON_PATH)
        );
        setIconImage(appIcon);  // set icon
    }

    private void loadDialogContent() throws IOException {
        aboutContent = loadDialogContent(ABOUT_PATH);
        helpContent = loadDialogContent(HELP_PATH);
    }

    private String loadDialogContent(String filePath) throws IOException {
        InputStream in = getClass().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder builder = new StringBuilder();

        String line = reader.readLine();
        while (line != null) {
            builder.append(line).append("\n");
            line = reader.readLine();
        }

        return builder.toString();
    }
}
