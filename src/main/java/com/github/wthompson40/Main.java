package com.github.wthompson40;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Properties;

public class Main {

    private static final String fileName = "application.conf";
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        // Insert your bot's token here
        String token = readProperties("token");

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        logger.info("Successfully join text channel");

        // Add a listener which answers with "Pong!" if someone writes "!ping"
        api.addMessageCreateListener(event -> {
            if (event.getMessage().getContent().equalsIgnoreCase("!ping")) {
                event.getChannel().sendMessage("Pong!");
            }
        });

        // Print the invite url of your bot
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
    }

    private static String readProperties(String key) {
        Properties properties = new Properties();
        String requestedProperty = "";
        try {
            ClassLoader classLoader = Main.class.getClassLoader();
            URL res = Objects.requireNonNull(classLoader.getResource(fileName), "Can't find configuration file app.config");
            InputStream is = new FileInputStream(res.getFile());

            properties.load(is);
            requestedProperty = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestedProperty;
    }

}
