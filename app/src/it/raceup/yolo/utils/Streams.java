package it.raceup.yolo.utils;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Utils method on in/out streams
 */
public class Streams {

    /**
     * Reads all content from stream
     *
     * @param reader reader of content
     * @return content (in string format)
     * @throws IOException when it cannot read stream
     */
    public static String readAllFromStream(final BufferedReader reader)
            throws IOException {
        String content = "";

        boolean thereIsAnotherLine = true;
        while (thereIsAnotherLine) {
            String line = reader.readLine();
            if (line != null) {
                content += line + "\n";
            } else {
                thereIsAnotherLine = false;
            }

        }

        return content;
    }
}
