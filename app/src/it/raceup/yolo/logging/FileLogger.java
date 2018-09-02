package it.raceup.yolo.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileLogger extends StreamLogger {
    public FileLogger(String filename) throws FileNotFoundException {
        super(new FileOutputStream(filename));
    }

    public static boolean create(String filename) {
        return new File(filename)
                .getParentFile()
                .mkdirs();
    }
}
