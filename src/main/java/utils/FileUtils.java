package utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
    private static final Logger LOG = Logger.getLogger(FileUtils.class);
    private static final String TT = "<TT>";

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

    public static void createDir(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public static StringBuilder readFileForHtml(String fileName) {
        StringBuilder text = new StringBuilder();
        File file = new File(fileName);
        try (FileReader fr = new FileReader(file);
             BufferedReader reader = new BufferedReader(fr)) {
            String line;
            text.append(TT);
            while ((line = reader.readLine()) != null) {
                text.append(line.replace(" ", "&nbsp")).append("<br>");
            }
        } catch (IOException e) {
            LOG.error("File read error");
        }
        return text.append(TT);
    }
}