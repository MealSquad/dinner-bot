package com.github.mealsquad.listeners;

import org.javacord.api.event.message.MessageCreateEvent;

public class HelpListener extends AbstractListener {

    @Override
    public void addMessageDetails(MessageCreateEvent event) {
        if (event.getMessage().getContent().equalsIgnoreCase("!help")) {
            event.getChannel().sendMessage("Thank you for using dinner-bot!  If you want to be included on the dinner-board, please use the following command: ```!addUser pubgUserName```");
        }
    }
}
