package utils;

import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
    private static final Logger LOG = Logger.getLogger(FileUtils.class);

    public static String readFiles(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static void displayReceiptToConsole(String path) {
        try {
            System.out.println(readFiles(path));
        } catch (IOException e) {
            LOG.error("Error reading the receipt " + e);
        }
    }

    public static void writeLastNumberInFile(int receiptNumber, String path) {
        try (FileWriter writer = new FileWriter(path, false)) {
            writer.write(String.valueOf(receiptNumber));
            writer.flush();
        } catch (IOException ex) {
            LOG.error("File creation error");
        }
    }
}