package util;

import java.io.FileWriter;
import java.io.IOException;

public class LoggerUtil {
    public static void log(String message) {
        try (FileWriter fw = new FileWriter("log.txt", true)) {
            fw.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}