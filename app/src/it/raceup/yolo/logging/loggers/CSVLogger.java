package it.raceup.yolo.logging.loggers;

import java.io.FileNotFoundException;
import java.util.stream.Stream;

public class CSVLogger extends FileLogger {
    private String[] columns;

    public CSVLogger(String filename, String[] columns) throws FileNotFoundException {
        super("CSV LOGGER", filename);
        this.columns = columns;

        log(this.columns);  // log header
    }

    public void log(Object[] row) {
        String message = getLogMessage(row);
        log(message);
    }

    @Override
    public String getLogMessage(String message) {
        if (message.endsWith("\n")) {
            return message;
        }

        return message + "\n";
    }

    private String getLogMessage(Object[] items) {
        String[] values = Stream.of(items)
                .map(Object::toString)
                .toArray(String[]::new);

        String row = String.join(",", values);
        return getLogMessage(row);
    }

    @Override
    public void log(Exception e) {
        // todo find wath to log in this case
    }
}
