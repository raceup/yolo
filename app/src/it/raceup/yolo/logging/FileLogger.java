package it.raceup.yolo.logging;

import it.raceup.yolo.error.ExceptionType;
import it.raceup.yolo.error.YoloException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileLogger extends StreamLogger {
    public FileLogger(String filename) throws FileNotFoundException {
        super(new FileOutputStream(filename));

        if (!setup(filename)) {
            new YoloException(
                    "Cannot open file logger @" + filename, ExceptionType.UNKNOWN
            ).print();
        }
    }

    private static boolean setup(String filename) {
        return new File(filename)
                .getParentFile()
                .mkdirs();
    }
}
