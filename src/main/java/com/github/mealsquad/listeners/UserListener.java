package com.github.mealsquad.listeners;

import com.github.mealsquad.channel.ChannelHandler;
import com.github.mealsquad.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.event.message.MessageCreateEvent;

public class UserListener extends AbstractListener {

    private static final Logger logger = LogManager.getFormatterLogger();

    @Override
    public void addMessageDetails(MessageCreateEvent event) {
        if (event.getMessage().getContent().contains("!addUser")) {
            String[] cmd = event.getMessageContent().split(" ");
            if (cmd.length > 2) {
                throw new UnsupportedOperationException("Unable to accept more than one username or a name containing a space");
            }
            String user = cmd[1];
            User newUser = new User(user);
            ChannelHandler.getInstance().getUserCache().getAddBuffer().add(newUser);

            logger.info("Adding %s to addUserBuffer", user);
            event.getChannel().sendMessage(String.format("Added new user %s to the dinner-board", user));
        }
    }
}
