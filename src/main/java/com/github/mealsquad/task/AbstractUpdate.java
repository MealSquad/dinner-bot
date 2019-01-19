package com.github.mealsquad.task;

import com.github.mealsquad.channel.ChannelHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

abstract class AbstractUpdate implements Runnable {

    private final Logger logger = LogManager.getFormatterLogger();
    protected final List<String> headerInfo = Arrays.asList("Order", "Kills", "Wins", "Top Kills", "Top DPS/Game", "# of Dion Dinners");
    protected ChannelHandler channelHandler;

    AbstractUpdate() {
        logger.info("Creating Update");
        channelHandler = ChannelHandler.getInstance();
    }
}

