package it.raceup.yolo.ui.window;

import it.raceup.yolo.models.data.Raw;
import it.raceup.yolo.ui.utils.AboutDialog;

import javax.swing.*;
import java.awt.*;

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        createMenuBar();
    }

    private void open() {
        pack();
        setSize(800, 400);
        setLocation(0, 0);  // top left corner
        setVisible(true);
    }

    public JPanel getContentPanel() {
        return null;
    }

    public void update(Raw data) {
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());  // file
        menuBar.add(createEditMenu());  // edit
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

    private JMenu createEditMenu() {
        JMenu menu = new JMenu("Edit");  // file menu

        JMenuItem item = new JMenuItem("Send CAN message");
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
