package it.raceup.yolo;

import it.raceup.yolo.utils.Misc;

import javax.swing.*;
import java.awt.*;

/**
 * Container of useful stuff used across the app. Implements the Facade design pattern.
 */
public class Data {
    // urls
    public static final String REPOSITORY_URL = "https://github.com/raceup";
    public static final String ISSUES_URL = REPOSITORY_URL + "/yolo";

    // files
    public static final String VERSION_PATH = "/res/strings/version.txt";
    public static final String ABOUT_PATH = "/res/strings/about/content.html";
    public static final String HELP_PATH = "/res/strings/help/content.html";
    public static final String INTRO_PATH = "/res/strings/help/intro.html";

    // images
    public static final String APP_IMAGE_PATH = "/res/images/logo.png";
    // messages
    public static final String ERROR_FETCHING_ABOUT = "Sorry, we had trouble fetching the about section\n" +
            "\n" +
            "Please, report this and other bugs here " + ISSUES_URL;
    public static final String ERROR_FETCHING_HELP = "Sorry, we had trouble fetching the troubleshooting guide\n" +
            "\n" +
            "Please, report this and other bugs here " + ISSUES_URL;
    // titles
    public static final String APP_NAME = "YOLO Telemetry";
    public static final String ABOUT_TITLE = "About " + APP_NAME;
    public static final String HELP_TITLE = "Help | " + APP_NAME;
    public static final String MOTORS_WINDOW_TITLE = APP_NAME + " | " + "AMK & inverters";
    public static final String CAN_WINDOW_TITLE = APP_NAME + " | " + "CAN bus";
    public static final String BATTERY_WINDOW_TITLE = APP_NAME + " | " + "Battery";
    public static final String IMU_WINDOW_TITLE = APP_NAME + " | " + "IMU";
    // options
    public static final String[] BITRATES = {"10k", "50k", "62k", "83k", "100k", "125k", "250k", "500k", "1m"};
    // formats
    public static final String PRETTY_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String ICONS_PATH = "/res/images/circle/small/";
    private static final String RED_ICON_PATH = ICONS_PATH + "red.png";
    private static final String GREEN_ICON_PATH = ICONS_PATH + "green.png";

    // getters
    private static String getFileContentOr(String filePath, String onError) {
        try {
            return Misc.getFileContent(filePath);
        } catch (Exception e) {
            return onError;
        }
    }

    public static String getAboutContent() {
        return getFileContentOr(ABOUT_PATH, ERROR_FETCHING_ABOUT);
    }

    public static String getHelpContent() {
        return getFileContentOr(HELP_PATH, ERROR_FETCHING_HELP);
    }

    public static String getIntroContent() {
        return getFileContentOr(INTRO_PATH, ERROR_FETCHING_HELP);
    }

    public static String getAppVersion() {
        return getFileContentOr(VERSION_PATH, "unknown");
    }

    // images
    private final Image getImage(String path) {
        return Toolkit.getDefaultToolkit().getImage(
                getClass().getResource(path)
        );
    }

    public final Image getAppImage() {
        return getImage(APP_IMAGE_PATH);
    }

    public final Icon getRedIcon() {
        return new ImageIcon(getImage(RED_ICON_PATH));
    }

    public final Icon getGreenIcon() {
        return new ImageIcon(getImage(GREEN_ICON_PATH));
    }
}
