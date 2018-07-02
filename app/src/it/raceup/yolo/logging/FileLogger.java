package it.raceup.yolo.logging;

import java.io.File;
import java.io.PrintWriter;

public class FileLogger {
    private PrintWriter writer;

    public FileLogger(String filename) {
        if (!setup(filename)) {
            System.err.println("Cannot open file logger @" + filename);
        }
    }

    private boolean setup(String filename) {
        try {
            writer = new PrintWriter(filename, "UTF-8");
            return true;
        } catch (Exception e) {
            boolean isOk = new File(filename)
                    .getParentFile()
                    .mkdirs();
            return isOk && setup(filename);

        }
    }

    public void appendNewLine(String line) {
        appendNew("\n" + line);
    }

    public void appendNew(String content) {
        writer.print(content);
    }
}
