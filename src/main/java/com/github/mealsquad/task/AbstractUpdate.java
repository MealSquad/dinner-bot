package com.github.mealsquad.task;

import com.github.mealsquad.channel.ChannelHandler;

import java.util.Arrays;
import java.util.List;

abstract class AbstractUpdate implements Runnable {

    protected final List<String> headerInfo = Arrays.asList("Order", "Kills", "Wins", "Top Kills", "Top DPS/Game", "# of Dion Dinners");
    protected ChannelHandler channelHandler;

    AbstractUpdate() {
        channelHandler = ChannelHandler.getInstance();
    }
}


