package com.github.mealsquad.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.event.message.MessageCreateEvent;

public class HelpListener extends AbstractListener {

    private static final Logger logger = LogManager.getFormatterLogger();

    public HelpListener() {
        logger.info("Creating help listener");
    }

    @Override
    public void addMessageDetails(MessageCreateEvent event) {
        if (event.getMessage().getContent().equalsIgnoreCase("!help")) {
            event.getChannel().sendMessage("Thank you for using dinner-bot!  If you want to be included on the dinner-board, please use the following command: ```!addUser pubgUserName```");
        }
    }
}
