package config;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationProperties {
    private static final Logger LOG = Logger.getLogger(ConfigurationProperties.class);
    private static Properties properties;
    private static String file = "";

    public static Properties createProperties(String fileName) {
        if (properties == null || !file.equals(fileName)) {
            file = fileName;
            properties = new Properties();
            try (InputStream fis = ConfigurationProperties.class.getClassLoader().getResourceAsStream(fileName)) {
                properties.load(fis);
            } catch (IOException e) {
                LOG.error("The file " + fileName + " is missing");
            }
        }
        return properties;
    }
}