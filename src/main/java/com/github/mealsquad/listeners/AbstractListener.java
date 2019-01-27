package com.github.mealsquad.listeners;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public abstract class AbstractListener implements MessageCreateListener {

    private final String ME = "dinner-bot";

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (!event.getMessageAuthor().equals(ME)) {
            addMessageDetails(event);
        }
    }

    public abstract void addMessageDetails(MessageCreateEvent event);
}
