package com.github.wthompson40.Models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.util.List;

public class Bot implements Runnable {

    private final Logger logger = LogManager.getLogger(Bot.class);
    private List<User> users;
    private String token;
    private String apiKey;

    public Bot(List<User> users, String token, String apiKey) {
        this.users = users;
        this.token = token;
        this.apiKey = apiKey;
    }

    public List<User> getUsers() {
        return users;
    }

    public String getToken() {
        return token;
    }

    public String getApiKey() {
        return apiKey;
    }

    @Override
    public void run() {
        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        logger.info("Successfully join text channel");

        // Add a listener which answers with "Pong!" if someone writes "!ping"
        api.addMessageCreateListener(event -> {
            if (event.getMessage().getContent().equalsIgnoreCase("!ping")) {
                event.getChannel().sendMessage("Pong!");
            }
        });

        // Log invite url of your bot
        logger.info("You can invite the bot by using the following url: " + api.createBotInvite());
    }
}
