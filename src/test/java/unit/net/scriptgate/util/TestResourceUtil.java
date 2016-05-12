package net.scriptgate.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestResourceUtil {

    public static final String USERNAME = "fiantje.username";

    public static final String PASSWORD = "fiantje.password";
    private static TestResourceUtil instance;


    private Properties config;

    private TestResourceUtil() {
        config = new Properties();
        try (InputStream configFile = TestResourceUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            config.load(configFile);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    public static String getFileContent(String fileName) throws IOException {
        InputStream orderExtractInputStream = TestResourceUtil.class.getClassLoader().getResourceAsStream(fileName);
        return InputStreamUtil.getContent(orderExtractInputStream);
    }

    private static TestResourceUtil getInstance() {
        if (instance == null) {
            instance = new TestResourceUtil();
        }
        return instance;
    }

    public static String getProperty(String name) {
        return getInstance().config.getProperty(name);
    }

}
