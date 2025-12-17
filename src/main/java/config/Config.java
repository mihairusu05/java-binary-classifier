package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Properties properties = new Properties();
    static {
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Warning: Could not find config.properties. Using defaults.");
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

}