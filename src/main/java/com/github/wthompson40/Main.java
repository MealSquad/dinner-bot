package com.github.wthompson40;

import com.github.wthompson40.model.Bot;
import com.github.wthompson40.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class Main {

    private static final String fileName = "application.conf";
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Bot dinner = new Bot(getUsers(), readProperties("token"), readProperties("apiKey"));
        dinner.run();
    }

    public static String readProperties(String key) {
        Properties properties = new Properties();
        String requestedProperty = "";
        try {
            ClassLoader classLoader = Main.class.getClassLoader();
            URL res = Objects.requireNonNull(classLoader.getResource(fileName), "Can't find configuration file app.config");
            InputStream is = new FileInputStream(res.getFile());

            properties.load(is);
            requestedProperty = properties.getProperty(key);
        } catch (IOException e) {
            logger.error(String.format("Failed to read property: [%s]", key));
            e.printStackTrace();
        }
        logger.info(String.format("Reading in configuration property: [%s] = %s", key, requestedProperty));
        return requestedProperty;
    }

    private static List<User> getUsers() {
        // This should read an on disk file with usernames who opt-in.  A player opting in should also update the on disk file.
        return new ArrayList<>();
    }

}
